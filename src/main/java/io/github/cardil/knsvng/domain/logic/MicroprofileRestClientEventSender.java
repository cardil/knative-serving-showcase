package io.github.cardil.knsvng.domain.logic;

import io.cloudevents.CloudEvent;
import io.cloudevents.core.builder.CloudEventBuilder;
import io.cloudevents.core.extensions.DistributedTracingExtension;
import io.cloudevents.http.restful.ws.CloudEventsProvider;
import io.github.cardil.knsvng.config.EventsConfiguration;
import io.opentracing.SpanContext;
import io.opentracing.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;
import pl.wavesoftware.eid.exceptions.EidIllegalStateException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import java.net.URI;
import java.util.stream.StreamSupport;

import static pl.wavesoftware.eid.DefaultEid.eid;

@ApplicationScoped
class MicroprofileRestClientEventSender implements EventSender {
  private static final Logger LOGGER =
    LoggerFactory.getLogger(MicroprofileRestClientEventSender.class);

  private final EventsConfiguration eventsConfiguration;
  private final Tracer tracer;

  @Inject
  MicroprofileRestClientEventSender(EventsConfiguration eventsConfiguration, Tracer tracer) {
    this.eventsConfiguration = eventsConfiguration;
    this.tracer = tracer;
  }

  @Override
  public void send(CloudEvent ce) {
    var sink = eventsConfiguration.sinkAddress();
    var target = webTarget(sink);
    ce = injectTracing(ce);
    var res = target
      .request()
      .buildPost(Entity.entity(ce, CloudEventsProvider.CLOUDEVENT_TYPE))
      .invoke();
    handleResponse(ce, sink, res);
  }

  private CloudEvent injectTracing(CloudEvent ce) {
    var tracing = new DistributedTracingExtension();
    var span = tracer.activeSpan();
    var ctx = span.context();
    var traceparent = ctx.toString();
    var tracestate = createTracestate(ctx);
    tracing.setTraceparent(traceparent);
    tracing.setTracestate(tracestate);

    return CloudEventBuilder.v1(ce)
      .withExtension(tracing)
      .build();
  }

  private String createTracestate(SpanContext ctx) {
    return StreamSupport.stream(ctx.baggageItems().spliterator(), false)
      .map(e -> String.format("%s=%s", e.getKey(), e.getValue()))
      .reduce((s1, s2) -> String.format("%s,%s", s1, s2))
      .orElse("");
  }

  private WebTarget webTarget(URI sink) {
    return ClientBuilder.newClient()
      .register(
        new CloudEventsProvider(),
        MessageBodyReader.class, MessageBodyWriter.class, ClientRequestFilter.class
      ).target(sink);
  }

  private void handleResponse(CloudEvent ce, URI sink, Response res) {
    if (isSuccessful(res)) {
      LOGGER.info("Event {} sent to {}", ce.getId(), sink);
    } else {
      throw new EidIllegalStateException(
        eid("20200928:182242"),
        MessageFormatter.format(
          "unexpected return code {} for event {}",
          res.getStatusInfo(), ce.getId()
        ).getMessage()
      );
    }
  }

  private boolean isSuccessful(Response response) {
    return response.getStatus() >= Response.Status.OK.getStatusCode()
      && response.getStatus() < Response.Status.BAD_REQUEST.getStatusCode();
  }
}

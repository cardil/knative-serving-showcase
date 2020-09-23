package io.github.cardil.knsvng.domain.logic;

import io.cloudevents.CloudEvent;
import io.cloudevents.http.restful.ws.CloudEventsProvider;
import io.github.cardil.knsvng.config.EventsConfiguration;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;

@ApplicationScoped
@Default
class MicroprofileRestClientEventSender implements EventSender {
  private static final Logger LOGGER =
    LoggerFactory.getLogger(MicroprofileRestClientEventSender.class);

  private final EventsConfiguration eventsConfiguration;

  @Inject
  MicroprofileRestClientEventSender(EventsConfiguration eventsConfiguration) {
    this.eventsConfiguration = eventsConfiguration;
  }

  @Override
  public void send(CloudEvent ce) {
    var sink = eventsConfiguration.sinkAddress();
    var client = RestClientBuilder.newBuilder()
      .baseUri(sink)
      .register(CloudEventsProvider.class)
      .build(EventSender.class);
    client.send(ce);
    LOGGER.info("Event {} sent to {}", ce.getId(), sink);
  }
}

package io.github.cardil.knsvng.domain.logic;

import io.cloudevents.Attributes;
import io.cloudevents.CloudEvent;
import io.cloudevents.json.Json;
import io.github.cardil.knsvng.config.EventsConfiguration;
import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.propagation.Format;
import io.opentracing.propagation.TextMap;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientRequest;
import io.vertx.core.http.RequestOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.wavesoftware.eid.exceptions.EidIllegalStateException;
import pl.wavesoftware.eid.utils.EidExecutions;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

@ApplicationScoped
class HttpEventSender implements EventSender {
  private static final Logger LOGGER = LoggerFactory.getLogger(HttpEventSender.class);
  public static final String SCHEME_HTTPS = "https";
  public static final String SCHEME_HTTP = "http";

  private final Vertx vertx;
  private final EventsConfiguration eventsConfiguration;
  private final Tracer tracer;

  @Inject
  HttpEventSender(Vertx vertx, EventsConfiguration eventsConfiguration, Tracer tracer) {
    this.vertx = vertx;
    this.eventsConfiguration = eventsConfiguration;
    this.tracer = tracer;
  }

  @Override
  public void send(CloudEvent<? extends Attributes, ?> ce) {
    Span active = tracer.activeSpan();

    RequestOptions requestOptions = new RequestOptions();
    requestOptions.setHost(eventsConfiguration.brokerAddress().getHost());
    requestOptions.setPort(portOf(eventsConfiguration.brokerAddress()));
    requestOptions.setURI("/");
    URL url = EidExecutions.tryToExecute(() -> getUrl(requestOptions), "20200303:172955");
    Span requestSpan = tracer
      .buildSpan("send-cloudevent:" + ce.getAttributes().getType())
      .asChildOf(active)
      .withTag("http.url", url.toString())
      .start();
    HttpClient httpClient = vertx.createHttpClient();
    HttpClientRequest req = httpClient.post(requestOptions);
    TextMap httpHeadersCarrier = new HttpClientRequestAdapter(req);
    tracer.inject(requestSpan.context(), Format.Builtin.HTTP_HEADERS, httpHeadersCarrier);
    String ceJson = Json.encode(ce);
    try {
      req
        .handler(res -> LOGGER.info("Event sent: {}", ce.getAttributes().getId()))
        .putHeader("content-type", MediaType.APPLICATION_JSON)
        .putHeader("content-length", "" + ceJson.length())
        .write(ceJson)
        .end();
    } finally {
      requestSpan.finish();
    }
  }

  private URL getUrl(RequestOptions requestOptions) throws MalformedURLException {
    String scheme = requestOptions.isSsl() != null && requestOptions.isSsl()
      ? SCHEME_HTTPS
      : SCHEME_HTTP;
    int port;
    if (SCHEME_HTTPS.equals(scheme) && requestOptions.getPort() == 443) {
      port = -1;
    } else {
      port = requestOptions.getPort();
    }
    if (port == -1) {
      return new URL(
        String.format("%s://%s%s",
          scheme, requestOptions.getHost(), requestOptions.getURI())
      );
    }
    return new URL(
      String.format("%s://%s:%d%s",
        scheme, requestOptions.getHost(), port, requestOptions.getURI())
    );
  }

  private int portOf(URI brokerAddress) {
    int port = brokerAddress.getPort();
    if (port == -1) {
      String scheme = brokerAddress.getScheme();
      if (SCHEME_HTTP.equals(scheme)) {
        return 80;
      }
      if (SCHEME_HTTPS.equals(scheme)) {
        return 443;
      }
      throw new EidIllegalStateException("20200302:163144");
    }
    return port;
  }
}

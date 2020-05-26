package io.github.cardil.knsvng.domain.logic;


import io.cloudevents.v1.CloudEventBuilder;
import io.github.cardil.knsvng.domain.entity.Hello;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.wavesoftware.eid.utils.EidExecutions;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Validator;
import java.net.URI;
import java.util.UUID;

@ApplicationScoped
class CloudEventsHelloReceivedNotify implements HelloReceivedNotify {

  private static final Logger LOGGER = LoggerFactory.getLogger(CloudEventsHelloReceivedNotify.class);
  public static final String SOURCE_URI = "events://serving-showcase";
  public static final URI SOURCE = EidExecutions.tryToExecute(
    () -> new URI(SOURCE_URI), "20200302:154748"
  );

  private final EventSender eventSender;
  private final Validator validator;

  @Inject
  CloudEventsHelloReceivedNotify(EventSender eventSender, Validator validator) {
    this.eventSender = eventSender;
    this.validator = validator;
  }

  @Override
  public void notifyFor(Hello hello) {
    var event = CloudEventBuilder.<Hello>builder()
      .withType(Hello.class.getName())
      .withId(UUID.randomUUID().toString())
      .withSource(SOURCE)
      .withData(hello)
      .withValidator(validator)
      .build();
    try {
      eventSender.send(event);
    } catch (RuntimeException ex) {
      LOGGER.error("Can't send hello event: {}", event);
      LOGGER.error("Exception", ex);
    }
  }
}

package io.github.cardil.knsvng.domain.logic;

import io.cloudevents.CloudEvent;

public interface EventSender {
  void send(CloudEvent ce);
}

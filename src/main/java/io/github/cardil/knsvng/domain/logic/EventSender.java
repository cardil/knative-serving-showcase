package io.github.cardil.knsvng.domain.logic;

import io.cloudevents.CloudEvent;

interface EventSender {
  void send(CloudEvent ce);
}

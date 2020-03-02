package io.github.cardil.knsvng.domain.logic;

import io.github.cardil.knsvng.domain.entity.Hello;

interface HelloReceivedNotify {
  void notifyFor(Hello hello);
}

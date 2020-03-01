package io.github.cardil.knsvng.domain.entity;

import pl.wavesoftware.utils.stringify.Stringify;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public final class Hello {

  private final String greeting;
  private final String who;

  @JsonbCreator
  public Hello(
    @JsonbProperty("greeting") String greeting,
    @JsonbProperty("who") String who
  ) {
    this.greeting = greeting;
    this.who = who;
  }

  public String getGreeting() {
    return greeting;
  }

  public String getWho() {
    return who;
  }

  @Override
  public String toString() {
    return Stringify.of(this).toString();
  }
}

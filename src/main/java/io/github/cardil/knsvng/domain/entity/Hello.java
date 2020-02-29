package io.github.cardil.knsvng.domain.entity;

public final class Hello {

  private final String greeting;
  private final String who;

  public Hello(String greeting, String who) {
    this.greeting = greeting;
    this.who = who;
  }

  public String getGreeting() {
    return greeting;
  }

  public String getWho() {
    return who;
  }
}

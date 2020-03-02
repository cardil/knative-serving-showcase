package io.github.cardil.knsvng.domain.contract;

import io.github.cardil.knsvng.domain.entity.EnvironmentVariable;

import java.util.function.Supplier;

public final class GreetEnvVar implements EnvironmentVariable {
  @Override
  public String key() {
    return "GREET";
  }

  @Override
  public Supplier<String> defaultValue() {
    return () -> "Hello";
  }
}

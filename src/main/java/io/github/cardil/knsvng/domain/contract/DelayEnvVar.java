package io.github.cardil.knsvng.domain.contract;

import io.github.cardil.knsvng.domain.entity.EnvironmentVariable;

import java.util.function.Supplier;

public final class DelayEnvVar implements EnvironmentVariable {
  @Override
  public String key() {
    return "DELAY";
  }

  @Override
  public Supplier<String> defaultValue() {
    return () -> "0";
  }
}

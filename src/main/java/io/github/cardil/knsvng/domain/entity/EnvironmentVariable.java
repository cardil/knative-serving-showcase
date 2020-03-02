package io.github.cardil.knsvng.domain.entity;

import java.util.function.Supplier;

public interface EnvironmentVariable {
  String key();
  Supplier<String> defaultValue();
}

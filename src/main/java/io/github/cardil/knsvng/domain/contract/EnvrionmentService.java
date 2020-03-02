package io.github.cardil.knsvng.domain.contract;

import io.github.cardil.knsvng.domain.entity.EnvironmentVariable;

import java.util.Optional;
import java.util.function.Function;

public interface EnvrionmentService {
  <T> Optional<T> map(EnvironmentVariable variable, Function<String, T> mapper);

  default String get(EnvironmentVariable variable) {
    return map(variable, s -> s)
      .orElseGet(variable.defaultValue());
  }
}

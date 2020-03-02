package io.github.cardil.knsvng.domain.logic;

import io.github.cardil.knsvng.domain.contract.EnvrionmentService;
import io.github.cardil.knsvng.domain.entity.EnvironmentVariable;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;
import java.util.function.Function;

@ApplicationScoped
class EnvironmentServiceImpl implements EnvrionmentService {
  @Override
  public <T> Optional<T> map(EnvironmentVariable variable, Function<String, T> mapper) {
    Optional<String> opt = Optional.ofNullable(System.getenv(variable.key()));
    return opt.map(mapper);
  }
}

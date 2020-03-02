package io.github.cardil.knsvng.domain.logic;

import io.github.cardil.knsvng.domain.contract.DelayEnvVar;
import io.github.cardil.knsvng.domain.contract.EnvrionmentService;
import io.github.cardil.knsvng.domain.contract.GreetEnvVar;
import io.github.cardil.knsvng.domain.contract.HelloService;
import io.github.cardil.knsvng.domain.entity.Hello;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
class HelloServiceImpl implements HelloService {
  private static final Logger LOGGER = LoggerFactory.getLogger(HelloServiceImpl.class);
  private final EnvrionmentService envrionmentService;
  private final CounterService counterService;

  @Inject
  HelloServiceImpl(EnvrionmentService envrionmentService, CounterService counterService) {
    this.envrionmentService = envrionmentService;
    this.counterService = counterService;
  }

  @Override
  public Hello greet(String who) {
    var greeting = envrionmentService.get(new GreetEnvVar());
    envrionmentService
      .map(new DelayEnvVar(), Long::parseLong)
      .ifPresent(HelloServiceImpl::sleep);
    return new Hello(greeting, who, counterService.getNumber());
  }

  private static void sleep(long delay) {
    try {
      Thread.sleep(delay);
    } catch (InterruptedException ex) {
      LOGGER.warn("Interrupted!", ex);
      // Restore interrupted state...
      Thread.currentThread().interrupt();
    }
  }
}

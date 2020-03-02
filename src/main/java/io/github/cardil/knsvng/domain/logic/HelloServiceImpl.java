package io.github.cardil.knsvng.domain.logic;

import io.github.cardil.knsvng.config.DelayConfiguration;
import io.github.cardil.knsvng.config.GreetConfiguration;
import io.github.cardil.knsvng.domain.contract.HelloService;
import io.github.cardil.knsvng.domain.entity.Hello;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
class HelloServiceImpl implements HelloService {
  private static final Logger LOGGER = LoggerFactory.getLogger(HelloServiceImpl.class);

  private final GreetConfiguration greetConfiguration;
  private final DelayConfiguration delayConfiguration;
  private final HelloReceivedNotify helloReceivedNotify;
  private final CounterService counterService;

  @Inject
  HelloServiceImpl(
    GreetConfiguration greetConfiguration,
    DelayConfiguration delayConfiguration,
    HelloReceivedNotify helloReceivedNotify,
    CounterService counterService
  ) {
    this.greetConfiguration = greetConfiguration;
    this.delayConfiguration = delayConfiguration;
    this.helloReceivedNotify = helloReceivedNotify;
    this.counterService = counterService;
  }

  @Override
  public Hello greet(String who) {
    var greeting = greetConfiguration.greeting();
    var counter = counterService.getNumber();
    var hello = new Hello(greeting, who, counter);
    helloReceivedNotify.notifyFor(hello);
    delayConfiguration.delayInMillis()
      .ifPresent(HelloServiceImpl::sleep);
    return hello;
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

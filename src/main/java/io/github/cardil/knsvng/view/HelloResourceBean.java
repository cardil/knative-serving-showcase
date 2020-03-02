package io.github.cardil.knsvng.view;

import io.github.cardil.knsvng.domain.entity.Hello;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Path;
import java.util.Optional;


@ApplicationScoped
@Path("")
public class HelloResourceBean implements HelloResource {
  private static final Logger LOGGER = LoggerFactory.getLogger(HelloResourceBean.class);

  private int number = 0;

  @Override
  public Hello hello(String who) {
    int counter = getNumber();
    LOGGER.info("Received hello({}) for: {}", counter, who);
    String greeting = Optional
      .ofNullable(System.getenv("GREET"))
      .orElse("Hello");
    Hello hello = new Hello(greeting, who, counter);
    LOGGER.debug("Responding with: {}", hello);
    return hello;
  }

  private synchronized int getNumber() {
    number++;
    return number;
  }
}

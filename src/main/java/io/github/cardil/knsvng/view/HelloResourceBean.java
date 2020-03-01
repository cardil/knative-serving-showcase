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

  @Override
  public Hello hello(String who) {
    LOGGER.info("Received hello for: {}", who);
    String greeting = Optional
      .ofNullable(System.getenv("GREET"))
      .orElse("Hello");
    Hello hello = new Hello(greeting, who);
    LOGGER.debug("Responding with: {}", hello);
    return hello;
  }
}

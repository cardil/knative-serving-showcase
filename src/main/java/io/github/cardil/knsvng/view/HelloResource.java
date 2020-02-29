package io.github.cardil.knsvng.view;

import io.github.cardil.knsvng.domain.entity.Hello;
import org.eclipse.microprofile.openapi.annotations.Operation;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Optional;

@Path("hello")
@ApplicationScoped
public class HelloResource {

  @GET
  @Operation(
    summary = "Basic hello operation",
    description = "Greeting can be changed by setting environment variable GREET"
  )
  @Produces(MediaType.APPLICATION_JSON)
  public Hello hello(@QueryParam("who") @DefaultValue("Person") String who) {
    String greeting = Optional
      .ofNullable(System.getenv("GREET"))
      .orElse("Hello");
    return new Hello(greeting, who);
  }
}

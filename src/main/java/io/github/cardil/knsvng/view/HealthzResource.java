package io.github.cardil.knsvng.view;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/healthz")
public final class HealthzResource {

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String healthz() {
    return "OK";
  }
}

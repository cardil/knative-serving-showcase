package io.github.cardil.knsvng.domain.logic;

import io.cloudevents.CloudEvent;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
@RegisterRestClient
public interface EventSender {
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  void send(CloudEvent ce);
}

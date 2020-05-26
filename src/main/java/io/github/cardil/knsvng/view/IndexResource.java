package io.github.cardil.knsvng.view;

import io.github.cardil.knsvng.domain.entity.Project;
import org.eclipse.microprofile.openapi.annotations.Operation;

import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public interface IndexResource {
    @GET
    @Produces(MediaType.TEXT_HTML)
    @Operation(summary = "Displays a index HTML page")
    Response index();

    @OPTIONS
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
      summary = "Retrives info about project",
      description = "Information about project like maven coordinates and versions"
    )
    @Valid
    Project project();
}

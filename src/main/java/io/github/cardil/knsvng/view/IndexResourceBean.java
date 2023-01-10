package io.github.cardil.knsvng.view;

import io.github.cardil.knsvng.domain.entity.Project;
import io.quarkus.qute.Location;
import io.quarkus.qute.Template;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("")
public class IndexResourceBean implements IndexResource {
  private final Template index;
  private final Project project;

  @Inject
  IndexResourceBean(@Location("index.html") Template index, Project project) {
    this.index = index;
    this.project = project;
  }

  @Override
  public Response index() {
    var body = index
      .data("project", project)
      .render();
    return Response.ok(body, MediaType.TEXT_HTML_TYPE).build();
  }

  @Override
  public Project project() {
    return project;
  }

}

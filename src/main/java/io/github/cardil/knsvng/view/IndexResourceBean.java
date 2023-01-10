package io.github.cardil.knsvng.view;

import io.github.cardil.knsvng.config.ProjectInfo;
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
  private final ProjectInfo projectInfo;

  @Inject
  IndexResourceBean(@Location("index.html") Template index, ProjectInfo projectInfo) {
    this.index = index;
    this.projectInfo = projectInfo;
  }

  @Override
  public Response index() {
    var body = index
      .data("project", projectInfo)
      .render();
    return Response.ok(body, MediaType.TEXT_HTML_TYPE).build();
  }

  @Override
  public Project project() {
    return Project.from(projectInfo);
  }

}

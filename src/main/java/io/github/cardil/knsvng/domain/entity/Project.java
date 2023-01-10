package io.github.cardil.knsvng.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.cardil.knsvng.config.ProjectInfo;
import pl.wavesoftware.utils.stringify.Stringify;

import javax.validation.constraints.NotEmpty;

public class Project {
  @JsonProperty("group-id")
  @NotEmpty
  public String groupId;
  @JsonProperty("artifact-id")
  @NotEmpty
  public String artifactId;
  @JsonProperty
  @NotEmpty
  public String version;

  @JsonProperty("platform-version")
  @NotEmpty
  public String platformVersion;

  public static Project from(ProjectInfo info) {
    var p = new Project();
    p.groupId = info.groupId();
    p.artifactId = info.artifactId();
    p.version = info.version();
    p.platformVersion = info.platformVersion();
    return p;
  }

  @Override
  public String toString() {
    return Stringify.of(this).toString();
  }

}

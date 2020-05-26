package io.github.cardil.knsvng.domain.entity;

import io.quarkus.arc.config.ConfigProperties;
import io.quarkus.arc.config.ConfigProperties.NamingStrategy;
import pl.wavesoftware.utils.stringify.Stringify;

import javax.json.bind.annotation.JsonbProperty;
import javax.validation.constraints.NotEmpty;

@ConfigProperties(prefix = "project", namingStrategy = NamingStrategy.KEBAB_CASE)
public class Project {
  @JsonbProperty("group-id")
  @NotEmpty
  public String groupId;
  @JsonbProperty("artifact-id")
  @NotEmpty
  public String artifactId;
  @JsonbProperty
  @NotEmpty
  public String version;

  @JsonbProperty("platform-version")
  @NotEmpty
  public String platformVersion;

  @Override
  public String toString() {
    return Stringify.of(this).toString();
  }

}

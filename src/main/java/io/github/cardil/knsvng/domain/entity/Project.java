package io.github.cardil.knsvng.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.arc.config.ConfigProperties;
import io.quarkus.arc.config.ConfigProperties.NamingStrategy;
import pl.wavesoftware.utils.stringify.Stringify;

import javax.validation.constraints.NotEmpty;

@ConfigProperties(prefix = "project", namingStrategy = NamingStrategy.KEBAB_CASE)
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

  @Override
  public String toString() {
    return Stringify.of(this).toString();
  }

}

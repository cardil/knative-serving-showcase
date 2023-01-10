package io.github.cardil.knsvng.config;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(
  prefix = "project",
  namingStrategy = ConfigMapping.NamingStrategy.KEBAB_CASE
)
public interface ProjectInfo {
  String groupId();
  String artifactId();
  String version();
  String platformVersion();
}

package io.github.cardil.knsvng.view;


import java.net.URI;
import java.net.URISyntaxException;

final class Constants {
  static final URI QUARKUS_BASEURI;

  public static final String DEFAULT_TEST_URL = "http://localhost:8081";

  static {
    try {
      QUARKUS_BASEURI = new URI(System.getProperty(
        "test.url", DEFAULT_TEST_URL
      ));
    } catch (URISyntaxException ex) {
      throw new IllegalStateException(ex);
    }
  }

  private Constants() {
    // not reachable
  }
}

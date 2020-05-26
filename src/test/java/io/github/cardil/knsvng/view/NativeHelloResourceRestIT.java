package io.github.cardil.knsvng.view;

import io.quarkus.test.junit.NativeImageTest;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

@NativeImageTest
public class NativeHelloResourceRestIT extends HelloResourceTest {
  public NativeHelloResourceRestIT() {
    // Execute the same tests but in native mode.
    super(getTestClient());
  }

  private static HelloResourceNativeTestClient getTestClient() {
    return RestClientBuilder
      .newBuilder()
      .baseUri(Constants.QUARKUS_BASEURI)
      .build(HelloResourceNativeTestClient.class);
  }
}

package io.github.cardil.knsvng.view;

import io.quarkus.test.junit.QuarkusIntegrationTest;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

@QuarkusIntegrationTest
class NativeIndexResourceRestIT extends IndexResourceTest {
  public NativeIndexResourceRestIT() {
    // Execute the same tests but in native mode.
    super(getTestClient());
  }

  private static IndexResourceNativeTestClient getTestClient() {
    return RestClientBuilder
      .newBuilder()
      .baseUri(Constants.QUARKUS_BASEURI)
      .build(IndexResourceNativeTestClient.class);
  }
}

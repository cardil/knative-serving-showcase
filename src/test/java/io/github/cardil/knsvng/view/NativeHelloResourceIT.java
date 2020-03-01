package io.github.cardil.knsvng.view;

import io.quarkus.test.junit.NativeImageTest;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

@NativeImageTest
public class NativeHelloResourceIT extends HelloResourceTest {

  public NativeHelloResourceIT() {
    // Execute the same tests but in native mode.
    super(RestClientBuilder
      .newBuilder()
      .baseUri(Constants.QUARKUS_BASEURI)
      .build(HelloResourceTestClient.class)
    );
  }

}

package io.github.cardil.knsvng.view;

import io.github.cardil.knsvng.domain.entity.Hello;
import io.quarkus.test.junit.QuarkusTest;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
class HelloResourceTest {

  private final HelloResourceTestClient helloTestClient;

  @Inject
  HelloResourceTest(@RestClient HelloResourceTestClient helloTestClient) {
    this.helloTestClient = helloTestClient;
  }

  @Test
  void hello() {
    Hello hello = helloTestClient.hello("Guy");

    assertThat(hello)
      .extracting(Hello::getGreeting, Hello::getWho)
      .containsExactly("Hello", "Guy");
  }

}

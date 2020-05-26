package io.github.cardil.knsvng.view;

import io.github.cardil.knsvng.domain.entity.Hello;
import io.quarkus.test.junit.QuarkusTest;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@QuarkusTest
class HelloResourceTest {

  private final HelloResourceTestClient helloResource;

  @Inject
  HelloResourceTest(@RestClient HelloResourceTestClient helloResource) {
    this.helloResource = helloResource;
  }

  @Test
  void hello() {
    var hello = helloResource.hello("Guy");

    assertThat(hello)
      .extracting(Hello::getGreeting, Hello::getWho, Hello::getNumber)
      .containsExactly("Hello", "Guy", 1);
  }

  @Test
  void invalidHello() {
    ThrowingCallable throwingCallable = () -> helloResource.hello("small-caps");

    assertThatThrownBy(throwingCallable).hasMessageContaining("status code 500");
  }

}

package io.github.cardil.knsvng.view;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.matching.UrlPattern;
import io.github.cardil.knsvng.domain.entity.Hello;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Random;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.matching;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@QuarkusTest
@QuarkusTestResource(EventSinkWiremock.class)
class HelloResourceTest implements HasWiremockServer {

  private final HelloResourceTestClient helloResource;

  private WireMockServer wireMockServer;

  @Inject
  HelloResourceTest(@RestClient HelloResourceTestClient helloResource) {
    this.helloResource = helloResource;
  }

  @Override
  public final void setWireMockServer(WireMockServer wireMockServer) {
    this.wireMockServer = wireMockServer;
  }

  @Test
  void hello() {
    var names = List.of(
      "Alice", "Bob", "Charlie", "Doug", "Emily", "Fran", "Greg"
    );
    var rand = new Random();
    var idx = rand.nextInt(names.size());
    var name = names.get(idx);
    wireMockServer.stubFor(post(urlEqualTo("/"))
      .withHeader("Ce-Specversion", equalTo("1.0"))
      .withHeader("Ce-Source", matching(".+"))
      .withHeader("Ce-Type", matching(".+"))
      .willReturn(aResponse()
        .withStatus(Response.Status.ACCEPTED.getStatusCode()))
    );

    var hello = helloResource.hello(name);

    assertThat(hello)
      .extracting(Hello::getGreeting, Hello::getWho, Hello::getNumber)
      .containsExactly("Hello", name, 1);

    wireMockServer.verify(1,
      postRequestedFor(new UrlPattern(equalTo("/"), false))
        .withHeader("Ce-Specversion", equalTo("1.0"))
        .withHeader("Ce-Source", equalTo("//events/serving-showcase"))
        .withHeader("Ce-Type", equalTo(Hello.class.getName()))
    );
  }

  @Test
  void invalidHello() {
    ThrowingCallable throwingCallable = () -> helloResource.hello("small-caps");

    assertThatThrownBy(throwingCallable).hasMessageContaining("status code 400");
  }
}

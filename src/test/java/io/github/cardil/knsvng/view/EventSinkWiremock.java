package io.github.cardil.knsvng.view;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

import java.util.Collections;
import java.util.Map;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

public final class EventSinkWiremock implements QuarkusTestResourceLifecycleManager {
  private WireMockServer wireMockServer;

  @Override
  public Map<String, String> start() {
    wireMockServer = new WireMockServer(wireMockConfig().dynamicPort());
    wireMockServer.start();

    return Collections.singletonMap("k.sink", wireMockServer.baseUrl());
  }

  @Override
  public void stop() {
    if (null != wireMockServer) {
      wireMockServer.stop();
    }
  }

  @Override
  public void inject(Object testInstance) {
    if (testInstance instanceof HasWiremockServer) {
      ((HasWiremockServer) testInstance).setWireMockServer(wireMockServer);
    }
  }
}

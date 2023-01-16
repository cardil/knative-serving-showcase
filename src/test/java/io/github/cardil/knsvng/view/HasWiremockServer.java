package io.github.cardil.knsvng.view;

import com.github.tomakehurst.wiremock.WireMockServer;

public interface HasWiremockServer {
  void setWireMockServer(WireMockServer wireMockServer);
}

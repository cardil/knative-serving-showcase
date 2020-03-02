package io.github.cardil.knsvng.domain.logic;

import io.opentracing.propagation.TextMap;
import io.vertx.core.http.HttpClientRequest;

import java.util.Iterator;
import java.util.Map;

final class HttpClientRequestAdapter implements TextMap {
  private final HttpClientRequest request;

  HttpClientRequestAdapter(HttpClientRequest request) {
    this.request = request;
  }

  @Override
  public Iterator<Map.Entry<String, String>> iterator() {
    return request.headers().iterator();
  }

  @Override
  public void put(String key, String value) {
    request.putHeader(key, value);
  }
}

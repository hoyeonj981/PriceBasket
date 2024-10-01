package me.hoyeonj.pricebasket.adapter.out;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public record ResponseDocument(
    String url,
    int statusCode,
    Headers headers,
    String content
) {

  private static final String MESSAGE = "파라미터는 null일 수 없습니다. - ";

  public static ResponseDocument create(
      final String url,
      final int statusCode,
      final Map<String, List<String>> headers,
      final String content) {
    return new ResponseDocument(url, statusCode, new Headers(headers), content);
  }

  public ResponseDocument(String url, int statusCode, Headers headers, String content) {
    validateParameter(url);
    validateParameter(statusCode);
    validateParameter(headers);
    validateParameter(content);

    this.url = url;
    this.statusCode = statusCode;
    this.headers = new Headers(headers.toMap());
    this.content = content;
  }

  @Override
  public Headers headers() {
    return new Headers(headers.toMap());
  }

  private <T> void validateParameter(T value) {
    Objects.requireNonNull(value, MESSAGE + value.getClass());
  }
}

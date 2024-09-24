package me.hoyeonj.pricebasket.adapter.out;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ResponseDocument {

  private final String url;
  private final int statusCode;
  private final Map<String, List<String>> headers;
  private final String content;

  public ResponseDocument(final String url, final int statusCode,
      final Map<String, List<String>> headers, final String content) {
    this.url = url;
    this.statusCode = statusCode;
    this.headers = headers;
    this.content = content;
  }

  public String getUrl() {
    return url;
  }

  public int getStatusCode() {
    return statusCode;
  }

  public Map<String, List<String>> getHeaders() {
    return Collections.unmodifiableMap(headers);
  }

  public String getContent() {
    return content;
  }
}

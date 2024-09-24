package me.hoyeonj.pricebasket.adapter.out;

public class HttpRequestException extends RuntimeException {

  public HttpRequestException(final String message) {
    super(message);
  }

  public HttpRequestException(final Throwable cause) {
    super(cause);
  }
}

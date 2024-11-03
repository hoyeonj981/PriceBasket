package me.hoyeonj.pricebasket.domain;

public class NotSupportedMallException extends RuntimeException {

  public NotSupportedMallException(final String s) {
    super(s);
  }
}

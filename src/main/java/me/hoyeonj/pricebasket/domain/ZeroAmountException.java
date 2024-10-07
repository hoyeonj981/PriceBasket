package me.hoyeonj.pricebasket.domain;

public class ZeroAmountException extends RuntimeException {

  public ZeroAmountException(final String s) {
    super(s);
  }
}

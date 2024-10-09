package me.hoyeonj.pricebasket.domain;

public class ZeroTotalPriceException extends RuntimeException {

  public ZeroTotalPriceException(final String s) {
    super(s);
  }
}

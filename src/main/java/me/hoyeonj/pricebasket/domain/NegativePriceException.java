package me.hoyeonj.pricebasket.domain;

public class NegativePriceException extends RuntimeException {

  public NegativePriceException(final String s) {
    super(s);
  }
}

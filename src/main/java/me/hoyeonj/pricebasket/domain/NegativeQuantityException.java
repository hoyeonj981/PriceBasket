package me.hoyeonj.pricebasket.domain;

public class NegativeQuantityException extends RuntimeException {

  public NegativeQuantityException(final String s) {
    super(s);
  }
}

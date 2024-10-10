package me.hoyeonj.pricebasket.domain;

public class NegativeValueException extends RuntimeException {

  public NegativeValueException(final String s) {
    super(s);
  }
}

package me.hoyeonj.pricebasket.domain;

public class NegativeResultException extends RuntimeException {

  public NegativeResultException(final String s) {
    super(s);
  }
}

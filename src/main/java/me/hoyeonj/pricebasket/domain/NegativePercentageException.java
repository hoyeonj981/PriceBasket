package me.hoyeonj.pricebasket.domain;

public class NegativePercentageException extends RuntimeException {

  public NegativePercentageException(final String s) {
    super(s);
  }
}

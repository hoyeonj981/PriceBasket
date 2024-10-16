package me.hoyeonj.pricebasket.domain;

public class InvalidEmailException extends RuntimeException {

  public InvalidEmailException(final String s) {
    super(s);
  }
}

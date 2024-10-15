package me.hoyeonj.pricebasket.domain;

public class InvalidPasswordException extends RuntimeException {

  public InvalidPasswordException(final String s) {
    super(s);
  }
}

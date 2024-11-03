package me.hoyeonj.pricebasket.application.service;

public class DuplicateEmailException extends RuntimeException {

  public DuplicateEmailException(final String s) {
    super(s);
  }
}

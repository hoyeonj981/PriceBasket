package me.hoyeonj.pricebasket.application.service;

public class NotBasketOwnerException extends RuntimeException {

  public NotBasketOwnerException(final String s) {
    super(s);
  }
}

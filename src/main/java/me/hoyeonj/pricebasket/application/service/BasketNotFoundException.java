package me.hoyeonj.pricebasket.application.service;

public class BasketNotFoundException extends RuntimeException {

  public BasketNotFoundException(final String message) {
    super(message);
  }
}

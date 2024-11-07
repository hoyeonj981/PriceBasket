package me.hoyeonj.pricebasket.application.service;

public class ClientNotFoundException extends RuntimeException {

  public ClientNotFoundException(final String s) {
    super(s);
  }
}

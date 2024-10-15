package me.hoyeonj.pricebasket.domain;

public class EmptyCategoryNameException extends RuntimeException {

  public EmptyCategoryNameException(final String s) {
    super(s);
  }
}

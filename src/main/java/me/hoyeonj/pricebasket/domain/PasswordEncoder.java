package me.hoyeonj.pricebasket.domain;

public interface PasswordEncoder {

  String encode(final String rawPassword);
}

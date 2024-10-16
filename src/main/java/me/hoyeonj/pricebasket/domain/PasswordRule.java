package me.hoyeonj.pricebasket.domain;

public interface PasswordRule {

  void validate(final String rawPassword) throws InvalidPasswordException;
}

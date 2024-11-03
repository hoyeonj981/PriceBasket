package me.hoyeonj.pricebasket.domain.rules;

import me.hoyeonj.pricebasket.domain.InvalidPasswordException;
import me.hoyeonj.pricebasket.domain.PasswordRule;

public class PasswordLength implements PasswordRule {

  private final int minLength;
  private final int maxLength;

  public PasswordLength(final int minLength, final int maxLength) {
    validateLengthBound(minLength, maxLength);
    this.minLength = minLength;
    this.maxLength = maxLength;
  }

  private void validateLengthBound(final int minLength, final int maxLength) {
    if (minLength <= 0) {
      throw new IllegalArgumentException("패스워드 최소 길이가 0보다 작습니다. - " + minLength);
    }

    if (maxLength < minLength) {
      throw new IllegalArgumentException("패스워드 최소 길이가 최대 길이보다 클 수 없습니다.");
    }
  }

  @Override
  public void validate(final String rawPassword) throws InvalidPasswordException {
    final var passwordLength = rawPassword.length();
    if (minLength > passwordLength || passwordLength > maxLength) {
      throw new InvalidPasswordException(
          String.format("패스워드 길이는 %d 와 %d 사이만 허용합니다.", minLength, maxLength)
      );
    }
  }
}

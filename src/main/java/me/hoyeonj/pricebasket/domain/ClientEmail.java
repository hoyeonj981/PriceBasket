package me.hoyeonj.pricebasket.domain;

import java.util.Objects;
import java.util.regex.Pattern;

public class ClientEmail {

  private static final String EMAIL_REGEX =
      "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
  private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

  private final String value;

  public static ClientEmail from(final String email) {
    validateEmail(email);
    return new ClientEmail(email);
  }

  public static ClientEmail createGuestEmail(final String id) {
    return new ClientEmail(id);
  }

  private static void validateEmail(final String value) {
    if (Objects.isNull(value) || !isMatches(value)) {
      throw new InvalidEmailException("유효하지 않는 이메일 형식입니다. - " + value);
    }
  }

  private ClientEmail(final String value) {
    this.value = value;
  }

  private static boolean isMatches(final String value) {
    return EMAIL_PATTERN.matcher(value).matches();
  }

  public String getEmail() {
    return value;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final ClientEmail that = (ClientEmail) o;
    return Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }
}

package me.hoyeonj.pricebasket.domain;

import java.util.Objects;
import java.util.regex.Pattern;

public class ItemUrl {

  private static final String HTTPS_URL_REGEX =
      "^(https?://)?([\\w-]+\\.)+[\\w-]+(:\\d{1,5})?(/[\\w-./?%&=]*)?$";
  private static final Pattern URL_PATTERN = Pattern.compile(HTTPS_URL_REGEX);

  private final String value;

  public static ItemUrl of(final String value) {
    return new ItemUrl(value);
  }

  private ItemUrl(final String value) {
    validateUrl(value);
    this.value = value;
  }

  private void validateUrl(final String value) {
    if (Objects.isNull(value) || isValidUrlPattern(value)) {
      throw new InvalidUrlException("유효하지 않는 URL입니다. - " + value);
    }
  }

  private boolean isValidUrlPattern(final String value) {
    return !URL_PATTERN.matcher(value).matches();
  }

  public String getValue() {
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
    final ItemUrl itemUrl = (ItemUrl) o;
    return Objects.equals(value, itemUrl.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }
}

package me.hoyeonj.pricebasket.domain;

import java.util.Objects;
import java.util.UUID;

public class CategoryId {

  static final String UNCATEGORIZED = "UNCATEGORIZED";

  private final String value;

  static CategoryId uncategorized() {
    return new CategoryId(UNCATEGORIZED);
  }

  static CategoryId create() {
    return new CategoryId(UUID.randomUUID().toString());
  }

  static CategoryId from(final String id) {
    return new CategoryId(id);
  }

  private CategoryId(final String value) {
    this.value = value;
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
    final CategoryId that = (CategoryId) o;
    return Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }
}

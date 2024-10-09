package me.hoyeonj.pricebasket.domain;

import java.util.Objects;
import java.util.UUID;

class ItemId {
  private final String value;

  static ItemId create() {
    return new ItemId(UUID.randomUUID().toString());
  }

  static ItemId from(final String id) {
    return new ItemId(id);
  }

  ItemId(final String value) {
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
    final ItemId itemId = (ItemId) o;
    return Objects.equals(value, itemId.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public String toString() {
    return "ItemId{" +
        "value='" + value + '\'' +
        '}';
  }
}

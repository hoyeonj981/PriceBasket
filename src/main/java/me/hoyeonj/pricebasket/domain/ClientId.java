package me.hoyeonj.pricebasket.domain;

import java.util.Objects;
import java.util.UUID;

public class ClientId {

  private final String value;

  static ClientId create() {
    return new ClientId(UUID.randomUUID().toString());
  }

  static ClientId createTemporary() {
    return new ClientId("guest-" + UUID.randomUUID().toString());
  }

  static ClientId from(final String id) {
    return new ClientId(id);
  }

  ClientId(final String value) {
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
    final ClientId clientId = (ClientId) o;
    return Objects.equals(value, clientId.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }
}

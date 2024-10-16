package me.hoyeonj.pricebasket.domain;

import java.util.Objects;

public class ClientPassword {

  private final String value;

  public static ClientPassword create(final String rawPassword, final PasswordEncoder encoder,
      final PasswordValidator validator){
    validator.validate(rawPassword);
    return new ClientPassword(encoder.encode(rawPassword));
  }

  public static ClientPassword createTemporary() {
    return new ClientPassword("");
  }

  private ClientPassword(final String value) {
    this.value = value;
  }

  public String getPassword() {
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
    final ClientPassword that = (ClientPassword) o;
    return Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }
}

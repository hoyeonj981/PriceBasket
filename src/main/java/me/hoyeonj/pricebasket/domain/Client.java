package me.hoyeonj.pricebasket.domain;

import java.util.Objects;

public class Client {

  private final ClientId clientId;
  private final ClientEmail email;
  private final ClientPassword password;
  private final ClientType clientType;

  public static Client create(final String email, final String rawPassword,
      final PasswordEncoder encoder, final PasswordValidator validator) {
    final var clientEmail = ClientEmail.from(email);
    final var clientPassword = ClientPassword.create(rawPassword, encoder, validator);
    return new Client(ClientId.create(), clientEmail, clientPassword, ClientType.REGISTERD);
  }

  public static Client create(final String id, final String email, final String rawPassword,
      final PasswordEncoder encoder, final PasswordValidator validator) {
    final var clientEmail = ClientEmail.from(email);
    final var clientPassword = ClientPassword.create(rawPassword, encoder, validator);
    return new Client(ClientId.from(id), clientEmail, clientPassword, ClientType.REGISTERD);
  }

  private Client(final ClientId clientId, final ClientEmail email, final ClientPassword password,
      final ClientType clientType) {
    this.clientId = clientId;
    this.email = email;
    this.password = password;
    this.clientType = clientType;
  }

  public String getClientId() {
    return this.clientId.getValue();
  }

  public String getEmail() {
    return this.email.getEmail();
  }

  public String getPassword() {
    return this.password.getPassword();
  }

  public ClientType getClientType() {
    return clientType;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final Client client = (Client) o;
    return Objects.equals(clientId, client.clientId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(clientId);
  }
}

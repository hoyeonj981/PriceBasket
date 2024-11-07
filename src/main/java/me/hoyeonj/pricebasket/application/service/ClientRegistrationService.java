package me.hoyeonj.pricebasket.application.service;

import me.hoyeonj.pricebasket.application.in.RegisterClientUseCase;
import me.hoyeonj.pricebasket.application.in.dto.RegistrationCommand;
import me.hoyeonj.pricebasket.application.in.dto.RegistrationResult;
import me.hoyeonj.pricebasket.application.out.ClientCommandPort;
import me.hoyeonj.pricebasket.application.out.ClientQueryPort;
import me.hoyeonj.pricebasket.domain.Client;
import me.hoyeonj.pricebasket.domain.ClientEmail;
import me.hoyeonj.pricebasket.domain.InvalidEmailException;
import me.hoyeonj.pricebasket.domain.PasswordEncoder;
import me.hoyeonj.pricebasket.domain.service.PasswordValidator;

public class ClientRegistrationService implements RegisterClientUseCase {

  private final ClientCommandPort clientCommandPort;
  private final ClientQueryPort clientQueryPort;
  private final PasswordEncoder passwordEncoder;
  private final PasswordValidator validator;

  public ClientRegistrationService(
      final ClientCommandPort clientCommandPort,
      final ClientQueryPort clientQueryPort,
      final PasswordEncoder passwordEncoder,
      final PasswordValidator validator
  ) {
    this.clientCommandPort = clientCommandPort;
    this.clientQueryPort = clientQueryPort;
    this.passwordEncoder = passwordEncoder;
    this.validator = validator;
  }

  @Override
  public RegistrationResult register(final RegistrationCommand command) {
    validateEmailNotExists(command.email());
    final var newClient = Client.create(command.email(), command.password(), passwordEncoder, validator);
    final var savedClient = clientCommandPort.save(newClient);
    return createRegistrationResult(savedClient);
  }

  private void validateEmailNotExists(final String email) {
    try {
      final var clientEmail = ClientEmail.from(email);
      if (clientQueryPort.existsByEmail(clientEmail)) {
        throw new DuplicateEmailException("이미 존재하는 이메일입니다. - " + email);
      }
    } catch (InvalidEmailException e) {
      throw e;
    }
  }

  private RegistrationResult createRegistrationResult(final Client savedClient) {
    return new RegistrationResult(
        savedClient.getClientId(),
        savedClient.getEmail()
    );
  }
}

package me.hoyeonj.pricebasket.application.service;

import me.hoyeonj.pricebasket.application.in.RegisterClientUseCase;
import me.hoyeonj.pricebasket.application.in.dto.RegistrationCommand;
import me.hoyeonj.pricebasket.application.in.dto.RegistrationResult;
import me.hoyeonj.pricebasket.application.out.ClientRepository;
import me.hoyeonj.pricebasket.domain.Client;
import me.hoyeonj.pricebasket.domain.PasswordEncoder;
import me.hoyeonj.pricebasket.domain.service.PasswordValidator;

public class ClientRegistrationService implements RegisterClientUseCase {

  private final ClientRepository repository;
  private final PasswordEncoder passwordEncoder;
  private final PasswordValidator validator;

  public ClientRegistrationService(
      final ClientRepository repository,
      final PasswordEncoder passwordEncoder,
      final PasswordValidator validator
  ) {
    this.repository = repository;
    this.passwordEncoder = passwordEncoder;
    this.validator = validator;
  }

  @Override
  public RegistrationResult register(final RegistrationCommand command) {
    validateEmailNotExists(command.email());
    final var newClient = Client.create(command.email(), command.password(), passwordEncoder, validator);
    final var savedClient = repository.save(newClient);
    return createRegistrationResult(savedClient);
  }

  private void validateEmailNotExists(final String email) {
    if (repository.existsByEmail(email)) {
      throw new DuplicateEmailException("이미 존재하는 이메일입니다. - " + email);
    }
  }

  private RegistrationResult createRegistrationResult(final Client savedClient) {
    return new RegistrationResult(
        savedClient.getClientId(),
        savedClient.getEmail()
    );
  }
}

package me.hoyeonj.pricebasket.domain.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import me.hoyeonj.pricebasket.domain.InvalidPasswordException;
import me.hoyeonj.pricebasket.domain.PasswordRule;

public class PasswordValidator {

  private final List<PasswordRule> validators;

  public PasswordValidator(final List<PasswordRule> validators) {
    this.validators = Collections.unmodifiableList(new ArrayList<>(validators));
  }

  public void validate(final String rawPassword) {
    final var errors = new ArrayList<String>();
    validateRawPassword(rawPassword, errors);
    throwExceptionIfErrorsExist(errors);
  }

  private void throwExceptionIfErrorsExist(final List<String> errors) {
    if (!errors.isEmpty()) {
      throw new InvalidPasswordException("Invalid Password - \n" + String.join(", ", errors));
    }
  }

  private void validateRawPassword(final String rawPassword, final List<String> errors) {
    for (PasswordRule validator : validators) {
      try {
        validator.validate(rawPassword);
      } catch (InvalidPasswordException e) {
        errors.add(e.getMessage() + "\n");
      }
    }
  }
}

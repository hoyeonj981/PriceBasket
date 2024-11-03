package me.hoyeonj.pricebasket.domain.service;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import me.hoyeonj.pricebasket.domain.InvalidPasswordException;
import me.hoyeonj.pricebasket.domain.PasswordRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PasswordValidatorTest {

  @Mock
  private PasswordRule rule1;

  @Mock
  private PasswordRule rule2;

  private PasswordValidator passwordValidator;

  @BeforeEach
  void setUp() {
    passwordValidator = new PasswordValidator(Arrays.asList(rule1, rule2));
  }

  @DisplayName("유효하지 않는 패스워드는 해당 예외 메시지와 함께 예외가 발생한다 - 1개 위반")
  @Test
  void throwExceptionIfPasswordIsInvalidAndViolationIsOne() {
    var givenPassword = "invalidPassword";
    var message1 = "password violation 1";
    Mockito.doThrow(new InvalidPasswordException(message1)).when(rule1).validate(givenPassword);

    assertThatThrownBy(() -> passwordValidator.validate(givenPassword))
        .isInstanceOf(InvalidPasswordException.class)
        .hasMessageContaining(message1);
    verify(rule1).validate(givenPassword);
  }

  @DisplayName("유효하지 않는 패스워드는 해당 예외 메시지와 함께 예외가 발생한다 - 2개 위반")
  @Test
  void throwExceptionIfPasswordIsInvalidAndViolationIsTwo() {
    var givenPassword = "invalidPassword";
    var message1 = "password violation 1";
    var message2 = "password violation 2";
    doThrow(new InvalidPasswordException(message1)).when(rule1).validate(givenPassword);
    doThrow(new InvalidPasswordException(message2)).when(rule2).validate(givenPassword);

    assertThatThrownBy(() -> passwordValidator.validate(givenPassword))
        .isInstanceOf(InvalidPasswordException.class)
        .hasMessageContaining(message1)
        .hasMessageContaining(message2);
    verify(rule1).validate(givenPassword);
    verify(rule2).validate(givenPassword);
  }

  @DisplayName("유효한 패스워드는 예외가 발생하지 않는다")
  @Test
  void validPasswordDoesNotThrowException() {
    var givenPassword = "validPassword";
    doNothing().when(rule1).validate(givenPassword);
    doNothing().when(rule2).validate(givenPassword);

    assertThatCode(() -> passwordValidator.validate(givenPassword))
        .doesNotThrowAnyException();
  }
}

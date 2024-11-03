package me.hoyeonj.pricebasket.domain.rules;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import me.hoyeonj.pricebasket.domain.InvalidPasswordException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PasswordLengthTest {

  @DisplayName("패스워드 최소 길이가 0보다 작다면 예외가 발생한다")
  @Test
  void throwExceptionWhenMinimalLengthIsUnderZero() {
    var min = 0;
    var max = 1;

    assertThatThrownBy(() -> new PasswordLength(min, max))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @DisplayName("패스워드 최대 길이가 최소 길이보다 작다면 예외가 발생한다")
  @Test
  void throwExceptionWhenMaximalLengthIsUnderMinimalLength() {
    var min = 12;
    var max = 8;

    assertThatThrownBy(() -> new PasswordLength(min, max))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @DisplayName("주어진 제한 길이에 충족하지 않는다면 예외가 발생한다")
  @ParameterizedTest
  @CsvSource({
      "1, 2, hello",
      "8, 12, adfsdafsdafsda",
      "1, 1, ab"
  })
  void throwExceptionIfGivenLengthOfLimitIsNotMet(int min, int max, String givenPassword) {
    var passwordValidator = new PasswordLength(min, max);

    assertThatThrownBy(() -> passwordValidator.validate(givenPassword))
        .isInstanceOf(InvalidPasswordException.class);
  }

  @DisplayName("주어진 길이 조건에 만족하는 패스워드는 예외가 발생하지 않는다")
  @ParameterizedTest
  @CsvSource({
      "1, 1, a",
      "8, 12, helloworld",
      "1, 3, abc",
      "1, 3, a"
  })
  void IfGivenLengthOfLimitIsMetDoesNotThrowException(int min, int max, String givenPassword) {
    var passwordValidator = new PasswordLength(min, max);

    assertThatCode(() -> passwordValidator.validate(givenPassword))
        .doesNotThrowAnyException();
  }
}

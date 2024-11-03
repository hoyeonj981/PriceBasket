package me.hoyeonj.pricebasket.domain.rules;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import me.hoyeonj.pricebasket.domain.InvalidPasswordException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class DefaultPasswordCompositionTest {

  @DisplayName("영문, 숫자, 특수문자가 모두 조합되어야 예외가 발생하지 않는다")
  @Test
  void PasswordShouldHaveAlphabetAndDigitAndSpecialChar() {
    var givenPassword = "hello123!";

    var passwordValidator = new DefaultPasswordComposition();

    assertThatCode(() -> passwordValidator.validate(givenPassword))
        .doesNotThrowAnyException();
  }

  @DisplayName("조합에 영문이 없다면 예외가 발생한다")
  @ParameterizedTest
  @CsvSource({
      "안녕하세요1!",
      "@#$#@1231",
      "::#@$@#12314",
      "!1안녕하세요"
  })
  void throwExceptionIfPasswordHasNotAlphabets(String givenPassword) {
    var passwordValidator = new DefaultPasswordComposition();

    assertThatThrownBy(() -> passwordValidator.validate(givenPassword))
        .isInstanceOf(InvalidPasswordException.class);
  }

  @DisplayName("조합에 숫자가 없다면 예외가 발생한다")
  @ParameterizedTest
  @CsvSource({
      "helloworld!",
      "!helloworld",
      "asdfsdad#@@#$",
      "@#dsf@#$sfsa"
  })
  void throwExceptionIfPasswordHasNotDigit(String givenPassword) {
    var passwordValidator = new DefaultPasswordComposition();

    assertThatThrownBy(() -> passwordValidator.validate(givenPassword))
        .isInstanceOf(InvalidPasswordException.class);
  }

  @DisplayName("조합에 특수 문자가 없다면 예외가 발생한다")
  @ParameterizedTest
  @CsvSource({
      "helloworld123",
      "pass12312word1234",
      "1231test23123",
      "1ds24sd343sf34",
  })
  void throwExceptionIfPasswordHasNotSpecialChar(String givenPassword) {
    var passwordValidator = new DefaultPasswordComposition();

    assertThatThrownBy(() -> passwordValidator.validate(givenPassword))
        .isInstanceOf(InvalidPasswordException.class);
  }
}

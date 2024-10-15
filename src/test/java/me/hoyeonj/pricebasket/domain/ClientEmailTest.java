package me.hoyeonj.pricebasket.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ClientEmailTest {

  @DisplayName("유효한 이메일 형식이 아니라면 예외가 발생한다")
  @ParameterizedTest
  @ValueSource(strings = {
      "",
      " ",
      "username",
      "@domain.com",
      "username@.com",
      ".username@domain.com",
      "username@domain",
      "username;;$(@#@domain.com",
      "user name@domain.com",
      "username@domain.c",
      "username@domain.verylongtld"
  })
  void throwExceptionWhenInvalidEmailPattern(String given) {
    assertThatThrownBy(() -> new ClientEmail(given))
        .isInstanceOf(InvalidEmailException.class);
  }

  @DisplayName("유효한 이메일 형식일 경우 객체를 생성한다")
  @Test
  void createObjectWhenValidEmailPattern() {
    var given = "hoyeonj981@google.com";

    var actual = new ClientEmail(given);

    assertThat(actual.getEmail()).isEqualTo(given);
  }

  @DisplayName("이메일이 같다면 같은 객체이다")
  @Test
  void SameObjectWhenEmailIsSame() {
    var given1 = "hoyeonj981@google.com";
    var given2 = "hoyeonj981@google.com";

    var email1 = new ClientEmail(given1);
    var email2 = new ClientEmail(given2);

    assertThat(email1).isEqualTo(email2);
  }

  @DisplayName("이메일이 다르다면 다른 객체이다")
  @Test
  void NotSameObjectWhenEmailIsDifferent() {
    var given1 = "hoyeonj981@google.com";
    var given2 = "ghdus8938@naver.com";

    var email1 = new ClientEmail(given1);
    var email2 = new ClientEmail(given2);

    assertThat(email1).isNotEqualTo(email2);
  }

  @DisplayName("같은 객체는 hashcode값이 같다")
  @Test
  void SameHashcodeWhenObjectIsSame() {
    var given1 = "hoyeonj981@google.com";
    var given2 = "hoyeonj981@google.com";

    var email1 = new ClientEmail(given1);
    var email2 = new ClientEmail(given2);

    assertThat(email1.hashCode()).isEqualTo(email2.hashCode());
  }

  @DisplayName("다른 객체는 hashcode값이 다르다")
  @Test
  void NotSameHashCodeWhenObjectIsDifferent() {
    var given1 = "hoyeonj981@google.com";
    var given2 = "ghdus8938@naver.com";

    var email1 = new ClientEmail(given1);
    var email2 = new ClientEmail(given2);

    assertThat(email1.hashCode()).isNotEqualTo(email2.hashCode());
  }
}

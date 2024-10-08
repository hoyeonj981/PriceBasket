package me.hoyeonj.pricebasket.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ItemUrlTest {

  @DisplayName("유효하지 않는 URL 형식은 예외가 발생한다.")
  @ParameterizedTest
  @ValueSource(strings = {
      "",
      " ",
      "ftp://test.com",
      "test",
      "/image/test.jpg"
  })
  void throwExceptionWhenInvalidUrlPattern(String given) {
    assertThatThrownBy(() -> ItemUrl.of(given))
        .isInstanceOf(InvalidUrlException.class);
  }

  @DisplayName("유효한 URL일 경우 ItemUrl 객체를 생성한다")
  @ParameterizedTest
  @ValueSource(strings = {
      "https://test.com",
      "http://test.com:8080",
      "https://test.com:8080/image/image.jpg",
      "https://test.com/search?key1=value1&key2=value2"
  })
  void createItemUrlWhenUrlIsValid(String given) {
    var itemUrl = ItemUrl.of(given);

    assertThat(itemUrl.getValue()).isEqualTo(given);
  }
}

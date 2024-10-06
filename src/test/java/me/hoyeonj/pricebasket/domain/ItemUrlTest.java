package me.hoyeonj.pricebasket.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class ItemUrlTest {

  @DisplayName("유효하지 않는 URL 형식은 예외가 발생한다.")
  @ParameterizedTest
  @MethodSource("invalidUrls")
  void throw_exception_when_invalid_url_pattern(String given) {
    assertThatThrownBy(() -> new ItemUrl(given))
        .isInstanceOf(InvalidUrlException.class);
  }

  private static Stream<String> invalidUrls() {
    return Stream.of(
        "",
        " ",
        "ftp://test.com",
        "test",
        "/image/test.jpg"
    );
  }

  @DisplayName("유효한 URL일 경우 ItemUrl 객체를 생성한다")
  @ParameterizedTest
  @MethodSource("validUrls")
  void create_ItemUrl_when_url_is_valid(String given) {
    var itemUrl = new ItemUrl(given);

    assertThat(itemUrl.getValue()).isEqualTo(given);
  }

  private static Stream<String> validUrls() {
    return Stream.of(
        "https://test.com",
        "http://test.com:8080",
        "https://test.com:8080/image/image.jpg",
        "https://test.com/search?key1=value1&key2=value2"
    );
  }
}
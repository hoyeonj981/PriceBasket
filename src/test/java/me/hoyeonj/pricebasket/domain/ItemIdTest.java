package me.hoyeonj.pricebasket.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ItemIdTest {

  private static final String UUID_REGEX =
      "^[0-9a-fA-F]{8}"
      + "-[0-9a-fA-F]{4}"
      + "-[0-9a-fA-F]{4}"
      + "-[0-9a-fA-F]{4}"
      + "-[0-9a-fA-F]{12}$";

  @DisplayName("주어진 문자열을 기반으로 객체를 생성한다")
  @Test
  void create_object_using_given_string() {
    var givenId = "11111";

    var itemId = ItemId.from(givenId);

    assertThat(itemId.getValue()).isEqualTo(givenId);
  }

  @DisplayName("무작위 id는 UUID 형식으로 생성된다.")
  @Test
  void withoutId_should_create_valid_UUID() {
    var itemId = ItemId.create();
    var id = itemId.getValue();

    assertThat(id.matches(UUID_REGEX)).isTrue();
  }
}

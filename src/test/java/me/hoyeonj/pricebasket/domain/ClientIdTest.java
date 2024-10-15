package me.hoyeonj.pricebasket.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ClientIdTest {

  private static final String UUID_REGEX =
      "^[0-9a-fA-F]{8}"
          + "-[0-9a-fA-F]{4}"
          + "-[0-9a-fA-F]{4}"
          + "-[0-9a-fA-F]{4}"
          + "-[0-9a-fA-F]{12}$";

  @DisplayName("주어진 문자열을 기반으로 객체를 생성한다")
  @Test
  void createObjectUsingGivenString() {
    var givenId = "11111";

    var clientId = ClientId.from(givenId);

    assertThat(clientId.getValue()).isEqualTo(givenId);
  }

  @DisplayName("무작위 id는 UUID 형식으로 생성된다.")
  @Test
  void withoutIdShouldCreateValidUuid() {
    var clientId = ClientId.create();
    var id = clientId.getValue();

    assertThat(id.matches(UUID_REGEX)).isTrue();
  }

  @DisplayName("id 값이 같다면 같은 객체이다")
  @Test
  void SameObjectIfIdValueIsSame() {
    var givenId1 = "1";
    var givenId2 = "1";

    var id1 = ClientId.from(givenId1);
    var id2 = ClientId.from(givenId2);

    assertThat(id1).isEqualTo(id2);
  }

  @DisplayName("id 값이 다르다면 다른 객체이다")
  @Test
  void NotSameObjectIfIdValueIsDifferent() {
    var givenId1 = "1";
    var givenId2 = "2";

    var id1 = ClientId.from(givenId1);
    var id2 = ClientId.from(givenId2);

    assertThat(id1).isNotEqualTo(id2);
  }

  @DisplayName("id 값이 같다면 같은 hashcode를 가진다")
  @Test
  void SameHashCodeIfIdValueIsSame() {
    var givenId1 = "1";
    var givenId2 = "1";

    var id1 = ClientId.from(givenId1);
    var id2 = ClientId.from(givenId2);

    assertThat(id1.hashCode()).isEqualTo(id2.hashCode());
  }
}

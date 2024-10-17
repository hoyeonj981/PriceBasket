package me.hoyeonj.pricebasket.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CategoryIdTest {

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

    var categoryId = CategoryId.from(givenId);

    assertThat(categoryId.getValue()).isEqualTo(givenId);
  }

  @DisplayName("무작위 id는 UUID 형식으로 생성된다.")
  @Test
  void withoutIdShouldCreateValidUuid() {
    var categoryId = CategoryId.create();
    var id = categoryId.getValue();

    assertThat(id.matches(UUID_REGEX)).isTrue();
  }

  @DisplayName("지정이 안된 카테고리 id값은 고정된 값이다")
  @Test
  void unspecifiedCategoryIdValueIsFixed() {
    var uncategorized = CategoryId.uncategorized();
    var id = uncategorized.getValue();

    assertThat(id).isEqualTo(CategoryId.UNCATEGORIZED);
  }

  @DisplayName("id 값이 같다면 같은 객체이다")
  @Test
  void SameObjectIfIdValueIsSame() {
    var givenId1 = "1";
    var givenId2 = "1";

    var id1 = CategoryId.from(givenId1);
    var id2 = CategoryId.from(givenId2);

    assertThat(id1).isEqualTo(id2);
  }

  @DisplayName("id 값이 다르다면 다른 객체이다")
  @Test
  void NotSameObjectIfIdValueIsDifferent() {
    var givenId1 = "1";
    var givenId2 = "2";

    var id1 = CategoryId.from(givenId1);
    var id2 = CategoryId.from(givenId2);

    assertThat(id1).isNotEqualTo(id2);
  }

  @DisplayName("id 값이 같다면 같은 hashcode를 가진다")
  @Test
  void SameHashCodeIfIdValueIsSame() {
    var givenId1 = "1";
    var givenId2 = "1";

    var id1 = CategoryId.from(givenId1);
    var id2 = CategoryId.from(givenId2);

    assertThat(id1.hashCode()).isEqualTo(id2.hashCode());
  }
}

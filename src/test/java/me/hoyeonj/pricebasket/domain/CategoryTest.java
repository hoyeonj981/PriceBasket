package me.hoyeonj.pricebasket.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CategoryTest {

  @DisplayName("카테고리 이름이 공백일 경우 예외가 발생한다")
  @ParameterizedTest
  @ValueSource(strings = {
      "", " "
  })
  void throwExceptionWhenCategoryNameIsBlank(String given) {
    assertThatThrownBy(() -> new Category(CategoryId.create(), given))
        .isInstanceOf(EmptyCategoryNameException.class);
  }

  @DisplayName("카테고리는 카테고리 id와 이름으로 생성한다")
  @Test
  void createCategoryWithCategroyIdAndName() {
    var givenId = "1";
    var givenName = "category1";

    var actual = new Category(CategoryId.from(givenId), givenName);

    assertThat(actual.getCategoryId()).isEqualTo(givenId);
    assertThat(actual.getName()).isEqualTo(givenName);
  }

  @DisplayName("id값과 이름이 같다면 같은 객체이다")
  @Test
  void SameObjectIfIdAndNameIsSame() {
    var givenId1 = "1";
    var givenName1 = "category1";
    var givenId2 = "1";
    var givenName2 = "category1";

    var category1 = new Category(CategoryId.from(givenId1), givenName1);
    var category2 = new Category(CategoryId.from(givenId2), givenName2);

    assertThat(category1).isEqualTo(category2);
  }

  @DisplayName("id값과 이름이 다르다면 같은 객체이다")
  @Test
  void NotSameObjectIfIdAndNameIsDifferent() {
    var givenId1 = "1";
    var givenName1 = "category1";
    var givenId2 = "2";
    var givenName2 = "category2";

    var category1 = new Category(CategoryId.from(givenId1), givenName1);
    var category2 = new Category(CategoryId.from(givenId2), givenName2);

    assertThat(category1).isNotEqualTo(category2);
  }

  @DisplayName("id값과 이름이 같다면 같은 hashcode를 가진다")
  @Test
  void SameHashCodeIfIdAndNameIsSame() {
    var givenId1 = "1";
    var givenName1 = "category1";
    var givenId2 = "1";
    var givenName2 = "category1";

    var category1 = new Category(CategoryId.from(givenId1), givenName1);
    var category2 = new Category(CategoryId.from(givenId2), givenName2);

    assertThat(category1.hashCode()).isEqualTo(category2.hashCode());
  }
}
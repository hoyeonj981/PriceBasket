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
}
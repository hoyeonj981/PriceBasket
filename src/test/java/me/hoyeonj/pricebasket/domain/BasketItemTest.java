package me.hoyeonj.pricebasket.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BasketItemTest {

  @DisplayName("상품 id를 받아 객체를 생성한다")
  @Test
  void createBasketItemWWithId() {
    var givenId = "1";

    var actual = new BasketItem(givenId);

    assertThat(actual.getItemId()).isEqualTo(givenId);
    assertThat(actual.getQuantity()).isEqualTo(0);
  }

  @DisplayName("상품 id와 수량을 받아 객체를 생성한다")
  @Test
  void createBasketItemWithIdAndQuantity() {
    var givenId = "1";
    var givenQuantity = 5;

    var actual = new BasketItem(givenId, givenQuantity);

    assertThat(actual.getItemId()).isEqualTo(givenId);
    assertThat(actual.getQuantity()).isEqualTo(givenQuantity);
  }

  @DisplayName("상품 수량을 주어진 값 만큼 올린다")
  @Test
  void increaseQuantityWithGivenAmount() {
    var givenId = "1";
    var givenAmount = 3;
    var basketItem = new BasketItem(givenId);

    var actual = basketItem.increaseQuantity(givenAmount);

    assertThat(actual).isEqualTo(givenAmount);
  }

  @DisplayName("상품 수량을 1개 올린다")
  @Test
  void increaseQuantityOne() {
    var givenId = "1";
    var givenAmount = 3;
    var basketItem = new BasketItem(givenId, givenAmount);
    var expected = givenAmount + 1;

    var actual = basketItem.increaseQuantityOne();

    assertThat(actual).isEqualTo(expected);
  }

  @DisplayName("상품 수량을 주어진 값 만큼 내린다")
  @Test
  void decreaseQuantityWithGivenAmount() {
    var givenId = "1";
    var givenQuantity = 5;
    var basketItem = new BasketItem(givenId, givenQuantity);
    var expected = 3;

    var actual = basketItem.decreaseQuantity(givenQuantity - expected);

    assertThat(actual).isEqualTo(expected);
  }

  @DisplayName("상품 수량을 1개 내린다")
  @Test
  void decreaseQuantityOne() {
    var givenId = "1";
    var givenQuantity = 3;
    var basketItem = new BasketItem(givenId, givenQuantity);
    var expected = givenQuantity - 1;

    var actual = basketItem.decreaseQuantityOne();

    assertThat(actual).isEqualTo(expected);
  }
}

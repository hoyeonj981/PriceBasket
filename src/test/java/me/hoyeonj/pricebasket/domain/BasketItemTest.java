package me.hoyeonj.pricebasket.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BasketItemTest {

  @DisplayName("상품 id를 받아 객체를 생성한다")
  @Test
  void createBasketItemWWithId() {
    var givenId = "1";
    var price = PriceWon.of("1000");

    var actual = BasketItem.create(givenId, price);

    assertThat(actual.getItemId()).isEqualTo(givenId);
    assertThat(actual.getQuantity()).isEqualTo(1);
  }

  @DisplayName("상품 id와 수량을 받아 객체를 생성한다")
  @Test
  void createBasketItemWithIdAndQuantity() {
    var givenId = "1";
    var givenQuantity = 5;
    var price = PriceWon.of("1000");

    var actual = BasketItem.create(givenId, price, givenQuantity);

    assertThat(actual.getItemId()).isEqualTo(givenId);
    assertThat(actual.getQuantity()).isEqualTo(givenQuantity);
  }

  @DisplayName("상품 수량을 주어진 값 만큼 올린다")
  @Test
  void increaseQuantityWithGivenAmount() {
    var givenId = "1";
    var givenAmount = 3;
    var price = PriceWon.of("1000");
    var basketItem = BasketItem.create(givenId, price);
    var expected = givenAmount + 1;

    var actual = basketItem.increaseQuantity(givenAmount);

    assertThat(actual).isEqualTo(expected);
  }

  @DisplayName("상품 수량을 1개 올린다")
  @Test
  void increaseQuantityOne() {
    var givenId = "1";
    var givenAmount = 3;
    var price = PriceWon.of("1000");
    var basketItem = BasketItem.create(givenId, price, givenAmount);
    var expected = givenAmount + 1;

    var actual = basketItem.increaseQuantityOne();

    assertThat(actual).isEqualTo(expected);
  }

  @DisplayName("상품 수량을 주어진 값 만큼 내린다")
  @Test
  void decreaseQuantityWithGivenAmount() {
    var givenId = "1";
    var givenQuantity = 5;
    var price = PriceWon.of("1000");
    var basketItem = BasketItem.create(givenId, price, givenQuantity);
    var expected = 3;

    var actual = basketItem.decreaseQuantity(givenQuantity - expected);

    assertThat(actual).isEqualTo(expected);
  }

  @DisplayName("상품 수량을 1개 내린다")
  @Test
  void decreaseQuantityOne() {
    var givenId = "1";
    var givenQuantity = 3;
    var price = PriceWon.of("1000");
    var basketItem = BasketItem.create(givenId, price, givenQuantity);
    var expected = givenQuantity - 1;

    var actual = basketItem.decreaseQuantityOne();

    assertThat(actual).isEqualTo(expected);
  }

  @DisplayName("BasketItem의 가격은 (지정된 가격 x 수량)이다")
  @Test
  void basketItemPriceIsSpecifiedPriceMultipliedQuantity() {
    var givenId = "1";
    var givenQuantity = 3;
    var givenPrice = 1000;
    var price = PriceWon.of(givenPrice);
    var basketItem = BasketItem.create(givenId, price, givenQuantity);
    var expected = PriceWon.of(givenPrice * givenQuantity);

    var actual = basketItem.getItemPrice();

    assertThat(actual).isEqualTo(expected);
  }

  @DisplayName("BasketItem은 ItemId 값이 같다면 같은 객체이다")
  @Test
  void sameObjectWhenItemIdIsSame() {
    var givenId1 = "1";
    var givenId2 = givenId1;
    var price = PriceWon.of("1000");

    var basketItem1 = BasketItem.create(givenId1, price);
    var basketItem2 = BasketItem.create(givenId2, price);

    assertThat(basketItem1).isEqualTo(basketItem2);
  }

  @DisplayName("BasketItem은 ItemId 값이 같다면 hash code도 같다")
  @Test
  void sameHashCodeWhenItemIdIsSame() {
    var givenId1 = "1";
    var givenId2 = givenId1;
    var price = PriceWon.of("1000");

    var basketItem1 = BasketItem.create(givenId1, price);
    var basketItem2 = BasketItem.create(givenId2, price);

    assertThat(basketItem1.hashCode()).isEqualTo(basketItem2.hashCode());
  }
}

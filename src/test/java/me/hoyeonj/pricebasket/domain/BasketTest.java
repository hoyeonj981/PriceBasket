package me.hoyeonj.pricebasket.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BasketTest {

  public static final String DUMMY_MART_NAME = "emart";

  @DisplayName("장바구니의 모든 상품을 비운다")
  @Test
  void clearAllBasketItems() {
    var dummyClientId = "1";
    var givenMart = MallType.from(DUMMY_MART_NAME);
    var basket = Basket.withoutId(dummyClientId, givenMart);
    var price = PriceWon.of("1000");
    basket.addItem(BasketItem.create(String.valueOf(1), price));
    basket.addItem(BasketItem.create(String.valueOf(2), price));
    basket.addItem(BasketItem.create(String.valueOf(3), price));
    var expected = 0;

    basket.clear();
    var actual = basket.getItemsCount();

    assertThat(actual).isEqualTo(expected);
  }

  @DisplayName("장바구니에 상품을 추가할 수 있다")
  @Test
  void addBasketItems() {
    var dummyClientId = "1";
    var givenMart = MallType.from(DUMMY_MART_NAME);
    var basket = Basket.withoutId(dummyClientId, givenMart);
    var price = PriceWon.of("1000");
    var expected = 3;

    basket.addItem(BasketItem.create(String.valueOf(1), price));
    basket.addItem(BasketItem.create(String.valueOf(2), price));
    basket.addItem(BasketItem.create(String.valueOf(3), price));

    assertThat(basket.getItemsCount()).isEqualTo(expected);
  }

  @DisplayName("장바구니 상품을 삭제할 수 있다")
  @Test
  void removeBasketItems() {
    var dummyClientId = "1";
    var givenMart = MallType.from(DUMMY_MART_NAME);
    var basket = Basket.withoutId(dummyClientId, givenMart);
    var price = PriceWon.of("1000");
    var item = BasketItem.create(String.valueOf(1), price);
    basket.addItem(item);
    basket.addItem(BasketItem.create(String.valueOf(2), price));
    basket.addItem(BasketItem.create(String.valueOf(3), price));
    var expected = 2;

    basket.removeItem(item);

    assertThat(basket.getItemsCount()).isEqualTo(expected);
  }

  @DisplayName("존재하지 않는 상품을 삭제할 경우 예외가 발생한다")
  @Test
  void throwExceptionWhenRemoveItemIfItemDoesNotExist() {
    var dummyClientId = "1";
    var givenMart = MallType.from(DUMMY_MART_NAME);
    var basket = Basket.withoutId(dummyClientId, givenMart);
    var price = PriceWon.of("1000");
    var item = BasketItem.create(String.valueOf(1), price);

    assertThatThrownBy(() -> basket.removeItem(item))
        .isInstanceOf(BasketItemNotFoundException.class);
  }

  @DisplayName("장바구니 상품 수량을 증가시킬 수 있다")
  @Test
  void increaseBasketItemQuantity() {
    var givenQuantity = 5;
    var dummyClientId = "1";
    var givenMart = MallType.from(DUMMY_MART_NAME);
    var basket = Basket.withoutId(dummyClientId, givenMart);
    var price = PriceWon.of("1000");
    var item = BasketItem.create(String.valueOf(1), price);
    basket.addItem(item);
    var expected = givenQuantity + 1;

    basket.increaseItemQuantity(item, givenQuantity);

    assertThat(basket.getItemQuantity(item)).isEqualTo(expected);
  }

  @DisplayName("존재하지 않는 상품을 증가시킬 경우 예외가 발생한다")
  @Test
  void throwExceptionWhenIncreaseItemIfItemDoesNotExist() {
    var dummyClientId = "1";
    var givenMart = MallType.from(DUMMY_MART_NAME);
    var basket = Basket.withoutId(dummyClientId, givenMart);
    var price = PriceWon.of("1000");
    var item = BasketItem.create(String.valueOf(1), price);

    assertThatThrownBy(() -> basket.increaseItemQuantity(item, 10))
        .isInstanceOf(BasketItemNotFoundException.class);
  }

  @DisplayName("장바구니 상품 수량을 한 개 증가시킬 수 있다")
  @Test
  void increaseOneBasketItemQuantity() {
    var dummyClientId = "1";
    var givenMart = MallType.from(DUMMY_MART_NAME);
    var basket = Basket.withoutId(dummyClientId, givenMart);
    var price = PriceWon.of("1000");
    var item = BasketItem.create(String.valueOf(1), price);
    basket.addItem(item);

    basket.increaseItemQuantity(item);

    assertThat(basket.getItemQuantity(item)).isEqualTo(2);
  }

  @DisplayName("존재하지 않는 상품을 한개 증가시킬 경우 예외가 발생한다")
  @Test
  void throwExceptionWhenIncreaseOneItemIfItemDoesNotExist() {
    var dummyClientId = "1";
    var givenMart = MallType.from(DUMMY_MART_NAME);
    var basket = Basket.withoutId(dummyClientId, givenMart);
    var price = PriceWon.of("1000");
    var item = BasketItem.create(String.valueOf(1), price);

    assertThatThrownBy(() -> basket.increaseItemQuantity(item))
        .isInstanceOf(BasketItemNotFoundException.class);
  }

  @DisplayName("장바구니 상품 수량을 감소시킬 수 있다")
  @Test
  void decreaseBasketItemQuantity() {
    var givenQuantity = 5;
    var dummyClientId = "1";
    var givenMart = MallType.from(DUMMY_MART_NAME);
    var basket = Basket.withoutId(dummyClientId, givenMart);
    var price = PriceWon.of("1000");
    var item = BasketItem.create(String.valueOf(1), price, givenQuantity);
    basket.addItem(item);
    var decreasingQuantity = 2;
    var expected = givenQuantity - decreasingQuantity;

    basket.decreaseItemQuantity(item, decreasingQuantity);

    assertThat(basket.getItemQuantity(item)).isEqualTo(expected);
  }

  @DisplayName("존재하지 않는 상품을 감소할 경우 예외가 발생한다")
  @Test
  void throwExceptionWhenDecreaseItemIfItemDoesNotExist() {
    var dummyClientId = "1";
    var givenMart = MallType.from(DUMMY_MART_NAME);
    var basket = Basket.withoutId(dummyClientId, givenMart);
    var price = PriceWon.of("1000");
    var item = BasketItem.create(String.valueOf(1), price);

    assertThatThrownBy(() -> basket.decreaseItemQuantity(item, 10))
        .isInstanceOf(BasketItemNotFoundException.class);
  }

  @DisplayName("장바구니 상품 수량을 한 개 감소시킬 수 있다")
  @Test
  void decreaseOneBasketItemQuantity() {
    var givenQuantity = 5;
    var dummyClientId = "1";
    var givenMart = MallType.from(DUMMY_MART_NAME);
    var basket = Basket.withoutId(dummyClientId, givenMart);
    var price = PriceWon.of("1000");
    var item = BasketItem.create(String.valueOf(1), price, givenQuantity);
    basket.addItem(item);
    var decreasingQuantity = 1;
    var expected = givenQuantity - decreasingQuantity;

    basket.decreaseItemQuantity(item);

    assertThat(basket.getItemQuantity(item)).isEqualTo(expected);
  }

  @DisplayName("존재하지 않는 상품을 한개 감소할 경우 예외가 발생한다")
  @Test
  void throwExceptionWhenDecreaseOneItemIfItemDoesNotExist() {
    var dummyClientId = "1";
    var givenMart = MallType.from(DUMMY_MART_NAME);
    var basket = Basket.withoutId(dummyClientId, givenMart);
    var price = PriceWon.of("1000");
    var item = BasketItem.create(String.valueOf(1), price);

    assertThatThrownBy(() -> basket.decreaseItemQuantity(item))
        .isInstanceOf(BasketItemNotFoundException.class);
  }

  @DisplayName("현재 장바구니 상품 종류 수를 얻는다")
  @Test
  void getItemsCount() {
    var dummyClientId = "1";
    var givenMart = MallType.from(DUMMY_MART_NAME);
    var basket = Basket.withoutId(dummyClientId, givenMart);
    var price = PriceWon.of("1000");
    basket.addItem(BasketItem.create(String.valueOf(1), price));
    basket.addItem(BasketItem.create(String.valueOf(2), price));

    assertThat(basket.getItemsCount()).isEqualTo(2);
  }

  @DisplayName("현재 장바구니 상품의 수량를 얻는다")
  @Test
  void getAllItemsQuantity() {
    var dummyClientId = "1";
    var givenMart = MallType.from(DUMMY_MART_NAME);
    var basket = Basket.withoutId(dummyClientId, givenMart);
    var givenQuantity = 10;
    var price = PriceWon.of("1000");
    var item = BasketItem.create(String.valueOf(1), price, givenQuantity);
    basket.addItem(item);

    assertThat(basket.getItemQuantity(item)).isEqualTo(givenQuantity);
  }

  @DisplayName("존재하지 않는 상품 수량 조회는 예외가 발생한다")
  @Test
  void throwExceptionWhenGetItemQuantityIfItemDoesNotExist() {
    var dummyClientId = "1";
    var givenMart = MallType.from(DUMMY_MART_NAME);
    var basket = Basket.withoutId(dummyClientId, givenMart);
    var givenQuantity = 10;
    var price = PriceWon.of("1000");
    var item = BasketItem.create(String.valueOf(1), price, givenQuantity);

    assertThatThrownBy(() -> basket.getItemQuantity(item))
        .isInstanceOf(BasketItemNotFoundException.class);
  }

  @DisplayName("가지고 있는 모든 상품의 가격을 계산한다")
  @Test
  void calculateItemsPrice() {
    var dummyClientId = "1";
    var givenMart = MallType.from(DUMMY_MART_NAME);
    var basket = Basket.withoutId(dummyClientId, givenMart);
    var givenQuantity1 = 1;
    var givenQuantity2 = 3;
    var givenQuantity3 = 5;
    var price1 = 1000;
    var price2 = 2000;
    var price3 = 1500;
    var givenPrice1 = PriceWon.of(price1);
    var givenPrice2 = PriceWon.of(price2);
    var givenPrice3 = PriceWon.of(price3);
    var item1 = BasketItem.create(String.valueOf(1), givenPrice1, givenQuantity1);
    var item2 = BasketItem.create(String.valueOf(2), givenPrice2, givenQuantity2);
    var item3 = BasketItem.create(String.valueOf(3), givenPrice3, givenQuantity3);
    var expected = PriceWon.of(price1 * givenQuantity1 + price2 * givenQuantity2 + price3 * givenQuantity3);
    basket.addItem(item1);
    basket.addItem(item2);
    basket.addItem(item3);

    var actual = basket.getTotalItemsPrice();

    assertThat(actual).isEqualTo(expected);
  }

  @DisplayName("Client id와 Basket id가 같다면 같은 객체이다")
  @Test
  void sameObjectWhenClientIdAndBasketIdIsSame() {
    var dummyClientId1 = "C1";
    var dummyClientId2 = dummyClientId1;
    var dummyBasketId1 = "B1";
    var dummyBasketId2 = dummyBasketId1;
    var givenMart = MallType.from(DUMMY_MART_NAME);
    var basket1 = Basket.withId(dummyBasketId1, dummyClientId1, givenMart);
    var basket2 = Basket.withId(dummyBasketId2, dummyClientId2, givenMart);

    assertThat(basket1).isEqualTo(basket2);
  }

  @DisplayName("Client id와 Basket id가 같다면 hash code도 같다")
  @Test
  void sameHashCodeWhenClientIdAndBasketIdIsSame() {
    var dummyClientId1 = "C1";
    var dummyClientId2 = dummyClientId1;
    var dummyBasketId1 = "B1";
    var dummyBasketId2 = dummyBasketId1;
    var givenMart = MallType.from(DUMMY_MART_NAME);
    var basket1 = Basket.withId(dummyBasketId1, dummyClientId1, givenMart);
    var basket2 = Basket.withId(dummyBasketId2, dummyClientId2, givenMart);

    assertThat(basket1.hashCode()).isEqualTo(basket2.hashCode());
  }
}

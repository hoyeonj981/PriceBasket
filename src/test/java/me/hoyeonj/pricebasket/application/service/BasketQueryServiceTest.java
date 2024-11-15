package me.hoyeonj.pricebasket.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import me.hoyeonj.pricebasket.application.in.dto.AllBasketResult;
import me.hoyeonj.pricebasket.application.in.dto.BasketResult;
import me.hoyeonj.pricebasket.application.out.BasketQueryPort;
import me.hoyeonj.pricebasket.application.out.ClientQueryPort;
import me.hoyeonj.pricebasket.domain.Basket;
import me.hoyeonj.pricebasket.domain.BasketItem;
import me.hoyeonj.pricebasket.domain.MallType;
import me.hoyeonj.pricebasket.domain.PriceWon;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BasketQueryServiceTest {

  @Mock
  private BasketQueryPort basketQueryPort;

  @Mock
  private ClientQueryPort clientQueryPort;

  @InjectMocks
  private BasketQueryService basketQueryService;

  @DisplayName("장바구니 조회 시 사용자가 존재하지 않는다면 예외가 발생한다")
  @Test
  void throwExceptionWhenGetBasketIfClientDoesNotExist() {
    var dummyClientId = "1";
    var dummyBasketId = "1";
    when(clientQueryPort.existByClientId(any())).thenThrow(ClientNotFoundException.class);

    assertThatThrownBy(() -> basketQueryService.getBasket(dummyClientId, dummyBasketId))
        .isInstanceOf(ClientNotFoundException.class);
  }

  @DisplayName("장바구니 조회 시 해당 장바구니가 존재하지 않는다면 예외가 발생한다")
  @Test
  void throwExceptionWhenGetBasketIfBasketDoesNotExist() {
    var dummyClientId = "1";
    var dummyBasketId = "1";
    when(clientQueryPort.existByClientId(any())).thenReturn(true);
    when(basketQueryPort.findById(any())).thenThrow(BasketNotFoundException.class);

    assertThatThrownBy(() -> basketQueryService.getBasket(dummyClientId, dummyBasketId))
        .isInstanceOf(BasketNotFoundException.class);
  }

  @DisplayName("사용자가 소유한 장바구니가 아닐 경우 예외가 발생한다")
  @Test
  void throwExceptionIfBasketIsNotOwnedByClient() {
    var dummyClientId1 = "1";
    var dummyClientId2 = "2";
    var dummyBasketId = "1";
    var mockBasket = mock(Basket.class);
    when(clientQueryPort.existByClientId(any())).thenReturn(true);
    when(basketQueryPort.findById(any())).thenReturn(Optional.of(mockBasket));
    when(mockBasket.getClientId()).thenReturn(dummyClientId2);

    assertThatThrownBy(() -> basketQueryService.getBasket(dummyClientId1, dummyBasketId))
        .isInstanceOf(NotBasketOwnerException.class);
  }

  @DisplayName("사용자가 소유한 장바구니가 존재할 경우 장바구니 결과를 반환한다")
  @Test
  void returnBasketResultIfBasketExistsAndIsOwnedByClient() {
    var dummyClientId = "1";
    var dummyBasketId = "1";
    var martName = "emart";
    var dummyBasket = createDummyBasket(dummyBasketId, dummyClientId, martName);
    var itemCount = 10;
    var defaultPrice = new BigDecimal(1000);
    addBasketItems(dummyBasket, itemCount, defaultPrice);
    when(clientQueryPort.existByClientId(any())).thenReturn(true);
    when(basketQueryPort.findById(any())).thenReturn(Optional.of(dummyBasket));
    var expectedTotalPrice = defaultPrice.multiply(new BigDecimal(itemCount));

    var actual = basketQueryService.getBasket(dummyClientId, dummyBasketId);

    assertThat(actual.totalItemsCount()).isEqualTo(itemCount);
    assertThat(actual.totalPrice()).isEqualTo(expectedTotalPrice);
    assertThat(actual.martName()).isEqualTo(martName);
  }

  private void addBasketItems(Basket basket, int itemCount, BigDecimal defaultPrice) {
    for (int i = 1; i <= itemCount; i++) {
      addBasketItem(basket, String.valueOf(i), defaultPrice);
    }
  }

  private void addBasketItem(Basket basket, String itemId, BigDecimal price) {
    var basketItem = BasketItem.create(itemId, PriceWon.of(price));
    basket.addItem(basketItem);
  }

  private Basket createDummyBasket(String basketId, String clientId, String martName) {
    return Basket.withId(basketId, clientId, MallType.from(martName));
  }

  @DisplayName("모든 장바구니를 조회 시 사용자가 존재하지 않는다면 예외가 발생한다")
  @Test
  void throwExceptionWhenGetAllBasketsIfClientDoesNotExist() {
    var dummyClientId = "1";
    when(clientQueryPort.existByClientId(any())).thenThrow(ClientNotFoundException.class);

    assertThatThrownBy(() -> basketQueryService.getAllBaskets(dummyClientId))
        .isInstanceOf(ClientNotFoundException.class);
  }

  @DisplayName("장바구니가 없는 경우 빈 목록을 반환한다")
  @Test
  void returnEmptyListWhenClientDoesNotHaveBaskets() {
    var dummyClientId = "1";
    when(clientQueryPort.existByClientId(any())).thenReturn(true);
    when(basketQueryPort.findAll(any())).thenReturn(Collections.EMPTY_LIST);

    var actual = basketQueryService.getAllBaskets(dummyClientId);

    assertThat(actual.values()).hasSize(0);
  }

  @DisplayName("유효한 id를 통해서 전체 장바구니를 조회할 수 있다")
  @Test
  void getAllBasketsIfClientIdIsValid() {
    var dummyClientId = "1";
    var basketCount = 2;
    var martName = "emart";
    var basketList = createDummyBasketList(basketCount, martName);
    when(clientQueryPort.existByClientId(any())).thenReturn(true);
    when(basketQueryPort.findAll(any())).thenReturn(basketList);

    var actual = basketQueryService.getAllBaskets(dummyClientId);

    assertThat(actual.values()).hasSize(basketCount)
        .extracting("basketId", "totalPrice", "martName")
        .containsExactly(
            tuple("1", new BigDecimal(1000), martName),
            tuple("2", new BigDecimal(1000), martName)
        );
  }

  private List<Basket> createDummyBasketList(int basketCount, String martName) {
    List<Basket> baskets = new ArrayList<>();
    for (int i = 1; i <= basketCount; i++) {
      var dummyBasket = createDummyBasket(String.valueOf(i), String.valueOf(i), martName);
      addBasketItems(dummyBasket, 1, new BigDecimal(1000));
      baskets.add(dummyBasket);
    }
    return baskets;
  }
}
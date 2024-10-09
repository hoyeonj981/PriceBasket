package me.hoyeonj.pricebasket.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class ItemTest {

  @DisplayName("id값 없이 상품객체를 생성한다")
  @ParameterizedTest
  @EnumSource(MallType.class)
  void create_item_object_withoud_id(MallType givenMall) {
    var mockItemInfo = mock(ItemInfo.class);

    var actual = Item.withoutId(mockItemInfo, givenMall);

    assertThat(actual.getId()).isNotNull();
    assertThat(actual.getMallType()).isEqualTo(givenMall);
    assertThat(actual.getCreatedAt()).isNotNull();
    assertThat(actual.getCreatedAt()).isEqualTo(actual.getUpdatedAt());
  }

  @DisplayName("주어진 id값을 가지고 상품객체를 생성한다")
  @ParameterizedTest
  @EnumSource(MallType.class)
  void create_item_object_with_id(MallType givenMall) {
    var mockItemInfo = mock(ItemInfo.class);
    var id = "testId";

    var actual = Item.withId(id, mockItemInfo, givenMall);

    assertThat(actual.getId()).isEqualTo(id);
    assertThat(actual.getMallType()).isEqualTo(givenMall);
    assertThat(actual.getCreatedAt()).isNotNull();
    assertThat(actual.getCreatedAt()).isEqualTo(actual.getUpdatedAt());
  }

  @DisplayName("총가격이 업데이트할 경우 새로운 객체를 생성한다")
  @Test
  void update_total_price_method_creates_new_object() {
    var originalItemInfo = mock(ItemInfo.class);
    var updatedItemInfo = mock(ItemInfo.class);
    var newPrice = mock(PriceWon.class);
    when(originalItemInfo.updatePrice(newPrice)).thenReturn(updatedItemInfo);

    var originalItem = Item.withoutId(originalItemInfo, MallType.EMARTMALL);
    var updatedItem = originalItem.updateTotalPrice(newPrice);

    assertThat(originalItem).isNotEqualTo(updatedItem);
    assertThat(originalItem.getId()).isEqualTo(updatedItem.getId());
    assertThat(originalItem.getMallType()).isEqualTo(updatedItem.getMallType());
    assertThat(originalItem.getCreatedAt()).isEqualTo(updatedItem.getCreatedAt());
    assertThat(updatedItem.getUpdatedAt().isAfter(originalItem.getUpdatedAt())).isTrue();
  }

  @DisplayName("상품은 id, itemInfo, MallType이 다르다면 다른 객체이다")
  @Test
  void same_object_when_id_iteminfo_malltype_are_same() {
    var itemInfo1 = mock(ItemInfo.class);
    var itemInfo2 = mock(ItemInfo.class);

    var item1 = Item.withId("1", itemInfo1, MallType.EMARTMALL);
    var item2 = Item.withId("1", itemInfo1, MallType.EMARTMALL);
    var item3 = Item.withId("2", itemInfo2, MallType.HOMEPLUS);

    assertThat(item1).isEqualTo(item2);
    assertThat(item1).isNotEqualTo(item3);
  }
}
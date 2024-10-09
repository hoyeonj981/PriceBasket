package me.hoyeonj.pricebasket.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ItemInfoTest {

  private UnitPrice unitPrice;
  private ItemInfo itemInfo;

  @BeforeEach
  void setUp() {
    var name = "Test Item";
    var totalPrice = PriceWon.of("10000");
    var rating = "4.5";
    var itemDetails = ItemUrl.of("http://example.com/details");
    var itemImage = ItemUrl.of("http://example.com/image.jpg");

    unitPrice = mock(UnitPrice.class);
    when(unitPrice.getPriceWon()).thenReturn(PriceWon.of("1000"));
    when(unitPrice.getUnit()).thenReturn(MeasurementType.G);
    when(unitPrice.getTotalAmount()).thenReturn(new BigDecimal("100"));

    itemInfo = new ItemInfo(name, totalPrice, unitPrice, rating, itemDetails, itemImage);
  }

  @DisplayName("가격을 업데이트할 경우, 가격과 단위을 빼고 모두 동일하다")
  @Test
  void updatedPriceShouldReturnNewObjectWithUpdatedPrice() {
    var newTotalPrice = PriceWon.of("20000");
    var newUnitPrice = mock(UnitPrice.class);
    when(unitPrice.updateUnitPrice(newTotalPrice)).thenReturn(newUnitPrice);

    var updatedItemInfo = itemInfo.updatePrice(newTotalPrice);


    assertThat(updatedItemInfo).isNotEqualTo(itemInfo);
    // 총가격, 단위가격 빼고 모두 동일하다.
    assertThat(updatedItemInfo.getRating()).isEqualTo(itemInfo.getRating());
    assertThat(updatedItemInfo.getItemDetails()).isEqualTo(itemInfo.getItemDetails());
    assertThat(updatedItemInfo.getItemImage()).isEqualTo(itemInfo.getItemImage());
    assertThat(updatedItemInfo.getName()).isEqualTo(itemInfo.getName());
  }
}

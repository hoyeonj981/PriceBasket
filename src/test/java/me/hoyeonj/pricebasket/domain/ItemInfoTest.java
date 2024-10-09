package me.hoyeonj.pricebasket.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ItemInfoTest {

  private String name;
  private PriceWon totalPrice;
  private UnitPrice unitPrice;
  private String rating;
  private ItemUrl itemDetails;
  private ItemUrl itemImage;
  private ItemInfo itemInfo;

  @BeforeEach
  void setUp() {
    name = "Test Item";
    totalPrice = PriceWon.of("10000");
    unitPrice = mock(UnitPrice.class);
    rating = "4.5";
    itemDetails = new ItemUrl("http://example.com/details");
    itemImage = new ItemUrl("http://example.com/image.jpg");

    when(unitPrice.getPriceWon()).thenReturn(PriceWon.of("1000"));
    when(unitPrice.getUnit()).thenReturn(MeasurementType.G);
    when(unitPrice.getTotalAmount()).thenReturn(new BigDecimal("100"));

    itemInfo = new ItemInfo(name, totalPrice, unitPrice, rating, itemDetails, itemImage);
  }

  @DisplayName("가격을 업데이트할 경우, 가격과 단위을 빼고 모두 동일하다")
  @Test
  void updatedPrice_should_return_new_object_with_updated_price() {
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

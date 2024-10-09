package me.hoyeonj.pricebasket.domain;

import static me.hoyeonj.pricebasket.domain.MallType.EMARTMALL;
import static me.hoyeonj.pricebasket.domain.MallType.HOMEPLUS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ItemFactoryTest {

  @Mock
  private UnitPriceCalculator mockCalculator;

  private ItemFactory itemFactory;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    itemFactory = new ItemFactory(mockCalculator);
    when(mockCalculator.calculate(any(), any(), any())).thenReturn(PriceWon.of(BigDecimal.TEN));
  }

  @DisplayName("주어진 input 데이터로 emartMall 상품을 생성한다")
  @Test
  void createEmartMallItemShouldItemWithCorrectData() {
    var input = createDummyInput();
    var mockUnitPrice = mock(UnitPrice.class);
    try (var mockedStatic = mockStatic(UnitPrice.class)) {
      mockedStatic.when(() -> UnitPrice.create(any(), any(), any(), any()))
          .thenReturn(mockUnitPrice);

      var actual = itemFactory.createEmarMallItem(input);

      assertThat(actual).isNotNull();
      assertThat(actual.getMallType()).isEqualTo(EMARTMALL);
      assertThat(actual.getName()).isEqualTo("Test Item");
      assertThat(actual.getTotalPrice()).isEqualTo(PriceWon.of("10000"));
      assertThat(actual.getRating()).isEqualTo("4.5");
      assertThat(actual.getDetailsUrl().getValue()).isEqualTo("http://example.com/details");
      assertThat(actual.getImageUrl().getValue()).isEqualTo("http://example.com/image.jpg");

      verify(UnitPrice.class);
      UnitPrice.create(
          eq(new BigDecimal("100")),
          eq(PriceWon.of("10000")),
          eq("g"),
          eq(mockCalculator)
      );
    }
  }

  @DisplayName("주어진 input 데이터로 homeplus 상품을 생성한다")
  @Test
  void createHomeplusItemShouldItemWithCorrectData() {
    var input = createDummyInput();
    var mockUnitPrice = mock(UnitPrice.class);
    try (var mockedStatic = mockStatic(UnitPrice.class)) {
      mockedStatic.when(() -> UnitPrice.create(any(), any(), any(), any()))
          .thenReturn(mockUnitPrice);
      var actual = itemFactory.createHomeplusItem(input);

      assertThat(actual).isNotNull();
      assertThat(actual.getMallType()).isEqualTo(HOMEPLUS);
      assertThat(actual.getName()).isEqualTo("Test Item");
      assertThat(actual.getTotalPrice()).isEqualTo(PriceWon.of("10000"));
      assertThat(actual.getRating()).isEqualTo("4.5");
      assertThat(actual.getDetailsUrl().getValue()).isEqualTo("http://example.com/details");
      assertThat(actual.getImageUrl().getValue()).isEqualTo("http://example.com/image.jpg");

      verify(UnitPrice.class);
      UnitPrice.create(
          eq(new BigDecimal("100")),
          eq(PriceWon.of("10000")),
          eq("g"),
          eq(mockCalculator)
      );
    }
  }

  private ItemFactoryInput createDummyInput() {
    return new ItemFactoryInput(
        "Test Item",
        new BigDecimal("10000"),
        new BigDecimal("100"),
        "g",
        "4.5",
        "http://example.com/details",
        "http://example.com/image.jpg"
    );
  }
}
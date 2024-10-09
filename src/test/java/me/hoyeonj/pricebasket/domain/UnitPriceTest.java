package me.hoyeonj.pricebasket.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class UnitPriceTest {

  @Mock
  private UnitPriceCalculator mockCalculator;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @DisplayName("정상적으로 UnitPrice 객체를 생성한다")
  @Test
  void createUnitPriceObject() {
    when(mockCalculator.calculate(any(), any(), any()))
        .thenReturn(PriceWon.of(1000));

    var actual = UnitPrice.create(BigDecimal.TEN, PriceWon.of(5000), "G", mockCalculator);

    assertThat(actual.getUnit()).isEqualTo(MeasurementType.G);
    assertThat(actual.getPriceWon()).isEqualTo(PriceWon.of(1000));
    assertThat(actual.getTotalAmount()).isEqualTo(BigDecimal.TEN);
  }

  @DisplayName("총 가격이 변경되면 새롭게 단위가격을 생성한다")
  @Test
  void updatingWillCreateNewUnitPriceObject() {
    var givenPrice = 2000L;
    when(mockCalculator.calculate(any(), any(), any()))
        .thenReturn(PriceWon.of(1000))
        .thenReturn(PriceWon.of(givenPrice));
    var expected = PriceWon.of(givenPrice);

    var unitPrice = UnitPrice.create(BigDecimal.TEN, PriceWon.of(5000), "G", mockCalculator);
    var updatedUnitPrice = unitPrice.updateUnitPrice(PriceWon.of(10000));
    var actual = updatedUnitPrice.getPriceWon();

    assertThat(actual).isEqualTo(expected);
  }

  @DisplayName("단위 가격은 가격으로 비교한다")
  @ParameterizedTest
  @CsvSource({
      "100, 200, -1",
      "100, 100, 0",
      "200, 100, 1",
  })
  void compareUnitPriceCorrectly(String left, String right, int expected) {
    when(mockCalculator.calculate(any(), any(), any()))
        .thenReturn(PriceWon.of(left))
        .thenReturn(PriceWon.of(right));

    var unitPrice1 = UnitPrice.create(BigDecimal.TEN, PriceWon.of(5000), "G", mockCalculator);
    var unitPrice2 = UnitPrice.create(BigDecimal.TEN, PriceWon.of(10000), "G", mockCalculator);
    var actual = unitPrice1.compareTo(unitPrice2);

    assertThat(actual).isEqualTo(expected);
  }
}

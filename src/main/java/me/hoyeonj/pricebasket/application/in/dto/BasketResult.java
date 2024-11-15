package me.hoyeonj.pricebasket.application.in.dto;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public record BasketResult(
    List<BasketItem> items,
    BigDecimal totalPrice,
    String martName,
    int totalItemsCount
) {

  public BasketResult(final List<BasketItem> items, final BigDecimal totalPrice,
      final String martName, final int totalItemsCount) {
    this.items = List.copyOf(items);
    this.totalPrice = totalPrice;
    this.martName = martName;
    this.totalItemsCount = totalItemsCount;
  }

  public List<BasketItem> items() {
    return Collections.unmodifiableList(items);
  }
}

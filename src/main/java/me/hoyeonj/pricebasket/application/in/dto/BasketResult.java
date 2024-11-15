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

  public List<BasketItem> items() {
    return Collections.unmodifiableList(items);
  }
}

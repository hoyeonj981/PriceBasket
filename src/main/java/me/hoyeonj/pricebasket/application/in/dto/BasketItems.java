package me.hoyeonj.pricebasket.application.in.dto;

import java.util.Collections;
import java.util.List;

public record BasketItems(
    List<BasketItem> values,
    String totalPrice
) {

  public List<BasketItem> values() {
    return Collections.unmodifiableList(values);
  }
}

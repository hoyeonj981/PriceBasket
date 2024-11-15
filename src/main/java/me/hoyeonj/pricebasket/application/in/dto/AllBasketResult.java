package me.hoyeonj.pricebasket.application.in.dto;

import java.util.Collections;
import java.util.List;

public record AllBasketResult(
    List<BasketSummary> values
) {

  public List<BasketSummary> values() {
    return Collections.unmodifiableList(values);
  }
}

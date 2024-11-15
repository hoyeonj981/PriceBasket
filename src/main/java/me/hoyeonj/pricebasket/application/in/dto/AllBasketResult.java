package me.hoyeonj.pricebasket.application.in.dto;

import java.util.Collections;
import java.util.List;

public record AllBasketResult(
    List<BasketSummary> values
) {

  public AllBasketResult(final List<BasketSummary> values) {
    this.values = List.copyOf(values);
  }

  public List<BasketSummary> values() {
    return Collections.unmodifiableList(values);
  }
}

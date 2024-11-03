package me.hoyeonj.pricebasket.application.in.dto;

import java.util.Map;

public record SearchedItemResult(
    Map<String, SearchedItems> result
) {

  public boolean hasResult(final String martName) {
    return result.get(martName).hasResults();
  }
}

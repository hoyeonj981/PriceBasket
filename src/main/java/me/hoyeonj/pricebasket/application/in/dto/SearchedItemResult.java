package me.hoyeonj.pricebasket.application.in.dto;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public record SearchedItemResult(
    Map<String, SearchedItems> result
) {

  public SearchedItemResult(final Map<String, SearchedItems> result) {
    final var collect = result.entrySet()
        .stream()
        .collect(Collectors.toMap(
            Entry::getKey,
            entry -> entry.getValue().copy()));
    this.result = Collections.unmodifiableMap(collect);
  }

  public boolean hasResult(final String martName) {
    return result.get(martName).hasResults();
  }

  public Map<String, SearchedItems> result() {
    return result.entrySet()
        .stream()
        .collect(Collectors.toMap(
            Entry::getKey,
            entry -> entry.getValue().copy()
        ));
  }
}

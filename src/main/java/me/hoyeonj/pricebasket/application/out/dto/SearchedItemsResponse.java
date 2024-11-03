package me.hoyeonj.pricebasket.application.out.dto;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;

public record SearchedItemsResponse(
    Map<String, List<RawItemData>> response
) {

  public SearchedItemsResponse(final Map<String, List<RawItemData>> response) {
    final var collect = response.entrySet()
        .stream()
        .collect(Collectors.toMap(
            entry -> entry.getKey(),
            entry -> List.copyOf(entry.getValue())));
    this.response = Collections.unmodifiableMap(collect);
  }

  public List<RawItemData> getItemsForMart(final String name) {
    return response.getOrDefault(name, List.of());
  }

  public boolean hasItemsForMart(final String name) {
    return !getItemsForMart(name).isEmpty();
  }

  public Map<String, List<RawItemData>> response() {
    return response.entrySet().stream()
        .collect(Collectors.toMap(
            Entry::getKey,
            entry -> List.copyOf(entry.getValue())
        ));
  }
}

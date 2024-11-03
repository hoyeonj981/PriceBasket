package me.hoyeonj.pricebasket.application.out.dto;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public record SearchedItemsResponse(
    Map<String, List<RawItemData>> response
) {

  public SearchedItemsResponse(final Map<String, List<RawItemData>> response) {
    this.response = Objects.requireNonNullElseGet(response, Map::of);
  }

  public List<RawItemData> getItemsForMart(final String name) {
    return response.getOrDefault(name, List.of());
  }

  public boolean hasItemsForMart(final String name) {
    return !getItemsForMart(name).isEmpty();
  }
}

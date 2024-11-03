package me.hoyeonj.pricebasket.application.in.dto;

import java.util.List;

public record SearchedItems(
    List<SearchedItem> items,
    SearchStatus status,
    int itemsCount
) {

  public static SearchedItems success(final List<SearchedItem> searchedItems) {
    return new SearchedItems(
        searchedItems,
        SearchStatus.SUCCESS,
        searchedItems.size()
    );
  }

  public static SearchedItems noResults() {
    return new SearchedItems(
        List.of(),
        SearchStatus.NO_RESULTS,
        0
    );
  }

  public boolean hasResults() {
    return status == SearchStatus.SUCCESS & itemsCount > 0;
  }
}

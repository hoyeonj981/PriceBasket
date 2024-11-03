package me.hoyeonj.pricebasket.application.in.dto;

import java.util.List;

public record SearchedItems(
    List<SearchedItem> items,
    SearchStatus status,
    int itemsCount
) {

  public SearchedItems(final List<SearchedItem> items, final SearchStatus status,
      final int itemsCount) {
    this.items = List.copyOf(items);
    this.status = status;
    this.itemsCount = itemsCount;
  }

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

  public List<SearchedItem> items() {
    return List.copyOf(this.items);
  }

  public SearchedItems copy() {
    return new SearchedItems(List.copyOf(this.items), this.status, this.itemsCount);
  }
}

package me.hoyeonj.pricebasket.application.in;

import me.hoyeonj.pricebasket.application.in.dto.SearchedItemResult;

public interface SearchItemsUseCase {

  SearchedItemResult search(final String keyword);
}

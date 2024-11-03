package me.hoyeonj.pricebasket.application.out;

import me.hoyeonj.pricebasket.application.out.dto.SearchedItemsResponse;

public interface ItemSearchPort {

  SearchedItemsResponse searchItems(final String keyword);
}

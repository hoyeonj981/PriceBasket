package me.hoyeonj.pricebasket.adapter.out;

import java.util.List;

record EmartMallParsedDocument(
    List<EmartMallItemInfo> itemList,
    int totalPages,
    int currentPage
) { }

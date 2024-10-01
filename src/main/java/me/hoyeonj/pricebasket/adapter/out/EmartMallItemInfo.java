package me.hoyeonj.pricebasket.adapter.out;

import lombok.Builder;

record EmartMallItemInfo(
    String name,
    String brandName,
    String price,
    String unitOfPrice,
    String rating,
    String detailsUrl,
    String imageUrl
) {

  @Builder EmartMallItemInfo {}
}
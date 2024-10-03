package me.hoyeonj.pricebasket.adapter.out;

import lombok.Builder;

record HomePlusItemInfo(
    String name,
    String price,
    String unitOfPrice,
    String rating,
    String detailsUrl,
    String imageUrl

) {
  @Builder HomePlusItemInfo {}
}

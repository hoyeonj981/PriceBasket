package me.hoyeonj.pricebasket.adapter.out;

class EmartMallItemInfoFixture {

  static EmartMallItemInfo createItemInfoFixture() {
    return EmartMallItemInfo.builder()
        .name("test")
        .brandName("test")
        .price("100")
        .rating("4.8")
        .imageUrl("testUrl")
        .detailsUrl("testUrl")
        .build();
  }

  static EmartMallItemInfo createItemInfo(
      final String itemName,
      final String brandName,
      final String price,
      final String unitOfPrice,
      final String rating,
      final String detailsUrl,
      final String imageUrl) {
    return EmartMallItemInfo.builder()
        .name(itemName)
        .brandName(brandName)
        .price(price)
        .unitOfPrice(unitOfPrice)
        .rating(rating)
        .detailsUrl(detailsUrl)
        .imageUrl(imageUrl)
        .build();
  }
}

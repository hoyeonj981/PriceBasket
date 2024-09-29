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
}

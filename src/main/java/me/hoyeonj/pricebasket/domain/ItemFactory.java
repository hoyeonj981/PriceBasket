package me.hoyeonj.pricebasket.domain;

public class ItemFactory {

  private final UnitPriceCalculator calculator;

  public ItemFactory(final UnitPriceCalculator calculator) {
    this.calculator = calculator;
  }

  public Item createEmarMallItem(ItemFactoryInput input) {
    return createFromData(input, MallType.EMARTMALL);
  }

  public Item createHomeplusItem(ItemFactoryInput input) {
    return createFromData(input, MallType.HOMEPLUS);
  }

  private Item createFromData(ItemFactoryInput input, MallType mallType) {
    final var totalPrice = PriceWon.of(input.totalPrice());
    final var unitPrice = UnitPrice.create(input.totalAmount(), totalPrice, input.unitSymbol(),
        calculator);
    final var detailsUrl = ItemUrl.of(input.detailsUrl());
    final var imageUrl = ItemUrl.of(input.imageUrl());

    final var itemInfo = new ItemInfo(input.name(), totalPrice, unitPrice, input.rating(),
        detailsUrl, imageUrl);

    return Item.withoutId(itemInfo, mallType);
  }
}

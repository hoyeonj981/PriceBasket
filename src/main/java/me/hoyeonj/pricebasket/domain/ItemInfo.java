package me.hoyeonj.pricebasket.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class ItemInfo {

  private final String name;
  private final PriceWon totalPrice;
  private final UnitPrice unitPrice;
  private final String rating;
  private final ItemUrl itemDetails;
  private final ItemUrl itemImage;

  public ItemInfo(final String name, final PriceWon totalPrice, final UnitPrice unitPrice,
      final String rating, final ItemUrl itemDetails, final ItemUrl itemImage) {
    this.name = name;
    this.totalPrice = totalPrice;
    this.unitPrice = unitPrice;
    this.rating = rating;
    this.itemDetails = itemDetails;
    this.itemImage = itemImage;
  }

  public ItemInfo updatePrice(final PriceWon totalPrice) {
    return new ItemInfo(
        this.name,
        totalPrice,
        this.unitPrice.updateUnitPrice(totalPrice),
        this.rating,
        this.itemDetails,
        this.itemImage
    );
  }

  public String getName() {
    return name;
  }

  public PriceWon getTotalPrice() {
    return new PriceWon(this.totalPrice);
  }

  public PriceWon getUnitPrice() {
    return new PriceWon(this.unitPrice.getPriceWon());
  }

  public MeasurementType getMeasurementType() {
    return unitPrice.getUnit();
  }

  public BigDecimal totalAmount() {
    return unitPrice.getTotalAmount();
  }

  public String getRating() {
    return rating;
  }

  public ItemUrl getItemDetails() {
    return itemDetails;
  }

  public ItemUrl getItemImage() {
    return itemImage;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final ItemInfo itemInfo = (ItemInfo) o;
    return Objects.equals(name, itemInfo.name) && Objects.equals(totalPrice,
        itemInfo.totalPrice) && Objects.equals(unitPrice, itemInfo.unitPrice)
        && Objects.equals(rating, itemInfo.rating) && Objects.equals(itemDetails,
        itemInfo.itemDetails) && Objects.equals(itemImage, itemInfo.itemImage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, totalPrice, unitPrice, rating, itemDetails, itemImage);
  }
}

package me.hoyeonj.pricebasket.domain;

import java.util.Objects;

public class BasketItem {

  private final String itemId;
  private final Quantity quantity;

  public static BasketItem create(final String itemId, final int quantity) {
    return new BasketItem(itemId, new Quantity(quantity));
  }

  public static BasketItem create(final String itemId) {
    return new BasketItem(itemId, new Quantity(1));
  }

  private BasketItem(final String itemId, final Quantity quantity) {
    this.itemId = itemId;
    this.quantity = quantity;
  }

  public int increaseQuantity(final int amount) {
    return this.quantity.increaseQuantity(amount);
  }

  public int increaseQuantityOne() {
    return this.quantity.increaseOne();
  }

  public int decreaseQuantity(final int amount) {
    return this.quantity.decreaseQuantity(amount);
  }

  public int decreaseQuantityOne() {
    return this.quantity.decreaseOne();
  }

  public String  getItemId() {
    return this.itemId;
  }

  public int getQuantity() {
    return this.quantity.getValue();
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final BasketItem that = (BasketItem) o;
    return Objects.equals(itemId, that.itemId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(itemId);
  }
}

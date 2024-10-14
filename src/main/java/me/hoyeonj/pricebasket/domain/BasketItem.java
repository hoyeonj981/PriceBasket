package me.hoyeonj.pricebasket.domain;

import java.util.Objects;

class BasketItem {

  private final ItemId itemId;
  private final Quantity quantity;

  public BasketItem(final String id) {
    this(ItemId.from(id));
  }

  public BasketItem(final String id, final int quantity) {
    this(ItemId.from(id), new Quantity(quantity));
  }

  private BasketItem(final ItemId itemId) {
    this(itemId, new Quantity(1));
  }

  private BasketItem(final ItemId itemId, final Quantity quantity) {
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
    return itemId.getValue();
  }

  public int getQuantity() {
    return quantity.getValue();
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
    return Objects.equals(itemId, that.itemId) && Objects.equals(quantity,
        that.quantity);
  }

  @Override
  public int hashCode() {
    return Objects.hash(itemId, quantity);
  }
}

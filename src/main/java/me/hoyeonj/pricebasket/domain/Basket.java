package me.hoyeonj.pricebasket.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Basket {

  private final BasketId basketId;
  private final List<BasketItem> items;
  private final LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public static Basket withoutId() {
    return new Basket(BasketId.create(), new ArrayList<>());
  }

  public static Basket withId(final String id) {
    return new Basket(BasketId.from(id), new ArrayList<>());
  }

  private Basket(final BasketId basketId, final List<BasketItem> items) {
    this.basketId = basketId;
    this.items = items;
    this.createdAt = LocalDateTime.now();
    this.updatedAt = createdAt;
  }

  public void clear() {
    this.items.clear();
    updateModificationTime();
  }

  public void addItem(final BasketItem item) {
    this.items.add(item);
    updateModificationTime();
  }

  public void removeItem(final BasketItem item) {
    if (!this.items.remove(item)) {
      throw new BasketItemNotFoundException();
    }
    updateModificationTime();
  }

  public void increaseItemQuantity(final BasketItem basketItem) {
    this.items.stream()
        .filter(findItem(basketItem))
        .findFirst()
        .ifPresentOrElse(BasketItem::increaseQuantityOne, throwItemNotFoundException());
    updateModificationTime();
  }

  public void increaseItemQuantity(final BasketItem basketItem, final int amount) {
    this.items.stream()
        .filter(findItem(basketItem))
        .findFirst()
        .ifPresentOrElse(increaseQuantity(amount), throwItemNotFoundException());
    updateModificationTime();
  }

  public void decreaseItemQuantity(final BasketItem basketItem) {
    this.items.stream()
        .filter(findItem(basketItem))
        .findFirst()
        .ifPresentOrElse(BasketItem::decreaseQuantityOne, throwItemNotFoundException());
    updateModificationTime();
  }

  public void decreaseItemQuantity(final BasketItem basketItem, final int amount) {
    this.items.stream()
        .filter(findItem(basketItem))
        .findFirst()
        .ifPresentOrElse(decreaseQuantity(amount), throwItemNotFoundException());
    updateModificationTime();
  }

  private Runnable throwItemNotFoundException() {
    return () -> {
      throw new BasketItemNotFoundException();
    };
  }

  private Predicate<BasketItem> findItem(final BasketItem basketItem) {
    return i -> i.equals(basketItem);
  }

  private Consumer<BasketItem> increaseQuantity(final int amount) {
    return i -> i.increaseQuantity(amount);
  }

  private static Consumer<BasketItem> decreaseQuantity(final int amount) {
    return i -> i.decreaseQuantity(amount);
  }

  public int getItemQuantity(final BasketItem item) {
    return items.stream()
        .filter(findItem(item))
        .findFirst()
        .map(BasketItem::getQuantity)
        .orElseThrow(BasketItemNotFoundException::new);
  }

  public int getItemsCount() {
    return this.items.size();
  }

  private void updateModificationTime() {
    this.updatedAt = LocalDateTime.now();
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final Basket basket = (Basket) o;
    return Objects.equals(basketId, basket.basketId) && Objects.equals(items,
        basket.items) && Objects.equals(createdAt, basket.createdAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(basketId, items, createdAt);
  }
}

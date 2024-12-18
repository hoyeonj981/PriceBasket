package me.hoyeonj.pricebasket.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Basket {

  private final BasketId basketId;
  private final MallType mallType;
  private final List<BasketItem> items;
  private final String clientId;
  private final LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public static Basket withoutId(final String clientId, final MallType mallType) {
    return new Basket(BasketId.create(), mallType, new ArrayList<>(), clientId);
  }

  public static Basket withId(final String basketId, final String clientId, final MallType mallType) {
    return new Basket(BasketId.from(basketId), mallType, new ArrayList<>(), clientId);
  }

  public Basket(
      final BasketId basketId,
      final MallType mallType,
      final List<BasketItem> items,
      final String clientId
  ) {
    this.basketId = basketId;
    this.mallType = mallType;
    this.items = new ArrayList<>(items.size());
    Collections.copy(this.items, items);
    this.clientId = clientId;
    this.createdAt = LocalDateTime.now();
    this.updatedAt = this.createdAt;
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

  public PriceWon getTotalItemsPrice() {
    return items.stream()
        .map(BasketItem::getItemPrice)
        .reduce(PriceWon.ZERO, PriceWon::add);
  }

  public int getItemsCount() {
    return this.items.size();
  }

  public String getClientId() {
    return this.clientId;
  }

  public String getBasketId() {
    return this.basketId.getValue();
  }

  public LocalDateTime getCreatedAt() {
    return this.createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return this.updatedAt;
  }

  public String getBasketMallName() {
    return mallType.getName();
  }

  public List<BasketItem> getItems() {
    return Collections.unmodifiableList(this.items);
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
    return Objects.equals(basketId, basket.basketId) && Objects.equals(clientId,
        basket.clientId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(basketId, clientId);
  }
}

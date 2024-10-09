package me.hoyeonj.pricebasket.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Item {

  private final ItemId id;
  private final ItemInfo itemInfo;
  private final MallType mallType;
  private final LocalDateTime createdAt;
  private final LocalDateTime updatedAt;

  public static Item withoutId(final ItemInfo itemInfo, MallType mallType) {
    final var itemId = ItemId.create();
    final var createdAt = LocalDateTime.now();
    return new Item(itemId, itemInfo, mallType, createdAt, createdAt);
  }

  public static Item withId(final String id, final ItemInfo itemInfo, MallType mallType) {
    final var itemId = ItemId.from(id);
    final var createdAt = LocalDateTime.now();
    return new Item(itemId, itemInfo, mallType, createdAt, createdAt);
  }

  private Item(final ItemId id, final ItemInfo itemInfo, final MallType mallType,
      final LocalDateTime createdAt, final LocalDateTime updatedAt) {
    this.id = id;
    this.itemInfo = itemInfo;
    this.mallType = mallType;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public Item updateTotalPrice(final PriceWon price) {
    final var newItemInfo = this.itemInfo.updatePrice(price);
    final var newUpdate = LocalDateTime.now();
    return new Item(
        this.id,
        newItemInfo,
        this.mallType,
        this.createdAt,
        newUpdate
    );
  }

  public String getId() {
    return id.getValue();
  }

  public String getName() {
    return itemInfo.getName();
  }

  public PriceWon getTotalPrice() {
    return itemInfo.getTotalPrice();
  }

  public PriceWon getUnitPrice() {
    return itemInfo.getUnitPrice();
  }

  public MeasurementType getMeasurementType() {
    return itemInfo.getMeasurementType();
  }

  public BigDecimal getTotalAmount() {
    return itemInfo.totalAmount();
  }

  public String getRating() {
    return itemInfo.getRating();
  }

  public ItemUrl getDetailsUrl() {
    return itemInfo.getItemDetails();
  }

  public ItemUrl getImageUrl() {
    return itemInfo.getItemImage();
  }

  public MallType getMallType() {
    return mallType;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final Item item = (Item) o;
    return Objects.equals(id, item.id) && Objects.equals(itemInfo, item.itemInfo)
        && mallType == item.mallType;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, itemInfo, mallType);
  }
}
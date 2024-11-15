package me.hoyeonj.pricebasket.application.in.dto;

import java.math.BigDecimal;
import java.util.List;

public record BasketResult(
    List<BasketItem> items,
    BigDecimal totalPrice,
    int totalItemsCount
) {
}

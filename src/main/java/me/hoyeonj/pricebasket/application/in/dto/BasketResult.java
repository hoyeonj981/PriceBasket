package me.hoyeonj.pricebasket.application.in.dto;

import java.util.List;

public record BasketResult(
    List<BasketItem> items,
    String totalPrice
) {
}

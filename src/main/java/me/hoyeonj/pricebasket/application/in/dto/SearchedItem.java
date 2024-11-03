package me.hoyeonj.pricebasket.application.in.dto;

public record SearchedItem(
    String name,
    String totalPrice,
    String unitPrice,
    String unitSymbol,
    String rating,
    String itemDetailsUrl,
    String itemImageUrl
) { }
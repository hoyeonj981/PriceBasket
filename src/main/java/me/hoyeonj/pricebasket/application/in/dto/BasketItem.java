package me.hoyeonj.pricebasket.application.in.dto;

public record BasketItem(
    String name,
    String price,
    String unitPrice,
    String amount,
    String unitSymbol,
    String rating,
    String detailsUrl,
    String imageUrl
) {

}

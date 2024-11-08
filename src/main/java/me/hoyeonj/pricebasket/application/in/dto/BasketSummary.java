package me.hoyeonj.pricebasket.application.in.dto;

public record BasketSummary(
    String basketId,
    String totalPrice,
    MartType martType
) {

}

package me.hoyeonj.pricebasket.application.in.dto;

import java.math.BigDecimal;

public record BasketSummary(
    String basketId,
    BigDecimal totalPrice,
    String martName
) {

}

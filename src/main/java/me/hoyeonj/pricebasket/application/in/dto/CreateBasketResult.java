package me.hoyeonj.pricebasket.application.in.dto;

import java.time.LocalDateTime;

public record CreateBasketResult(
    String clientId,
    String basketId,
    LocalDateTime createdAt
) {

}

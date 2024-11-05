package me.hoyeonj.pricebasket.application.in.dto;

import java.time.Duration;

public record CreateBasketCommand(
    String clientId,
    String martName,
    Duration expirationDate
) {

}

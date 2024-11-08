package me.hoyeonj.pricebasket.application.in.dto;

import java.util.List;

public record AllBasketResult(
    List<BasketSummary> values
) {

}

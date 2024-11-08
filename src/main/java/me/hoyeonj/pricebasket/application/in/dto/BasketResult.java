package me.hoyeonj.pricebasket.application.in.dto;

import java.util.Map;

public record BasketResult(
    Map<MartType, BasketItems> result
) {
}

package me.hoyeonj.pricebasket.domain;

import java.math.BigDecimal;

public record ItemFactoryInput(
    String name,
    BigDecimal totalPrice,
    BigDecimal totalAmount,
    String unitSymbol,
    String rating,
    String detailsUrl,
    String imageUrl
) {

}

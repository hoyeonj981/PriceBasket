package me.hoyeonj.pricebasket.application.out.dto;

import java.math.BigDecimal;
import me.hoyeonj.pricebasket.domain.ItemFactoryInput;

public record RawItemData(
    String name,
    String price,
    String amount,
    String unitSymbol,
    String rating,
    String detailsUrl,
    String imageUrl
) {

  public ItemFactoryInput toItemInput() {
    return new ItemFactoryInput(
        this.name,
        new BigDecimal(price),
        new BigDecimal(this.amount),
        this.unitSymbol,
        this.rating,
        this.detailsUrl,
        this.imageUrl
    );
  }
}

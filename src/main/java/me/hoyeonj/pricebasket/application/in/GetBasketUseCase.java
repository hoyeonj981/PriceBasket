package me.hoyeonj.pricebasket.application.in;

import me.hoyeonj.pricebasket.application.in.dto.AllBasketResult;
import me.hoyeonj.pricebasket.application.in.dto.BasketResult;

public interface GetBasketUseCase {

  BasketResult getBasket(final String clientId, final String basketId);

  AllBasketResult getAllBaskets(final String clientId);
}

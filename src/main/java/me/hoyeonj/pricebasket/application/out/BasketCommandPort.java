package me.hoyeonj.pricebasket.application.out;

import me.hoyeonj.pricebasket.domain.Basket;

public interface BasketCommandPort {

  Basket save(Basket basket);
}

package me.hoyeonj.pricebasket.application.out;

import java.util.Optional;
import me.hoyeonj.pricebasket.domain.Basket;
import me.hoyeonj.pricebasket.domain.BasketId;

public interface BasketQueryPort {

  Optional<Basket> findById(BasketId basketId);
}

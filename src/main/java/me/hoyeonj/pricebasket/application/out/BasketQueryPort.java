package me.hoyeonj.pricebasket.application.out;

import java.util.List;
import java.util.Optional;
import me.hoyeonj.pricebasket.domain.Basket;
import me.hoyeonj.pricebasket.domain.BasketId;
import me.hoyeonj.pricebasket.domain.ClientId;

public interface BasketQueryPort {

  Optional<Basket> findById(BasketId basketId);

  List<Basket> findAll(ClientId domainClientId);
}

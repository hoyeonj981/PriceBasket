package me.hoyeonj.pricebasket.application.out;

import me.hoyeonj.pricebasket.domain.Basket;
import me.hoyeonj.pricebasket.domain.Client;
import me.hoyeonj.pricebasket.domain.ClientId;

public interface BasketRepository {

  Basket saveBasket(Client client, Basket basket);
  boolean existByClientId(ClientId clientId);
}

package me.hoyeonj.pricebasket.application.out;

import me.hoyeonj.pricebasket.domain.ClientEmail;
import me.hoyeonj.pricebasket.domain.ClientId;

public interface ClientQueryPort {

  boolean existByClientId(ClientId clientId);

  boolean existsByEmail(ClientEmail clientEmail);
}

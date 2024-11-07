package me.hoyeonj.pricebasket.application.out;

import me.hoyeonj.pricebasket.domain.Client;

public interface ClientCommandPort {

  Client save(Client newClient);
}

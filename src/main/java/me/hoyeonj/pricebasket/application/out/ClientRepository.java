package me.hoyeonj.pricebasket.application.out;

import me.hoyeonj.pricebasket.domain.Client;

public interface ClientRepository {

  Client save(Client client);

  boolean existsByEmail(String email);
}

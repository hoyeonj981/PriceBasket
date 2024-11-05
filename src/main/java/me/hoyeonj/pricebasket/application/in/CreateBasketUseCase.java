package me.hoyeonj.pricebasket.application.in;

import me.hoyeonj.pricebasket.application.in.dto.CreateBasketCommand;
import me.hoyeonj.pricebasket.application.in.dto.CreateBasketResult;

public interface CreateBasketUseCase {

  CreateBasketResult createBasket(CreateBasketCommand command);
}

package me.hoyeonj.pricebasket.application.service;

import me.hoyeonj.pricebasket.application.in.CreateBasketUseCase;
import me.hoyeonj.pricebasket.application.in.dto.CreateBasketCommand;
import me.hoyeonj.pricebasket.application.in.dto.CreateBasketResult;
import me.hoyeonj.pricebasket.application.out.BasketCommandPort;
import me.hoyeonj.pricebasket.application.out.ClientQueryPort;
import me.hoyeonj.pricebasket.domain.Basket;
import me.hoyeonj.pricebasket.domain.ClientId;

public class CreateBasketService implements CreateBasketUseCase {

  private final BasketCommandPort basketCommandPort;
  private final ClientQueryPort clientQueryPort;

  public CreateBasketService(final BasketCommandPort basketCommandPort,
      final ClientQueryPort clientQueryPort) {
    this.basketCommandPort = basketCommandPort;
    this.clientQueryPort = clientQueryPort;
  }

  @Override
  public CreateBasketResult createBasket(final CreateBasketCommand command) {
    final var clientId = ClientId.from(command.clientId());
    validateExistingClient(clientId);
    final var newBasket = Basket.withoutId(clientId.getValue());
    basketCommandPort.save(newBasket);
    return new CreateBasketResult(
        newBasket.getClientId(),
        newBasket.getBasketId(),
        newBasket.getCreatedAt()
    );
  }

  private void validateExistingClient(final ClientId clientId) {
    if (!clientQueryPort.existByClientId(clientId)) {
      throw new ClientNotFoundException("해당 사용자는 존재하지 않습니다. - " + clientId);
    }
  }
}

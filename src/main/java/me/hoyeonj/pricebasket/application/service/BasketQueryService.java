package me.hoyeonj.pricebasket.application.service;

import java.util.List;
import java.util.stream.Collectors;
import me.hoyeonj.pricebasket.application.in.GetBasketUseCase;
import me.hoyeonj.pricebasket.application.in.dto.AllBasketResult;
import me.hoyeonj.pricebasket.application.in.dto.BasketItem;
import me.hoyeonj.pricebasket.application.in.dto.BasketResult;
import me.hoyeonj.pricebasket.application.in.dto.BasketSummary;
import me.hoyeonj.pricebasket.application.out.BasketQueryPort;
import me.hoyeonj.pricebasket.application.out.ClientQueryPort;
import me.hoyeonj.pricebasket.domain.Basket;
import me.hoyeonj.pricebasket.domain.BasketId;
import me.hoyeonj.pricebasket.domain.ClientId;

public class BasketQueryService implements GetBasketUseCase {

  private final BasketQueryPort basketQueryPort;
  private final ClientQueryPort clientQueryPort;

  public BasketQueryService(
      final BasketQueryPort basketQueryPort,
      final ClientQueryPort clientQueryPort
  ) {
    this.basketQueryPort = basketQueryPort;
    this.clientQueryPort = clientQueryPort;
  }

  @Override
  public BasketResult getBasket(final String clientId, final String basketId) {
    final var domainClientId = validateExistingClient(clientId);
    final var domainBasket = basketQueryPort.findById(BasketId.from(basketId))
        .orElseThrow(() -> new BasketNotFoundException("존재하지 않는 장바구니입니다"));
    validateBasketOwner(domainClientId, domainBasket);
    return createBasketResult(domainBasket);
  }

  private void validateBasketOwner(final ClientId domainClientId, final Basket domainBasket) {
    final var basketOwner = domainBasket.getClientId();
    if(!basketOwner.equals(domainClientId.getValue())) {
      throw new NotBasketOwnerException("장바구니 소유자가 아닙니다");
    }
  }

  private BasketResult createBasketResult(final Basket domainBasket) {
    return new BasketResult(
        createDtoBasketItems(domainBasket),
        domainBasket.getTotalItemsPrice().getValue(),
        domainBasket.getBasketMallName(),
        domainBasket.getItemsCount()
    );
  }

  private List<BasketItem> createDtoBasketItems(final Basket domainBasket) {
    return domainBasket.getItems()
        .stream()
        .map(domainBasketItem -> new BasketItem(
            domainBasketItem.getItemId(), domainBasketItem.getQuantity()))
        .collect(Collectors.toUnmodifiableList());
  }

  private ClientId validateExistingClient(final String clientId) {
    final var domainClientId = ClientId.from(clientId);
    if (!clientQueryPort.existByClientId(domainClientId)) {
      throw new ClientNotFoundException("존재하지 않는 사용자입니다");
    }
    return domainClientId;
  }

  @Override
  public AllBasketResult getAllBaskets(final String clientId) {
    final var domainClientId = validateExistingClient(clientId);
    final var baskets = basketQueryPort.findAll(domainClientId);
    return createAllBasketResult(baskets);
  }

  private AllBasketResult createAllBasketResult(final List<Basket> baskets) {
    final var collect = baskets.stream()
        .map(domainBasket -> new BasketSummary(domainBasket.getBasketId(),
            domainBasket.getTotalItemsPrice().getValue(), domainBasket.getBasketMallName()))
        .collect(Collectors.toUnmodifiableList());
    return new AllBasketResult(collect);
  }
}

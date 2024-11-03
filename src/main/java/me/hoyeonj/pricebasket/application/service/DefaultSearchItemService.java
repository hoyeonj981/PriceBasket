package me.hoyeonj.pricebasket.application.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import me.hoyeonj.pricebasket.application.in.SearchItemsUseCase;
import me.hoyeonj.pricebasket.application.in.dto.SearchedItem;
import me.hoyeonj.pricebasket.application.in.dto.SearchedItemResult;
import me.hoyeonj.pricebasket.application.in.dto.SearchedItems;
import me.hoyeonj.pricebasket.application.out.ItemSearchPort;
import me.hoyeonj.pricebasket.application.out.dto.RawItemData;
import me.hoyeonj.pricebasket.domain.Item;
import me.hoyeonj.pricebasket.domain.ItemFactory;
import me.hoyeonj.pricebasket.domain.ItemFactoryInput;
import me.hoyeonj.pricebasket.domain.MallType;
import me.hoyeonj.pricebasket.domain.UnitPriceCalculator;

public class DefaultSearchItemService implements SearchItemsUseCase {

  private final ItemSearchPort itemSearchPort;
  private final UnitPriceCalculator calculator;
  private final ItemFactory itemFactory;
  private final Set<MallType> supportedMalls;

  public DefaultSearchItemService(final ItemSearchPort itemSearchPort,
      final UnitPriceCalculator calculator) {
    this.itemSearchPort = itemSearchPort;
    this.calculator = calculator;
    this.itemFactory = new ItemFactory(this.calculator);
    this.supportedMalls = Set.of(MallType.EMARTMALL, MallType.HOMEPLUS);
  }

  /*
   * 1. 외부에서 검색 결과를 얻는다.
   * 2. 도메인 객체로 만들어서 비지니스 로직을 수행한다
   * 3. 비지니스 로직 수행 후 결과를 반환한다.
   *
   * 예외 사항
   * 1. 검색 결과가 없을 수도 있다.
   * 2. 없다면 없다는 의미를 포함해야 한다.
   */
  @Override
  public SearchedItemResult search(final String keyword) {
    final var searchedItems = itemSearchPort.searchItems(keyword);

    final var domainItemsByMalls = searchedItems.response()
        .entrySet()
        .stream()
        .collect(Collectors.toMap(
            entry -> MallType.from(entry.getKey()),
            entry -> convertToDomainItems(entry.getValue(), entry.getKey())
        ));

    return convertToResult(domainItemsByMalls);
  }

  private SearchedItemResult convertToResult(final Map<MallType, List<Item>> domainItemsByMalls) {
    final var searchResultsByMall = domainItemsByMalls.entrySet()
        .stream()
        .collect(Collectors.toMap(
            entry -> entry.getKey().getName(),
            this::convertToSearchedItems,
            (v1, v2) -> v1));

    return new SearchedItemResult(searchResultsByMall);
  }

  private SearchedItems convertToSearchedItems(final Map.Entry<MallType, List<Item>> entry) {
    final var domainItems = entry.getValue();

    if (domainItems == null || domainItems.isEmpty()) {
      return SearchedItems.noResults();
    }

    final var searchedItems = domainItems.stream()
        .map(this::convertToSearchedItem)
        .collect(Collectors.toUnmodifiableList());
    return SearchedItems.success(searchedItems);
  }

  private SearchedItem convertToSearchedItem(Item item) {
    return new SearchedItem(
        item.getName(),
        item.getTotalPriceToString(),
        item.getMeasurementTypeToString(),
        item.getUnitSymbol(),
        item.getRating(),
        item.getDetailsUrlToString(),
        item.getImageUrlToString()
    );
  }

  private List<Item> convertToDomainItems(final List<RawItemData> itemDatas, final String mallType) {
    return itemDatas.stream()
        .map(RawItemData::toItemInput)
        .map(input -> createDomainItem(input, MallType.from(mallType)))
        .collect(Collectors.toUnmodifiableList());
  }

  private Item createDomainItem(final ItemFactoryInput itemInput, final MallType mallType) {
    return switch(mallType) {
      case EMARTMALL -> itemFactory.createEmarMallItem(itemInput);
      case HOMEPLUS -> itemFactory.createHomeplusItem(itemInput);
    };
  }
}

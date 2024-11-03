package me.hoyeonj.pricebasket.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import me.hoyeonj.pricebasket.application.in.dto.SearchStatus;
import me.hoyeonj.pricebasket.application.out.ItemSearchPort;
import me.hoyeonj.pricebasket.application.out.dto.RawItemData;
import me.hoyeonj.pricebasket.application.out.dto.SearchedItemsResponse;
import me.hoyeonj.pricebasket.domain.ItemFactory;
import me.hoyeonj.pricebasket.domain.UnitPriceCalculator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DefaultSearchItemServiceTest {

  @Mock
  private ItemSearchPort itemSearchPort;

  @Mock
  private UnitPriceCalculator calculator;

  @Mock
  private ItemFactory itemfactory;

  @InjectMocks
  private DefaultSearchItemService searchItemService;

  @DisplayName("키워드가 주어지면 검색된 상품을 결과로 반환한다")
  @Test
  void searchItemsWhenKeywordGiven() {
    var givenKeyword = "만두";
    var mockResponse = Map.of(
        "Emart", List.of(createMockRawItemData(givenKeyword)),
        "Homeplus", List.of(createMockRawItemData(givenKeyword))
    );
    when(itemSearchPort.searchItems(givenKeyword))
        .thenReturn(new SearchedItemsResponse(mockResponse));

    var actual = searchItemService.search(givenKeyword);

    assertThat(actual.result()).hasSize(2);
    verify(itemSearchPort).searchItems(givenKeyword);
  }

  @DisplayName("키워드 검색 결과가 없다면 비어있는 결과를 반환한다")
  @Test
  void giveEmptyResultWhenSearchedKeywordDoesNotExist() {
    var givenKeyword = "존재하지않는 키워드";
    Map<String, List<RawItemData>> mockResponse = Map.of(
        "emart", Collections.emptyList(),
        "homeplus", Collections.emptyList()
    );
    when(itemSearchPort.searchItems(givenKeyword))
        .thenReturn(new SearchedItemsResponse(mockResponse));

    var actual = searchItemService.search(givenKeyword);

    assertThat(actual.result())
        .hasSize(2)
        .containsKeys("emart", "homeplus")
        .allSatisfy((mallName, searchedItems) -> {
          assertThat(searchedItems)
              .satisfies(items -> {
                assertThat(items.status()).isEqualTo(SearchStatus.NO_RESULTS);
                assertThat(items.itemsCount()).isZero();
                assertThat(items.items()).isEmpty();
                assertThat(items.hasResults()).isFalse();
              });
        });
    verify(itemSearchPort).searchItems(givenKeyword);
  }

  private RawItemData createMockRawItemData(final String givenKeyword) {
    return new RawItemData(
        givenKeyword,
        "1000",
        "1000",
        "g",
        "4.5",
        "https://imageurl.url",
        "https://details.url"
    );
  }
}
package me.hoyeonj.pricebasket.adapter.out;

import static me.hoyeonj.pricebasket.adapter.out.EmartMallItemInfoFixture.createItemInfoFixture;
import static me.hoyeonj.pricebasket.adapter.out.EmartMallTestHtml.TEST_HTML;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class HomePlusCrawlerTest {

  @Mock
  private HomePlusHtmlFetcher fetcher;

  @Mock
  private HomePlusParser parser;

  private HomePlusCrawler crawler;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    crawler = new HomePlusCrawler(fetcher, parser);
  }

  @DisplayName("주어진 검색어로 검색 후 파싱된 결과를 가져온다")
  @Test
  void get_parsed_document_using_given_query() {
    var parameters = "givenParameters";
    var htmlDocument = new HtmlDocument(TEST_HTML);
    var expectedQuery = new HomePlusSearchQuery(parameters);
    var expectedResult = new HomePlusParsedDocument(
        Arrays.asList(createItemInfoFixture(), createItemInfoFixture()));

    when(fetcher.fetchFrom(any(HomePlusSearchQuery.class))).thenReturn(htmlDocument);
    when(parser.parseDocument(htmlDocument)).thenReturn(expectedResult);

    var actual = crawler.getSearchResult(parameters);

    assertThat(actual).isEqualTo(expectedResult);
    verify(fetcher).fetchFrom(eq(expectedQuery));
    verify(parser).parseDocument(htmlDocument);
  }

  private HomePlusItemInfo createItemInfoFixture() {
    return HomePlusItemInfo.builder()
        .name("testName")
        .price("1000")
        .unitOfPrice("10g of 1000")
        .rating("4.8")
        .detailsUrl("testUrl").
        imageUrl("testUrl")
        .build();
  }
}
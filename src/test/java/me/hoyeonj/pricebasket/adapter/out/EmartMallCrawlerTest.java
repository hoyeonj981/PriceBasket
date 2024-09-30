package me.hoyeonj.pricebasket.adapter.out;

import static me.hoyeonj.pricebasket.adapter.out.EmartMallItemInfoFixture.createItemInfoFixture;
import static me.hoyeonj.pricebasket.adapter.out.EmartMallTestHtml.TEST_HTML;
import static org.assertj.core.api.Assertions.assertThat;
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

class EmartMallCrawlerTest {

  @Mock
  private EmartMallHtmlFetcher fetcher;

  @Mock
  private EmartMallParser parser;

  private EmartMallCrawler crawler;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    crawler = new EmartMallCrawler(fetcher, parser);
  }

  @DisplayName("주어진 검색어로 검색 후 파싱된 결과를 가져온다")
  @Test
  void get_parsed_document_using_given_query() {
    var parameters = "givenParameters";
    var htmlDocument = new HtmlDocument(TEST_HTML);
    var expectedQuery = new EmartMallSearchQuery(parameters);
    var expectedResult = new EmartMallParsedDocument(
        Arrays.asList(createItemInfoFixture(), createItemInfoFixture()),
        10,
        1);

    when(fetcher.fetchFrom(any(EmartMallSearchQuery.class))).thenReturn(htmlDocument);
    when(parser.parseDocument(htmlDocument)).thenReturn(expectedResult);

    var actual = crawler.getSearchResult(parameters);

    assertThat(actual).isEqualTo(expectedResult);
    verify(fetcher).fetchFrom(eq(expectedQuery));
    verify(parser).parseDocument(htmlDocument);
  }
}

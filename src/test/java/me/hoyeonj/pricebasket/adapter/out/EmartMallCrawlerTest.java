package me.hoyeonj.pricebasket.adapter.out;

import static me.hoyeonj.pricebasket.adapter.out.EmartMallItemInfoFixture.*;
import static me.hoyeonj.pricebasket.adapter.out.EmartMallTestHtml.TEST_HTML;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
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

  @Test
  void 주어진_검색어로_검색_후_파싱된_결과를_가져온다() {
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

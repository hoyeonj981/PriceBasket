package me.hoyeonj.pricebasket.adapter.out;

import static me.hoyeonj.pricebasket.adapter.out.EmartMallTestHtml.TEST_HTML;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class EmartMallHtmlFetcherTest {

  @Mock
  private ApacheHttpClient httpClient;

  @InjectMocks
  private EmartMallHtmlFetcher fetcher;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @DisplayName("쿼리결과는 HTML를 가져와야 한다")
  @Test
  void query_result_should_have_html() {
    var keyword = "test";
    var searchQuery = new EmartMallSearchQuery(keyword);
    var given = new ResponseDocument(null, 0, null, TEST_HTML);
    when(httpClient.fetchFromUri(any())).thenReturn(given);

    var emartHtmlDocument = fetcher.fetchFrom(searchQuery);
    var actual = emartHtmlDocument.getContent();

    assertThat(actual.contains("<html>")).isTrue();
  }

  @DisplayName("잘못된 HTML 형식은 예외가 발생한다")
  @Test
  void throw_exception_when_html_format_is_not_correct() {
      var mockHtml = "{\"test\":\"test\"}";
      var keyword = "test";
      var searchQuery = new EmartMallSearchQuery(keyword);
      var given = new ResponseDocument(null, 0, null, mockHtml);
      when(httpClient.fetchFromUri(any())).thenReturn(given);

      assertThatThrownBy(() -> fetcher.fetchFrom(searchQuery))
          .isInstanceOf(NotHtmlDocumentException.class);
  }

  @DisplayName("쿼리결과가 404 에러가 발생하면 예외가 발생한다")
  @Test
  void thorw_exception_when_query_result_has_404_error() {
    var keyword = "test";
    var searchQuery = new EmartMallSearchQuery(keyword);
    var given = new ResponseDocument(null, 404, null, TEST_HTML);
    when(httpClient.fetchFromUri(any())).thenReturn(given);

    assertThatThrownBy(() -> fetcher.fetchFrom(searchQuery))
        .isInstanceOf(PageNotFoundException.class);
  }

  @DisplayName("쿼리결과가 500 에러가 발생하면 예외가 발생한다")
  @Test
  void throw_exception_when_query_result_has_500_error() {
    var keyword = "test";
    var searchQuery = new EmartMallSearchQuery(keyword);
    var given = new ResponseDocument(null, 500, null, TEST_HTML);
    when(httpClient.fetchFromUri(any())).thenReturn(given);

    assertThatThrownBy(() -> fetcher.fetchFrom(searchQuery))
        .isInstanceOf(InternalServerErrorException.class);

  }
}

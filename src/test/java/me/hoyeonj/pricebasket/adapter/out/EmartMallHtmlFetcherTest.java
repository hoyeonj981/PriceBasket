package me.hoyeonj.pricebasket.adapter.out;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class EmartMallHtmlFetcherTest {

  @Mock
  private HttpClient httpClient;

  @InjectMocks
  private EmartMallHtmlFetcher fetcher;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void 쿼리결과는_HTML를_가져와야_한다() {
    var mockHtml = "<html></html>";
    var keyword = "test";
    var searchQuery = new EmartMallSearchQuery(keyword);
    var given = new ResponseDocument(null, 0, null, mockHtml);
    when(httpClient.fetchFromUri(any())).thenReturn(given);

    var emartHtmlDocument = fetcher.fetchFrom(searchQuery);
    var actual = emartHtmlDocument.getContent();

    assertThat(actual.contains("<html>")).isTrue();
  }

  @Test
  void 잘못된_HTML_형식은_예외가_발생한다() {
      var mockHtml = "{\"test\":\"test\"}";
      var keyword = "test";
      var searchQuery = new EmartMallSearchQuery(keyword);
      var given = new ResponseDocument(null, 0, null, mockHtml);
      when(httpClient.fetchFromUri(any())).thenReturn(given);

      assertThatThrownBy(() -> fetcher.fetchFrom(searchQuery))
          .isInstanceOf(NotHtmlDocumentException.class);
  }

  @Test
  void 쿼리결과가_404_에러가_발생하면_예외가_발생한다() {
    var mockHtml = "<html></html>";
    var keyword = "test";
    var searchQuery = new EmartMallSearchQuery(keyword);
    var given = new ResponseDocument(null, 404, null, mockHtml);
    when(httpClient.fetchFromUri(any())).thenReturn(given);

    assertThatThrownBy(() -> fetcher.fetchFrom(searchQuery))
        .isInstanceOf(PageNotFoundException.class);
  }

  @Test
  void 쿼리결과가_500_에러가_발생하면_예외가_발생한다() {
    var mockHtml = "<html></html>";
    var keyword = "test";
    var searchQuery = new EmartMallSearchQuery(keyword);
    var given = new ResponseDocument(null, 500, null, mockHtml);
    when(httpClient.fetchFromUri(any())).thenReturn(given);

    assertThatThrownBy(() -> fetcher.fetchFrom(searchQuery))
        .isInstanceOf(InternalServerErrorException.class);

  }
}

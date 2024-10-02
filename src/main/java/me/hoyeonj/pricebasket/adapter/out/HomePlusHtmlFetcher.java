package me.hoyeonj.pricebasket.adapter.out;

public class HomePlusHtmlFetcher {

  private static final HomePlusUrl SEARCH_URL = HomePlusUrl.HTTPS_SEARCH_URL;

  private final ApacheHttpClient client;

  public HomePlusHtmlFetcher(final ApacheHttpClient client) {
    this.client = client;
  }

  public HtmlDocument fetchFrom(final HomePlusSearchQuery query) {
    final var uri = SEARCH_URL.createSearchUrl(query.query());
    final var responseDocument = client.fetchFromUri(uri);
    if (!responseDocument.content().contains("<!doctype html>")) {
      throw new NotHtmlDocumentException("해당 uri에 대한 응답이 html 형식이 아닙니다. - "
          + responseDocument.url());
    }
    if (responseDocument.statusCode() == 404) {
      throw new PageNotFoundException("해당 uri에 대한 응답 페이지를 찾을 수 없습니다. - "
          + responseDocument.url());
    }
    if (responseDocument.statusCode() == 500) {
      throw new InternalServerErrorException("해당 uri에 대한 요청을 서버에서 응답하지 않습니다. - "
          + responseDocument.url());
    }
    return new HtmlDocument(responseDocument.content());
  }
}

package me.hoyeonj.pricebasket.adapter.out;

public class EmartHtmlFetcher {

  private static final EmartMallUrl SEARCH_URL = EmartMallUrl.HTTPS_SEARCH_URL;

  private final HttpClient client;

  public EmartHtmlFetcher(final HttpClient client) {
    this.client = client;
  }

  public EmartHtmlDocument fetchFrom(final EmartMallSearchQuery query) {
    var uri = SEARCH_URL.createSearchUrl(query.query());
    var responseDocument = client.fetchFromUri(uri);
    if (!responseDocument.getContent().contains("<html>")) {
      throw new NotHtmlDocumentException("해당 uri에 대한 응답이 html 형식이 아닙니다. - "
          + responseDocument.getUrl());
    }
    if (responseDocument.getStatusCode() == 404) {
      throw new PageNotFoundException("해당 uri에 대한 응답 페이지를 찾을 수 없습니다. - "
          + responseDocument.getUrl());
    }
    if (responseDocument.getStatusCode() == 500) {
      throw new InternalServerErrorException("해당 uri에 대한 요청을 서버에서 응답하지 않습니다. - "
          + responseDocument.getUrl());
    }
    return new EmartHtmlDocument(responseDocument.getContent());
  }
}

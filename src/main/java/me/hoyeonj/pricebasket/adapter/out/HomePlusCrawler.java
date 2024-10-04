package me.hoyeonj.pricebasket.adapter.out;

public class HomePlusCrawler {

  private final HomePlusHtmlFetcher fetcher;
  private final HomePlusParser parser;

  public HomePlusCrawler(final HomePlusHtmlFetcher fetcher, final HomePlusParser parser) {
    this.fetcher = fetcher;
    this.parser = parser;
  }

  public HomePlusParsedDocument getSearchResult(final String parameters) {
    final var htmlDocument = fetcher.fetchFrom(new HomePlusSearchQuery(parameters));
    return parser.parseDocument(htmlDocument);
  }
}

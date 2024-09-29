package me.hoyeonj.pricebasket.adapter.out;

public class EmartMallCrawler {

  private final EmartMallHtmlFetcher fetcher;
  private final EmartMallParser parser;

  public EmartMallCrawler(final EmartMallHtmlFetcher fetcher, final EmartMallParser parser) {
    this.fetcher = fetcher;
    this.parser = parser;
  }

  public EmartMallParsedDocument getSearchResult(final String parameters) {
    final var htmlDocument = fetcher.fetchFrom(new EmartMallSearchQuery(parameters));
    return parser.parseDocument(htmlDocument);
  }
}

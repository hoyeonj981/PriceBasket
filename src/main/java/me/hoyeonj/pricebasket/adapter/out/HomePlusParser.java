package me.hoyeonj.pricebasket.adapter.out;

import java.util.stream.Collectors;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

public class HomePlusParser {

  private static final String ITEM_LIST_DIV = "div.unitItem";
  private static final String ITEM_NAME_SELECTOR = "p.css-xktqki";
  private static final String DETAILS_URL_SELECTOR = "a";
  private static final String DETAILS_URL = "href";
  private static final String IMG_SELECTOR = "img";
  private static final String IMAGE_URL = "src";
  private static final String PRICE_SELECTOR = "div.price";
  private static final String UNIT_OF_PRICE_SELECTOR = "div.priceQty";
  private static final String RATING_SELECTOR = "span.score";

  public HomePlusParsedDocument parseDocument(final HtmlDocument htmlDocument) {
    final var document = Jsoup.parse(htmlDocument.content());
    final var elements = document.select(ITEM_LIST_DIV);
    final var collect = elements.stream()
        .map(element -> HomePlusItemInfo.builder()
                .name(getItemName(element))
                .price(getPrice(element))
                .unitOfPrice(getUnitOfPrice(element))
                .rating(getRating(element))
                .detailsUrl(getDetailsUrl(element))
                .imageUrl(getImageUrl(element))
                .build())
        .collect(Collectors.toUnmodifiableList());
    return new HomePlusParsedDocument(collect);
  }

  private String getRating(final Element element) {
    return element.select(RATING_SELECTOR).text();
  }

  private String getUnitOfPrice(final Element element) {
    return element.select(UNIT_OF_PRICE_SELECTOR).text();
  }

  private String getPrice(final Element element) {
    return element.select(PRICE_SELECTOR).text();
  }

  private String getImageUrl(final Element element) {
    return element.select(IMG_SELECTOR).attr(IMAGE_URL);
  }

  private String getDetailsUrl(final Element element) {
    return element.select(DETAILS_URL_SELECTOR).attr(DETAILS_URL);
  }

  private String getItemName(final Element element) {
    return element.select(ITEM_NAME_SELECTOR).text();
  }
}

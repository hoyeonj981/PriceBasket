package me.hoyeonj.pricebasket.adapter.out;

import static java.util.stream.Collectors.toUnmodifiableList;

import java.util.List;
import java.util.Objects;

public class EmartMallParser {

  protected static final String ITEM_LIST_DIV_CSSQUERY = "div.tmpl_itemlist";
  protected static final String ITEM_LIST_LI_CSSQUERY = ".mnemitem_grid_item";
  protected static final String CURRENT_PAGE_CSSQUERY = "#item_navi > div > strong";
  protected static final String PAGES_NAVIGATOR_CSSQUERY = "#item_navi > div > a";
  protected static final String IMAGE_CSSQUERY = "img";
  protected static final String SRC_ATTRIBUTE_KEY = "src";

  private final EmartMallItemConverter converter;
  private final JsoupHtmlParser htmlParser;

  public EmartMallParser(final EmartMallItemConverter converter, final JsoupHtmlParser htmlParser) {
    this.converter = converter;
    this.htmlParser = htmlParser;
  }

  public EmartMallParsedDocument parseDocument(final HtmlDocument htmlDocument) {
    validateDocument(htmlDocument);
    final var htmlText = htmlDocument.getContent();
    final var itemListDiv = htmlParser.getElementByCssQuery(htmlText, ITEM_LIST_DIV_CSSQUERY);
    validateElement(itemListDiv, ITEM_LIST_DIV_CSSQUERY);
    final var itemListLi = htmlParser.getElementsByCssQuery(itemListDiv, ITEM_LIST_LI_CSSQUERY);
    validateElements(itemListLi, ITEM_LIST_LI_CSSQUERY);

    final var itemList = itemListLi.stream()
        .map(element -> {
          final var text = htmlParser.getText(element);
          final var imageUrl = htmlParser.getFirstAttributeValue(element, IMAGE_CSSQUERY,
              SRC_ATTRIBUTE_KEY);
          return converter.convert(text, imageUrl); })
        .collect(toUnmodifiableList());
    final var pages = htmlParser.getElementsByCssQuery(htmlText, PAGES_NAVIGATOR_CSSQUERY).size();
    final var currentPage = Integer.parseInt(
        htmlParser.getElementByCssQuery(htmlText, CURRENT_PAGE_CSSQUERY));

    return new EmartMallParsedDocument(itemList, pages, currentPage);
  }

  private void validateElements(final List<String> elements, final String query) {
    if (elements.isEmpty()) {
      throw new ElementNotFoundException("파싱된 결과가 존재하지 않습니다. - " + query);
    }
  }

  private void validateElement(final String element, final String query) {
    if (element.isBlank()) {
      throw new ElementNotFoundException("파싱된 결과가 존재하지 않습니다. - " + query);
    }
  }

  private void validateDocument(final HtmlDocument htmlDocument) {
    if (Objects.isNull(htmlDocument)) {
      throw new IllegalArgumentException("htmlDocument는 null일 수 없습니다.");
    }
    var content = htmlDocument.getContent();
    if (Objects.isNull(content) || content.isBlank()) {
      throw new EmptyHtmlDocumentException("html 문서는 null이거나 비어있을 수 없습니다.");
    }
  }
}

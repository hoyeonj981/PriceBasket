package me.hoyeonj.pricebasket.adapter.out;

import static java.util.stream.Collectors.toUnmodifiableList;

import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Node;

public class JsoupHtmlParser {

  public String getElementByCssQuery(final String html, final String cssQuery) {
    final var document = Jsoup.parse(html);
    return document.select(cssQuery).toString();
  }

  public List<String> getElementsByCssQuery(final String html, final String cssQuery) {
    final var document = Jsoup.parse(html);
    return document.select(cssQuery)
        .stream()
        .map(Node::outerHtml)
        .collect(toUnmodifiableList());
  }

  public String getFirstAttributeValue(final String html, final String cssQuery,
      final String value) {
    final var document = Jsoup.parse(html);
    return document.select(cssQuery)
        .first()
        .attr(value);
  }

  public String getText(final String html) {
    final var document = Jsoup.parse(html);
    return document.text();
  }
}

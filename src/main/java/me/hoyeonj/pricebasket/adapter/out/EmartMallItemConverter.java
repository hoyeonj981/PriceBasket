package me.hoyeonj.pricebasket.adapter.out;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.regex.Pattern;
import org.jsoup.nodes.Element;

public class EmartMallItemConverter {

  private static final String NAME = "\"itemNm\":\"";
  private static final String BRAND_NAME = "\"brandNm\":\"";
  private static final String PRICE = "\"displayPrc\":\"";
  private static final String DETAILS_URL = "\"itemLnkd\":\"";
  private static final String DELIMITER = "\"";

  public EmartMallItemInfo convert(final String text, final String imageUrl) {
    if (Objects.isNull(text) || text.isBlank()) {
      throw new EmptyTextException("주어진 상품 텍스트가 비어 있습니다.");
    }
    if (Objects.isNull(imageUrl)) {
      throw new InvalidImageUrlException("유효하지 않는 이미지 url입니다. " + imageUrl);
    }
    return EmartMallItemInfo.builder()
        .name(extractName(text))
        .brandName(extractBrandName(text))
        .price(extractPrice(text))
        .unitOfPrice(extractUnitOfPrice(text))
        .rating(extractRating(text))
        .detailsUrl(extractDetailsUrl(text))
        .imageUrl(imageUrl)
        .build();
  }

  private String extractName(final String text) {
    final var extracted = extract(text, NAME);
    if (extracted.isBlank()) {
      throw new ItemNameNotFoundException("상품 이름이 없습니다.");
    }
    return extracted;
  }

  private String extractBrandName(final String text) {
    return extract(text, BRAND_NAME);
  }

  private String extractPrice(final String text) {
    final var extracted = extract(text, PRICE);
    final var price = new BigDecimal(extracted);
    if (price.compareTo(BigDecimal.ZERO) <= 0) {
      throw new InvalidItemPriceException("상품 가격은 0이거나 음수일 수 없습니다. - " + price);
    }
    return extracted;
  }

  private String extractUnitOfPrice(final String text) {
    final var unitOfPricePattern = "\\d+(매|g|ml) 당 \\d+원";
    final var pattern = Pattern.compile(unitOfPricePattern);
    final var matcher = pattern.matcher(text);
    if (matcher.find()) {
      return matcher.group();
    }
    return "";
  }

  private String extractRating(final String text) {
    var reviewStart = text.indexOf("상품평점");
    return text.substring(reviewStart + 12, reviewStart + 15);
  }

  private String extractDetailsUrl(final String text) {
    final var extracted = extract(text, DETAILS_URL);
    if (extracted.isBlank()) {
      throw new ItemDetailNotFoundException("상품 상세 페이지가 없습니다.");
    }
    return extracted;
  }

  private String extract(final String text, final String startKey) {
    int start = text.indexOf(startKey) + startKey.length();
    int end = text.indexOf(DELIMITER, start);
    return text.substring(start, end);
  }
}

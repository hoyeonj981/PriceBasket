package me.hoyeonj.pricebasket.adapter.out;

import java.util.Objects;

public record EmartMallSearchQuery(
    String query
) {

  public EmartMallSearchQuery {
    if (Objects.isNull(query) || query.isBlank()) {
      throw new InvalidQueryParameterException("유요하지 않는 Emart 쿼리 파라미터입니다. - "
          + query);
    }
  }
}

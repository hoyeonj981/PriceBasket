package me.hoyeonj.pricebasket.adapter.out;

import java.util.Objects;

record HomePlusSearchQuery(
    String query
) {

  HomePlusSearchQuery {
    if (Objects.isNull(query) || query.isBlank()) {
      throw new InvalidQueryParameterException("유효하지 않는 homeplus 쿼리 파라미터입니다. - "
          + query);
    }
  }
}

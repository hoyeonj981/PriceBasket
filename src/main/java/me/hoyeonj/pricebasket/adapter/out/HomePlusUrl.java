package me.hoyeonj.pricebasket.adapter.out;

import java.util.Objects;

enum HomePlusUrl {

  HTTPS_SEARCH_URL(
      Protocol.HTTPS,
      "mfront.homeplus.co.kr",
      "search",
      "TD",
      "entry=direct"
  );

  private static final String KEYWORD_PARAMETER = "inputKeyword";
  private static final String MALLTYPE_PARAMETER = "mallType";
  private static final String PATH_DELIMETER = "/";
  private static final String QUERY_DELIMETER = "?";
  private static final String VALUE_ASSIGNER = "=";
  private static final String PARAMETER_SEPARATOR = "&";

  private final Protocol protocol;
  private final String domain;
  private final String path;
  private final String mallType;
  private final String defaultParameter;

  HomePlusUrl(final Protocol protocol, final String domain, final String path,
      final String mallType, final String defaultParameter) {
    this.protocol = protocol;
    this.domain = domain;
    this.path = path;
    this.mallType = mallType;
    this.defaultParameter = defaultParameter;
  }

  // https://mfront.homeplus.co.kr/search?entry=direct&inputKeyword=%EA%B0%90%EC%9E%90&mallType=TD
  public String createSearchUrl(final String queryParameterValue) {
    validateQueryParameter(queryParameterValue);

    final var builder = new StringBuilder();
    builder.append(this.protocol.getValue());
    builder.append(this.domain);
    builder.append(PATH_DELIMETER);
    builder.append(this.path);
    builder.append(QUERY_DELIMETER);
    builder.append(this.defaultParameter);
    builder.append(PARAMETER_SEPARATOR);
    builder.append(KEYWORD_PARAMETER);
    builder.append(VALUE_ASSIGNER);
    builder.append(queryParameterValue);
    builder.append(PARAMETER_SEPARATOR);
    builder.append(MALLTYPE_PARAMETER);
    builder.append(VALUE_ASSIGNER);
    builder.append(this.mallType);

    return builder.toString();
  }

  private void validateQueryParameter(final String queryParameterValue) {
    if (Objects.isNull(queryParameterValue) || queryParameterValue.isBlank()) {
      throw new InvalidQueryParameterException("유요하지 않는 homeplus 쿼리 파라미터입니다. - "
          + queryParameterValue);
    }
  }
}

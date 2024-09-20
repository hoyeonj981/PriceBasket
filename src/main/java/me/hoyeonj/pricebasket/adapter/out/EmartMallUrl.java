package me.hoyeonj.pricebasket.adapter.out;

enum EmartMallUrl {

  HTTPS_SEARCH_URL(
      Protocol.HTTPS,
      "emart.ssg.com",
      "search.ssg",
      "all",
      "ssgem"
  );

  private static final String TARGET_PARAMETER = "target";
  private static final String QUERY_PARAMETER = "query";
  private static final String SHOP_PARAMETER = "shpp";
  private static final String PATH_DELIMETER = "/";
  private static final String QUERY_DELIMETER = "?";
  private static final String VALUE_ASSIGNER = "=";
  private static final String PARAMETER_SEPARATOR = "&";

  private final Protocol protocol;
  private final String domain;
  private final String path;
  private final String targetParameterValue;
  private final String shopParameterValue;

  EmartMallUrl(final Protocol protocol, final String domain, final String path,
      final String targetParameterValue, final String shopParameterValue) {
    this.protocol = protocol;
    this.domain = domain;
    this.path = path;
    this.targetParameterValue = targetParameterValue;
    this.shopParameterValue = shopParameterValue;
  }

  public String createSearchUrl(final String queryParameterValue) {
    var builder = new StringBuilder();
    builder.append(this.protocol.getValue());
    builder.append(this.domain);
    builder.append(PATH_DELIMETER);
    builder.append(this.path);
    builder.append(QUERY_DELIMETER);
    builder.append(TARGET_PARAMETER);
    builder.append(VALUE_ASSIGNER);
    builder.append(this.targetParameterValue);
    builder.append(PARAMETER_SEPARATOR);
    builder.append(QUERY_PARAMETER);
    builder.append(VALUE_ASSIGNER);
    builder.append(queryParameterValue);
    builder.append(PARAMETER_SEPARATOR);
    builder.append(SHOP_PARAMETER);
    builder.append(VALUE_ASSIGNER);
    builder.append(this.shopParameterValue);

    return builder.toString();
  }
  //https://emart.ssg.com/search.ssg?target=all&query=%EA%B0%90%EC%9E%90&shpp=ssgem
}

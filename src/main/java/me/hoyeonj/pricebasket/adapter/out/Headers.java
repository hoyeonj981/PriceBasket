package me.hoyeonj.pricebasket.adapter.out;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

class Headers {
  private final Map<String, HeaderValue> headerMap;

  Headers() {
    this.headerMap = new LinkedHashMap<>();
  }

  Headers(Map<String, List<String>> headers) {
    this.headerMap = headers.entrySet()
        .stream()
        .collect(Collectors.toMap(
            Map.Entry::getKey,
            e -> new HeaderValue(e.getValue()),
            (v1, v2) -> v1,
            LinkedHashMap::new)
        );
  }

  void add(final String name, final String value) {
    headerMap.computeIfAbsent(name, k -> new HeaderValue()).add(value);
  }

  Optional<HeaderValue> get(final String name) {
    return Optional.ofNullable(headerMap.get(name));
  }

  Map<String, List<String>> toMap() {
    return headerMap.entrySet()
        .stream()
        .collect(Collectors.toUnmodifiableMap(
            Map.Entry::getKey,
            e -> e.getValue().getValues()));
  }

  @Override
  public String toString() {
    return headerMap.entrySet()
        .stream()
        .map(e -> e.getKey() + ": " + e.getValue())
        .collect(Collectors.joining("\n"));
  }
}

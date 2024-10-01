package me.hoyeonj.pricebasket.adapter.out;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class HeaderValue {
  private final List<String> values;

  HeaderValue() {
    this.values = new ArrayList<>();
  }

  HeaderValue(final List<String> values) {
    this.values = values;
  }

  void add(final String value) {
    this.values.add(value);
  }

  List<String> getValues() {
    return Collections.unmodifiableList(values);
  }

  @Override
  public String toString() {
    return String.join(", ", values);
  }
}

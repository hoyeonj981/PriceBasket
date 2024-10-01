package me.hoyeonj.pricebasket.adapter.out;

enum Protocol {
  HTTPS("https://");

  private final String value;

  Protocol(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
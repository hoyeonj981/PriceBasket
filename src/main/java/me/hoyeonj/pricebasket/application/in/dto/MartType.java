package me.hoyeonj.pricebasket.application.in.dto;

enum MartType {

  EMART("emart"),
  HOMEPLUS("homeplus");

  private final String name;

  MartType(final String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
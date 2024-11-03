package me.hoyeonj.pricebasket.application.in.dto;

public enum SearchStatus {
  SUCCESS("검색 성공"),
  NO_RESULTS("검색 결과가 없습니다");

  private final String message;

  SearchStatus(final String message) {
    this.message = message;
  }
}

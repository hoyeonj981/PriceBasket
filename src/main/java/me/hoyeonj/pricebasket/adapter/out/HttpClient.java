package me.hoyeonj.pricebasket.adapter.out;

public interface HttpClient {

  ResponseDocument fetchFromUri(final String uri);
}

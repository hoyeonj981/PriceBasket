package me.hoyeonj.pricebasket.adapter.out;

import java.util.List;
import java.util.Map;

public record ResponseDocument(
  String url,
  int statusCode,
  Map<String, List<String>> headers,
  String content
) { }

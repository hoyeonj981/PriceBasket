package me.hoyeonj.pricebasket.adapter.out;

import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.options.LoadState;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class PlaywrightHttpClient {

  private static final String SEPERATOR = "[;\\n]";

  public ResponseDocument fetchFromUri(final String uri) {
    try (final var playwright = Playwright.create();
        final var browser = playwright.chromium().launch();
        final var page = browser.newPage()) {
      final var response = page.navigate(uri);
      page.waitForLoadState(LoadState.NETWORKIDLE);
      final var headers = response.allHeaders()
          .entrySet()
          .stream()
          .collect(Collectors.toMap(
              Entry::getKey,
              this::separateValues));
      return ResponseDocument.create(uri, response.status(), headers, page.content());
    } catch (PlaywrightException e) {
      throw new HttpRequestException(e);
    }
  }

  private List<String> separateValues(final Entry<String, String> e) {
    final var value = e.getValue();
    return Arrays.stream(value.split(SEPERATOR))
        .map(String::trim)
        .filter(s -> !s.isEmpty())
        .collect(Collectors.toList());
  }
}

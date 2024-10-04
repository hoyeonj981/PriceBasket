package me.hoyeonj.pricebasket.adapter.out;

import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.TimeoutError;
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
      page.setDefaultTimeout(60000);

      int previousHeight = 0;
      int scrollCount = 0;
      int unchangedCount = 0;
      final int MAX_UNCHANGED = 3; // 연속으로 변화가 없는 최대 횟수
      int maxScrolls = 10;

      while (scrollCount < maxScrolls) {
        // 페이지 끝까지 스크롤
        page.evaluate("window.scrollTo(0, document.body.scrollHeight)");

        // 새로운 콘텐츠 로딩 대기
        try {
          page.waitForFunction("() => document.body.scrollHeight > " + previousHeight);
        } catch (TimeoutError e) {
          System.out.println("새로운 콘텐츠 로딩 시간 초과");
        }

        // 새로운 높이 확인
        int newHeight = (int) page.evaluate("document.body.scrollHeight");

        if (newHeight > previousHeight) {
          // 새로운 콘텐츠가 로드됨
          previousHeight = newHeight;
          unchangedCount = 0;
          System.out.println("새로운 콘텐츠 로드됨. 현재 높이: " + newHeight);
        } else {
          // 높이 변화 없음
          unchangedCount++;
          System.out.println("높이 변화 없음. 연속 " + unchangedCount + "회");

          if (unchangedCount >= MAX_UNCHANGED) {
            System.out.println("더 이상 새로운 콘텐츠가 없는 것 같습니다. 스크롤 종료.");
            break;
          }
        }

        scrollCount++;
      }
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

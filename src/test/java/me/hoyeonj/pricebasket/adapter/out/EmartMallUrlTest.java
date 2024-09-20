package me.hoyeonj.pricebasket.adapter.out;

import static me.hoyeonj.pricebasket.adapter.out.EmartMallUrl.*;
import static org.assertj.core.api.Assertions.*;

import java.net.MalformedURLException;
import java.net.URL;
import org.junit.jupiter.api.Test;

class EmartMallUrlTest {

  @Test
  void 유효한_HTTPS_URL을_생성한다() {
    var testKeyword = "testKeyword";
    var searchUrl = HTTPS_SEARCH_URL.createSearchUrl(testKeyword);

    var actual = isValidUrl(searchUrl);

    assertThat(actual).isTrue();
  }

  private boolean isValidUrl(String urlString) {
    try {
      new URL(urlString);
      return true;
    } catch (MalformedURLException e) {
      return false;
    }
  }
}

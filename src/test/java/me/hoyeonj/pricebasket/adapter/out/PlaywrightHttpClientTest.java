package me.hoyeonj.pricebasket.adapter.out;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PlaywrightHttpClientTest {

  private PlaywrightHttpClient playwrightHttpClient;

  @DisplayName("HTTP 요청을 보낼 수 없다면 예외가 발생한다")
  @Test
  void throwExceptionWhenCanNotSendRequest() {
    playwrightHttpClient = mock(PlaywrightHttpClient.class);
    var givenKeyword = "keyword";
    when(playwrightHttpClient.fetchFromUri(any())).thenThrow(new HttpRequestException("IO Error"));

    assertThatThrownBy(() -> playwrightHttpClient.fetchFromUri(givenKeyword))
        .isInstanceOf(HttpRequestException.class);
  }
}

package me.hoyeonj.pricebasket.adapter.out;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ApacheHttpClientTest {

  private ApacheHttpClient apacheClient;

  @DisplayName("HTTP 요청을 보낼 수 없다면 예외가 발생한다")
  @Test
  void throw_exception_when_can_not_send_request() {
    apacheClient = mock(ApacheHttpClient.class);
    var givenKeyword = "keyword";
    when(apacheClient.fetchFromUri(any())).thenThrow(new HttpRequestException("IO Error"));;

    assertThatThrownBy(() -> apacheClient.fetchFromUri(givenKeyword))
        .isInstanceOf(HttpRequestException.class);
  }
}
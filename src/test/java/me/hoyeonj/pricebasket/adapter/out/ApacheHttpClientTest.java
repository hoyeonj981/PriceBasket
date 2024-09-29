package me.hoyeonj.pricebasket.adapter.out;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

class ApacheHttpClientTest {

  private ApacheHttpClient apacheClient;

  @Test
  void HTTP_요청을_보낼_수_없다면_예외가_발생한다() {
    apacheClient = mock(ApacheHttpClient.class);
    var givenKeyword = "keyword";
    when(apacheClient.fetchFromUri(any())).thenThrow(new HttpRequestException("IO Error"));;

    assertThatThrownBy(() -> apacheClient.fetchFromUri(givenKeyword))
        .isInstanceOf(HttpRequestException.class);
  }
}
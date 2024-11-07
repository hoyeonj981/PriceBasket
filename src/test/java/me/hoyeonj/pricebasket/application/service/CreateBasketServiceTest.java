package me.hoyeonj.pricebasket.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.Duration;
import me.hoyeonj.pricebasket.application.in.dto.CreateBasketCommand;
import me.hoyeonj.pricebasket.application.out.BasketCommandPort;
import me.hoyeonj.pricebasket.application.out.ClientQueryPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreateBasketServiceTest {

  private static final String VALID_CLIENT_ID = "client-1";

  @Mock
  private BasketCommandPort basketCommandPort;

  @Mock
  private ClientQueryPort clientQueryPort;

  @InjectMocks
  private CreateBasketService createBasketService;

  @DisplayName("사용자 ID가 유효하다면 새로운 Basket을 생성한다")
  @Test
  void createBasketIfClientIdIsValid() {
    var command = new CreateBasketCommand(VALID_CLIENT_ID, "emart", Duration.ofMillis(1));
    when(clientQueryPort.existByClientId(any())).thenReturn(true);

    var actual = createBasketService.createBasket(command);

    assertThat(actual.clientId()).isEqualTo(VALID_CLIENT_ID);
    assertThat(actual.basketId()).isNotNull();
    assertThat(actual.createdAt()).isNotNull();
  }
}
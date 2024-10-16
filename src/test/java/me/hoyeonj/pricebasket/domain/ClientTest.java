package me.hoyeonj.pricebasket.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ClientTest {

  private static final String VALID_EMAIL = "test@example.com";
  private static final String VALID_PASSWORD = "validPassword123!";
  private static final String ENCODED_PASSWORD = "encodedPassword";
  private static final String VALID_ID = "123e4567-e89b-12d3-a456-426614174000";

  @Mock
  private PasswordEncoder passwordEncoder;

  @Mock
  private PasswordValidator passwordValidator;

  @BeforeEach
  void setUp() {
    doNothing().when(passwordValidator).validate(anyString());
    when(passwordEncoder.encode(anyString())).thenReturn(ENCODED_PASSWORD);
  }

  @DisplayName("등록은 위한 Client를 생성한다")
  @Test
  void createClientForRegister() {
    var guestClient = Client.create(
        VALID_EMAIL, VALID_PASSWORD, passwordEncoder, passwordValidator);

    assertThat(guestClient).isNotNull();
    assertThat(guestClient.getClientType()).isEqualTo(ClientType.REGISTERD);
    assertThat(guestClient.getEmail()).isEqualTo(VALID_EMAIL);
    verify(passwordValidator).validate(VALID_PASSWORD);
    verify(passwordEncoder).encode(VALID_PASSWORD);
  }

  @DisplayName("등록된 사용자를 생성한다")
  @Test
  void createRegisteredClient() {
    var guestClient = Client.create(
        VALID_ID, VALID_EMAIL, VALID_PASSWORD, passwordEncoder, passwordValidator);

    assertThat(guestClient).isNotNull();
    assertThat(guestClient.getClientType()).isEqualTo(ClientType.REGISTERD);
    assertThat(guestClient.getEmail()).isEqualTo(VALID_EMAIL);
    verify(passwordValidator).validate(VALID_PASSWORD);
    verify(passwordEncoder).encode(VALID_PASSWORD);
  }
}
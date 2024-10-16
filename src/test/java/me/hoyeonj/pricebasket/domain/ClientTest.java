package me.hoyeonj.pricebasket.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
  private static final String GUEST = "guest";
  private static final String TEMPORARY_PASSWORD = "";

  @Mock
  private PasswordEncoder passwordEncoder;

  @Mock
  private PasswordValidator passwordValidator;

  @DisplayName("등록은 위한 Client를 생성한다")
  @Test
  void createClientForRegister() {
    doNothing().when(passwordValidator).validate(anyString());
    when(passwordEncoder.encode(anyString())).thenReturn(ENCODED_PASSWORD);

    var client = Client.create(
        VALID_EMAIL, VALID_PASSWORD, passwordEncoder, passwordValidator);

    assertThat(client).isNotNull();
    assertThat(client.getClientType()).isEqualTo(ClientType.REGISTERD);
    assertThat(client.getEmail()).isEqualTo(VALID_EMAIL);
    verify(passwordValidator).validate(VALID_PASSWORD);
    verify(passwordEncoder).encode(VALID_PASSWORD);
  }

  @DisplayName("등록된 client를 생성한다")
  @Test
  void createRegisteredClient() {
    doNothing().when(passwordValidator).validate(anyString());
    when(passwordEncoder.encode(anyString())).thenReturn(ENCODED_PASSWORD);

    var client = Client.create(
        VALID_ID, VALID_EMAIL, VALID_PASSWORD, passwordEncoder, passwordValidator);

    assertThat(client).isNotNull();
    assertThat(client.getClientType()).isEqualTo(ClientType.REGISTERD);
    assertThat(client.getEmail()).isEqualTo(VALID_EMAIL);
    verify(passwordValidator).validate(VALID_PASSWORD);
    verify(passwordEncoder).encode(VALID_PASSWORD);
  }

  @DisplayName("Guest client를 생성한다")
  @Test
  void createGuestClient() {
    var guest = Client.createGuest();

    assertThat(guest).isNotNull();
    assertThat(guest.getClientType()).isEqualTo(ClientType.GUEST);
    assertThat(guest.getEmail()).contains(GUEST);
    assertThat(guest.getClientId()).contains(GUEST);
    assertThat(guest.getPassword()).contains(TEMPORARY_PASSWORD);
  }

  @DisplayName("id값이 같다면 같은 client 객체이다")
  @Test
  void sameObjectWhenClientIdIsSame() {
    var id1 = VALID_ID;
    var id2 = VALID_ID;

    var client1 = Client.create(
        id1, VALID_EMAIL, VALID_PASSWORD, passwordEncoder, passwordValidator);
    var client2 = Client.create(
        id2, VALID_EMAIL, VALID_PASSWORD, passwordEncoder, passwordValidator);

    assertThat(client1).isEqualTo(client2);
  }

  @DisplayName("id값이 같다면 hashcode 값이 같다")
  @Test
  void sameHashCodeWhenClientIdIsSame() {
    var id1 = VALID_ID;
    var id2 = VALID_ID;

    var client1 = Client.create(
        id1, VALID_EMAIL, VALID_PASSWORD, passwordEncoder, passwordValidator);
    var client2 = Client.create(
        id2, VALID_EMAIL, VALID_PASSWORD, passwordEncoder, passwordValidator);

    assertThat(client1).isEqualTo(client2);
  }
}
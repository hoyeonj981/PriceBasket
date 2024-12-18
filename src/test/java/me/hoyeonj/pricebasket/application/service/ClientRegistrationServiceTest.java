package me.hoyeonj.pricebasket.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import me.hoyeonj.pricebasket.application.in.dto.RegistrationCommand;
import me.hoyeonj.pricebasket.application.in.dto.RegistrationResult;
import me.hoyeonj.pricebasket.application.out.ClientCommandPort;
import me.hoyeonj.pricebasket.application.out.ClientQueryPort;
import me.hoyeonj.pricebasket.domain.Client;
import me.hoyeonj.pricebasket.domain.ClientEmail;
import me.hoyeonj.pricebasket.domain.InvalidEmailException;
import me.hoyeonj.pricebasket.domain.PasswordEncoder;
import me.hoyeonj.pricebasket.domain.service.PasswordValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ClientRegistrationServiceTest {

  @Mock
  private ClientCommandPort clientCommandPort;

  @Mock
  private ClientQueryPort clientQueryPort;

  @Mock
  private PasswordEncoder encoder;

  @Mock
  private PasswordValidator validator;

  @InjectMocks
  private ClientRegistrationService registrationService;

  @DisplayName("중복된 이메일이 있는 경우 예외가 발생한다")
  @Test
  void throwExceptionIfEmailIsDuplicated() {
    var givenMail = "tester@test.com";
    var givenPassword = "helloworld123!";
    var command = new RegistrationCommand(givenMail, givenPassword);
    var clientEmail = ClientEmail.from(givenMail);
    when(clientQueryPort.existsByEmail(clientEmail)).thenReturn(true);

    assertThatThrownBy(() -> registrationService.register(command))
        .isInstanceOf(DuplicateEmailException.class);
  }

  @DisplayName("유효한 정보로 사용자를 생성한다")
  @Test
  void createClientWithValidInformation() {
    var givenMail = "tester@test.com";
    var givenPassword = "helloworld123!";
    var givenClientId = "testId";
    var command = new RegistrationCommand(givenMail, givenPassword);
    var newClient = mock(Client.class);
    var clientEmail = ClientEmail.from(givenMail);
    when(newClient.getClientId()).thenReturn(givenClientId);
    when(newClient.getEmail()).thenReturn(givenMail);
    when(clientQueryPort.existsByEmail(clientEmail)).thenReturn(false);
    when(clientCommandPort.save(any())).thenReturn(newClient);
    var expected = new RegistrationResult(givenClientId, givenMail);

    var actual = registrationService.register(command);

    assertThat(actual).isEqualTo(expected);
  }

  @DisplayName("올바르지 않는 이메일 형식은 도메인 예외가 발생한다")
  @Test
  void throwDomainExceptionIfEmailFormatIsInvalid() {
    var givenMail = "adfasdfsdafsdfssdfsdavxczvEmail";
    var givenPassword = "helloworld123!";
    var command = new RegistrationCommand(givenMail, givenPassword);

    assertThatThrownBy(() -> registrationService.register(command))
        .isInstanceOf(InvalidEmailException.class);
  }
}

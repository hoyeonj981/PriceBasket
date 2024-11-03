package me.hoyeonj.pricebasket.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import me.hoyeonj.pricebasket.application.in.dto.RegistrationCommand;
import me.hoyeonj.pricebasket.application.in.dto.RegistrationResult;
import me.hoyeonj.pricebasket.application.out.ClientRepository;
import me.hoyeonj.pricebasket.domain.Client;
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
  private ClientRepository repository;

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
    when(repository.existsByEmail(givenMail)).thenReturn(true);

    assertThatThrownBy(() -> registrationService.register(command))
        .isInstanceOf(DuplicateEmailException.class);
  }

  @DisplayName("유요한 정보로 사용자를 생성한다")
  @Test
  void createClientWithValidInformation() {
    var givenMail = "tester@test.com";
    var givenPassword = "helloworld123!";
    var givenClientId = "testId";
    var command = new RegistrationCommand(givenMail, givenPassword);
    var newClient = mock(Client.class);
    when(newClient.getClientId()).thenReturn(givenClientId);
    when(newClient.getEmail()).thenReturn(givenMail);
    when(repository.existsByEmail(givenMail)).thenReturn(false);
    when(repository.save(any())).thenReturn(newClient);
    var expected = new RegistrationResult(givenClientId, givenMail);

    var actual = registrationService.register(command);

    assertThat(actual).isEqualTo(expected);
  }
}

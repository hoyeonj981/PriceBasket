package me.hoyeonj.pricebasket.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import me.hoyeonj.pricebasket.domain.service.PasswordValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ClientPasswordTest {

  @Mock
  private PasswordEncoder encoder;

  @Mock
  private PasswordValidator validator;

  @DisplayName("비밀번호를 생성한다")
  @Test
  void createPasswordObject() {
    var givenPassword = "validPassword";
    var hashedPassword = "hashedPassword";
    when(encoder.encode(anyString())).thenReturn(hashedPassword);
    doNothing().when(validator).validate(givenPassword);

    var actual = ClientPassword.create(givenPassword, encoder, validator);

    assertThat(actual.getPassword()).isEqualTo(hashedPassword);
  }

  @DisplayName("임시 비밀번호를 생성한다")
  @Test
  void createTemporaryPassword() {
    var actual = ClientPassword.createTemporary();

    assertThat(actual.getPassword()).contains("");
  }

  @DisplayName("비밀번호 값이 같다면 같은 객체이다")
  @Test
  void sameObjectWhenPasswordValueIsSame() {
    var givenPassword1 = "validPassword1";
    var givenPassword2 = "validPassword2";
    var hashedPassword = "hashedPassword";
    when(encoder.encode(anyString())).thenReturn(hashedPassword);
    doNothing().when(validator).validate(givenPassword1);

    var password1 = ClientPassword.create(givenPassword1, encoder, validator);
    var password2 = ClientPassword.create(givenPassword2, encoder, validator);

    assertThat(password1).isEqualTo(password2);
  }

  @DisplayName("비밀번호가 값이 같다면 hashcode 값도 같다")
  @Test
  void sameHashCodeWhenPasswordValueIsSame() {
    var givenPassword1 = "validPassword1";
    var givenPassword2 = "validPassword2";
    var hashedPassword = "hashedPassword";
    when(encoder.encode(anyString())).thenReturn(hashedPassword);
    doNothing().when(validator).validate(givenPassword1);

    var password1 = ClientPassword.create(givenPassword1, encoder, validator);
    var password2 = ClientPassword.create(givenPassword2, encoder, validator);

    assertThat(password1.hashCode()).isEqualTo(password2.hashCode());
  }
}

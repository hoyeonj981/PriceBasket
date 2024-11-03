package me.hoyeonj.pricebasket.domain.rules;

import java.util.regex.Pattern;
import me.hoyeonj.pricebasket.domain.InvalidPasswordException;
import me.hoyeonj.pricebasket.domain.PasswordRule;

public class DefaultPasswordComposition implements PasswordRule {

  private static final String ONLY_ALPHABET = "[a-zA-Z]";
  private static final String DIGIT = "\\d";
  private static final String SPECIAL_CHAR = "[!@#$%^&*(),.?\":{}|<>]";

  private static final Pattern LETTER_PATTERN = Pattern.compile(ONLY_ALPHABET);
  private static final Pattern DIGIT_PATTERN = Pattern.compile(DIGIT);
  private static final Pattern SPECIAL_CHAR_PATTERN = Pattern.compile(SPECIAL_CHAR);

  @Override
  public void validate(final String rawPassword) throws InvalidPasswordException {
    if (!LETTER_PATTERN.matcher(rawPassword).find() ||
        !DIGIT_PATTERN.matcher(rawPassword).find() ||
        !SPECIAL_CHAR_PATTERN.matcher(rawPassword).find()
    ) {
      throw new InvalidPasswordException("비밀번호는 영문, 숫자, 특수문자 조합이어야 합니다");
    }
  }
}

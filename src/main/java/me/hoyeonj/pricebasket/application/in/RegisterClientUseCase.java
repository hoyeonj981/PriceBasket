package me.hoyeonj.pricebasket.application.in;

import me.hoyeonj.pricebasket.application.in.dto.RegistrationCommand;
import me.hoyeonj.pricebasket.application.in.dto.RegistrationResult;

public interface RegisterClientUseCase {

  RegistrationResult register(RegistrationCommand command);
}

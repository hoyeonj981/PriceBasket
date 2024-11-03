package me.hoyeonj.pricebasket.application.in.dto;

import java.time.LocalDateTime;

public record RegistrationResult(
    String clientId,
    String email
) { }

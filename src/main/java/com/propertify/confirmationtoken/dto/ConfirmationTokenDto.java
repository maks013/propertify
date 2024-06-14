package com.propertify.confirmationtoken.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ConfirmationTokenDto(Long id,
                                   String token,
                                   LocalDateTime createdAt,
                                   LocalDateTime expiresAt,
                                   LocalDateTime confirmedAt,
                                   Long userId
) {
}

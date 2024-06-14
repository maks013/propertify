package com.propertify.confirmationtoken.domain;

import com.propertify.confirmationtoken.dto.ConfirmationTokenDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
class ConfirmationToken {

    private Long id;
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private LocalDateTime confirmedAt;
    private Long userId;


    ConfirmationTokenDto toDto() {
        return ConfirmationTokenDto.builder()
                .id(id)
                .token(token)
                .createdAt(createdAt)
                .expiresAt(expiresAt)
                .confirmedAt(confirmedAt)
                .userId(userId)
                .build();
    }
}

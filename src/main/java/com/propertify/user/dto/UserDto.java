package com.propertify.user.dto;

import lombok.Builder;

@Builder
public record UserDto(Long id,
                      String email,
                      String password,
                      boolean enabled
) {
}

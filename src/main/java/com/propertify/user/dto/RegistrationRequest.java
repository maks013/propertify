package com.propertify.user.dto;

import lombok.Builder;

@Builder
public record RegistrationRequest(String email,
                                  String password
) {
}

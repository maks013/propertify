package com.propertify.user.domain;

import com.propertify.user.dto.UserDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
class User {

    private Long id;
    private String email;
    private String password;
    private Boolean enabled;

    static Boolean enabledForNewUser() {
        return false;
    }

    void enableUser() {
        this.enabled = true;
    }

    UserDto toDto() {
        return UserDto.builder()
                .id(id)
                .email(email)
                .password(password)
                .enabled(enabled)
                .build();
    }
}

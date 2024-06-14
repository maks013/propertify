package com.propertify.confirmationtoken.domain;

import java.time.LocalDateTime;

class DateTimeProvider {
    LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }
}

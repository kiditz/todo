package com.savantdegrees.todo.common

import org.springframework.data.domain.AuditorAware

class AuditorAwareImpl implements AuditorAware<String> {


    @Override
    Optional<String> getCurrentAuditor() {
        return Optional.of("Admin")
    }
}
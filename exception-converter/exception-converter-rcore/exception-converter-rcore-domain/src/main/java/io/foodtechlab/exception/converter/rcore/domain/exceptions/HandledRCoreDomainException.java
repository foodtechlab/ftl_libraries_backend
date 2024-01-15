package io.foodtechlab.exception.converter.rcore.domain.exceptions;

import com.rcore.domain.commons.exception.BadRequestDomainException;
import com.rcore.domain.commons.exception.DomainException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Пойманный RCORE эксепшн у интеграции
 * <p>
 * Если не обрабатывать, пойдёт дальше, и по итогу клиент получит то, что выкинула интеграция
 */
@Getter
public class HandledRCoreDomainException extends RuntimeException {
    private final List<RCoreError> errors;

    public HandledRCoreDomainException(List<RCoreError> errors) {
        super("Unhandled integration rcore exceptions: " + errors
                .stream()
                .map(RCoreError::toString)
                .collect(Collectors.joining("\n")));
        this.errors = errors;
    }

    public DomainException toDomainException() {
        return new BadRequestDomainException(errors.stream()
                .map(RCoreError::toRCoreError)
                .collect(Collectors.toList())
        );
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static final class RCoreError {
        private final String domain;
        private final String reason;
        private final String details;

        private final PresentationData presentationData;

        public DomainException.Error toRCoreError() {
            return new DomainException.Error(domain, reason, details);
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @ToString
    public static final class PresentationData {
        private final String title;
        private final String message;
    }
}

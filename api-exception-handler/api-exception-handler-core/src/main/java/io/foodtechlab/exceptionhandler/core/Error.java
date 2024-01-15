 package io.foodtechlab.exceptionhandler.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class Error {
    private PresentationData presentationData;
    private String domain;
    private String reason;
    private String details;

    public Error(String title, String message, String domain, String reason, String details) {
        this.presentationData = PresentationData.of(title, message);
        this.domain = domain.toUpperCase();
        this.reason = reason.toUpperCase();
        this.details = details;
    }

    public static Error of(String title, String message, String domain, String reason, String detail) {
        return new Error(title, message, domain, reason, detail);
    }

    public static Error unknownError(String title, String message, Throwable e) {
        return new Error(title, message, "SERVER", "UNKNOWN", e.getClass().getSimpleName() + ": " + e.getMessage());
    }

    @Data
    @AllArgsConstructor(staticName = "of")
    @NoArgsConstructor
    public static class PresentationData {
        private String title;
        private String message;
    }
}

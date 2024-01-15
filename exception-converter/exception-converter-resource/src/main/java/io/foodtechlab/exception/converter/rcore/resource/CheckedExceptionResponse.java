package io.foodtechlab.exception.converter.rcore.resource;


import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public final class CheckedExceptionResponse {
    private String domain;
    private String reason;
    private String details;
    private Map<String, Object> data;
}

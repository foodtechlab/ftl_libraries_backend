package io.foodtechlab.mongo.documents;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class ExternalLinkDocument {
    /**
     * Идентификатор сущности во внешней системе
     */
    private String id;

    /**
     * Тип внешней системы
     */
    private String type;

    /**
     * Идентификатор аккаунта во внешней системе
     */
    private String externalSystemAccountId;

    /**
     * Дата последней синхронизации
     */
    private Instant lastSyncDate;
}

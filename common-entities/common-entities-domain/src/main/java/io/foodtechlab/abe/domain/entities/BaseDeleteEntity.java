package io.foodtechlab.domain.entities;

import com.rcore.domain.commons.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

/**
 * базовая сущность с флагом удалено/не_удалено
 *
 * @param <Id> - тип идентификатора сущности
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public abstract class BaseDeleteEntity<Id> extends BaseEntity<Id> implements DeleteProperty {
    /**
     * флаг удалено/не_удалено, по умолчанию false
     */
    protected boolean deleted = false;
    protected Instant deletedAt;
}

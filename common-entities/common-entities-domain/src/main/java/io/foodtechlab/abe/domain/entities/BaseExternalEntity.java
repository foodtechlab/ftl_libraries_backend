package io.foodtechlab.domain.entities;

import com.rcore.domain.commons.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * базовая сущность содержащая id-s для внешней привязки
 *
 * @param <Id> - тип идентификатора сущности
 */
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseExternalEntity<Id> extends BaseEntity<Id> implements ExternalProperty {
    /**
     * ссылки на внешние системы
     */
    protected List<ExternalLink> externalLinks = new ArrayList<>();
}

package io.foodtechlab.common.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * базовая сущность содержащая id-s для внешней
 * привязки и флаг удалено/не_удалено
 *
 * @param <Id> - тип идентификатора сущности
 */
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseExternalDeleteEntity<Id> extends BaseDeleteEntity<Id> implements DeleteProperty, ExternalProperty {
    /**
     * ссылки на внешние системы
     */
    protected List<ExternalLink> externalLinks = new ArrayList<>();

    /**
     * Добавляем External Link проверяя, что такой ссылки нет.
     * <p>
     * Основная проверка идет по полю id.
     * <p>
     * Далее идет проверка аккаунта "ExternalSystemAccountId".
     * Мы считаем если ExternalSystemAccountId == null во всех вариантах, то сверить нужно только id
     * В случае если хотя бы один ExternalSystemAccountId не null, то сверка двух сущностей
     * External должна идти по двум полям и id и External Link
     *
     * @param newLink
     */
    public void addExternalLink(ExternalLink newLink) {
        if (externalLinks == null) externalLinks = new ArrayList<>();

        Boolean hasExternal = false;
        for (ExternalLink el : externalLinks) {
            if (el.equals(newLink)) {
                // NOTE: обновляем данные если пришли не пустые значения

                el.setName(newLink.getName() != null ? newLink.getName() : el.getName());
                el.setType(newLink.getType() != null ? newLink.getType() : el.getType());
                el.setExternalSystemAccountId(newLink.getExternalSystemAccountId() != null ?
                        newLink.getExternalSystemAccountId() :
                        el.getExternalSystemAccountId());
                el.setLastSyncDate(Instant.now());

                hasExternal = true;
            }
        }

        if (hasExternal == false) {
            if (newLink.getLastSyncDate() == null) {
                newLink.setLastSyncDate(Instant.now());
            }

            // FIX: java.lang.UnsupportedOperationException
            if (externalLinks.size() == 0) {
                externalLinks = new ArrayList<>();
            }
            externalLinks.add(newLink);
        }

        List<ExternalLink> unicLinks = new ArrayList<>();
        // Появляются дубли. что бы этого не было проверяем
        for (ExternalLink externalLink : externalLinks) {

            Boolean needAdd = true;
            try {
                for (ExternalLink unic : unicLinks) {
                    if (unic.getId().equals(externalLink.getId())
                            && unic.getExternalSystemAccountId().equals(externalLink.getExternalSystemAccountId())
                            && unic.getType().equals(externalLink.getType())) {
                        needAdd = false;
                    }
                }
            } catch (Exception igonre) {
                // Что бы не проверять на NPE
            }

            if (needAdd == true) {
                unicLinks.add(externalLink);
            }

        }

        externalLinks = unicLinks;
    }

    public void addExternalLink(List<ExternalLink> links) {
        for (ExternalLink externalLink : links) addExternalLink(externalLink);
    }

    public Optional<ExternalLink> getExternalLinkByType(String externalType) {
        if (externalLinks == null) return Optional.empty();

        for (ExternalLink externalLink : externalLinks) {
            if (externalLink.getType().equals(externalType)) return Optional.of(externalLink);
        }

        return Optional.empty();
    }
}

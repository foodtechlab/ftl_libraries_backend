package io.foodtechlab.common.domain.entities;

import lombok.*;

import java.time.Instant;
import java.util.Objects;

/**
 * Структура содержащая данные привязки внешнего id
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExternalLink {

    /**
     * Идентификатор сущности во внешней системе
     */
    private String id;

    /**
     * Название
     */
    private String name;

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
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ExternalLink el = (ExternalLink) obj;

        if (el.getId().equals(this.getId())) {

            // Считаем, что если тайп не заполнен, то мы просто сравниваем ID
            if (this.getType() == null && el.getType() == null) {
                return true;
            }

            // Если тайпы равны, то идем дальше в сравнение
            if (this.getType() != null && el.getType() != null && this.getType().equals(el.getType())) {

                // Считаем, что если один из аккаунтов пустой, то это тот же самый
                // Вообще лучше не держать пустыми аккаунты
                if (this.getExternalSystemAccountId() == null || el.getExternalSystemAccountId() == null) {
                    return true;
                }

                if (this.getExternalSystemAccountId() != null && el.getExternalSystemAccountId() != null) {
                    if (el.getExternalSystemAccountId().equals(this.getExternalSystemAccountId())) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public int hashCode() {
        if (externalSystemAccountId != null) {
            return Objects.hash(id, externalSystemAccountId);
        } else {
            return Objects.hash(id);
        }
    }

    public boolean compareType(Class clazz) {
        if (type == null) return false;

        return clazz.getName().equals(this.type);
    }
}

package io.foodtechlab.common.mongo.util;

import lombok.experimental.UtilityClass;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.Optional;

@UtilityClass
public class IsDeletedCriteria {
    public Criteria getDeletedCriteria() {
        return Criteria.where("isDeleted").is(true);
    }

    public Criteria getNotDeletedCriteria() {
        return Criteria.where("isDeleted").ne(true);
    }

    /**
     * Генератор критерии из фильтра, чтобы не прописывать эти строчки во всех фильтрах каждый раз и использовать такой вид
     *
     * <pre>
     * {@code
     * filterCriteria(filters.getIsDeleted()).ifPresent(c - > criteriaSet.add (c))
     * }
     * </pre>
     */
    public Optional<Criteria> filterCriteria(Boolean isDeleted) {
        if (isDeleted == null)
            return Optional.empty();
        else if (isDeleted)
            return Optional.of(getDeletedCriteria());
        else
            return Optional.of(getNotDeletedCriteria());
    }
}

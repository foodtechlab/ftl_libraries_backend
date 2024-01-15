package io.foodtechlab.mongo.util;

import lombok.experimental.UtilityClass;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.Optional;

@UtilityClass
public class DeletedCriteria {
    public Criteria getDeletedCriteria() {
        return Criteria.where("deleted").is(true);
    }

    public Criteria getNotDeletedCriteria() {
        return Criteria.where("deleted").ne(true);
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
    public Optional<Criteria> filterCriteria(Boolean deleted) {
        if (deleted == null)
            return Optional.empty();
        else if (deleted)
            return Optional.of(getDeletedCriteria());
        else
            return Optional.of(getNotDeletedCriteria());
    }
}

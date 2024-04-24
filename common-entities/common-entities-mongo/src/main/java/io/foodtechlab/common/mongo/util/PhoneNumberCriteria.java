package io.foodtechlab.common.mongo.util;

import com.rcore.commons.utils.StringUtils;
import com.rcore.database.mongo.commons.utils.MongoQueryUtils;
import io.foodtechlab.common.core.entities.PhoneNumber;
import lombok.experimental.UtilityClass;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.Optional;

@UtilityClass
public class PhoneNumberCriteria {
    public Criteria getCriteria(String query, String field) {
        return MongoQueryUtils.generateQueryRegEXCriteria(query, field + ".value");
    }

    public Optional<Criteria> filterCriteria(String query, String field) {
        if (StringUtils.hasText(query)) {
            query = PhoneNumber.formatFullRuNumber(query);
            if (StringUtils.hasText(query))
                return Optional.of(getCriteria(query, field));
        }
        return Optional.empty();
    }
}
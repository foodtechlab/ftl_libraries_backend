package io.foodtechlab.mongo.util;

import com.rcore.commons.utils.StringUtils;
import com.rcore.database.mongo.commons.utils.MongoQueryUtils;
import lombok.experimental.UtilityClass;
import org.springframework.data.mongodb.core.query.Criteria;
import io.foodtechlab.core.utils.PhoneNumberUtils;

import java.util.Optional;

@UtilityClass
public class PhoneNumberCriteria {
    public Criteria getCriteria(String query, String field) {
        return MongoQueryUtils.generateQueryRegEXCriteria(query, field + ".phoneString");
    }

    public Optional<Criteria> filterCriteria(String query, String field) {
        if (StringUtils.hasText(query)) {
            query = PhoneNumberUtils.removeSymbols(query);
            if (StringUtils.hasText(query))
                return Optional.of(getCriteria(query, field));
        }

        return Optional.empty();
    }
}

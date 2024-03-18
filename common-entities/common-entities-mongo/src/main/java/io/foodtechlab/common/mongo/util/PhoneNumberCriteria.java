package io.foodtechlab.common.mongo.util;

import com.rcore.commons.utils.StringUtils;
import com.rcore.database.mongo.commons.utils.MongoQueryUtils;
import io.foodtechlab.common.core.utils.PhoneNumberParser;
import io.foodtechlab.common.core.utils.PhoneNumberUtils;
import lombok.experimental.UtilityClass;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.Optional;

@UtilityClass
public class PhoneNumberCriteria {
    public Criteria getCriteria(String query, String field) {
        return MongoQueryUtils.generateQueryRegEXCriteria(query, field + ".phoneString");
    }

    public Optional<Criteria> filterCriteria(String query, String field) {
        if (StringUtils.hasText(query)) {
            query = PhoneNumberParser.parsePhoneNumber(query, null).getValue();
            if (StringUtils.hasText(query))
                return Optional.of(getCriteria(query, field));
        }
        return Optional.empty();
    }
}
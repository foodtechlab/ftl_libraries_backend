package io.foodtechlab.mongo.util;

import com.rcore.commons.utils.StringUtils;
import com.rcore.database.mongo.commons.utils.MongoQueryUtils;
import lombok.experimental.UtilityClass;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.Arrays;

@UtilityClass
public class FullNameCriteria {
    public Criteria get(String field, String query) {
        if (!StringUtils.hasText(query))
            return new Criteria();

        return new Criteria().orOperator(Arrays.stream(query.split(" "))
                .map(MongoQueryUtils::shieldNotSupportedSymbols)
                .map(word -> Criteria.where(field + ".fullName").regex(word, "i")).toArray(Criteria[]::new));
    }
}

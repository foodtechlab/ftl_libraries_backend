package io.foodtechlab.common.mongo.util;

import com.rcore.commons.utils.StringUtils;
import lombok.experimental.UtilityClass;
import org.springframework.data.mongodb.core.query.Criteria;

@UtilityClass

public class FindByExternalLinkCriteria {
    public Criteria get(String field, String type, String id) {
        if (!StringUtils.hasText(type) || !StringUtils.hasText(id) || !StringUtils.hasText(field))
            return new Criteria();

        return Criteria.where(field).elemMatch(
                Criteria.where("type").is(type).and("_id").is(id)
        );
    }
}

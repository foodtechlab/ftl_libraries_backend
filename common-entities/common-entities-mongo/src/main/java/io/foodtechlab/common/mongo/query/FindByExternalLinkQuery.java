package io.foodtechlab.common.mongo.query;

import com.rcore.database.mongo.commons.query.ExampleQuery;
import io.foodtechlab.common.mongo.util.FindByExternalLinkCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.query.Criteria;

@RequiredArgsConstructor
public abstract class FindByExternalLinkQuery implements ExampleQuery {

    private final String id;
    private final String type;

    abstract protected String getField();

    @Override
    public Criteria getCriteria() {
        return FindByExternalLinkCriteria.get(getField(), type, id);
    }
}

package io.foodtechlab.mongo.query;

import com.rcore.database.mongo.commons.query.ExampleQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.query.Criteria;
import io.foodtechlab.mongo.util.FindByExternalLinkCriteria;

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

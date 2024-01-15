package io.foodtechlab.mongo.query;

import com.rcore.database.mongo.commons.query.ExampleQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.query.Criteria;
import io.foodtechlab.mongo.util.DeletedCriteria;

@RequiredArgsConstructor(staticName = "of")
public class FindNotDeleteByIdQuery<ID> implements ExampleQuery {
    private final ID id;

    @Override
    public Criteria getCriteria() {
        return new Criteria().andOperator(
                Criteria.where("_id").is(this.id),
                DeletedCriteria.getNotDeletedCriteria()
        );
    }
}

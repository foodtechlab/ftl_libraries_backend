package io.foodtechlab.mongo.port;

import com.rcore.commons.mapper.ExampleDataMapper;
import com.rcore.database.mongo.commons.port.impl.AbstractMongoRepository;
import com.rcore.database.mongo.commons.query.FindByIdQuery;
import org.springframework.data.mongodb.core.MongoTemplate;
import io.foodtechlab.domain.entities.BaseDeleteEntity;
import io.foodtechlab.domain.port.SafeDeleteCRUDRepository;
import io.foodtechlab.domain.port.filters.DeleteFilter;
import io.foodtechlab.mongo.documents.BaseDeleteDocument;
import io.foodtechlab.mongo.query.FindNotDeleteByIdQuery;
import io.foodtechlab.mongo.update.SetDeletedUpdate;

import java.util.Optional;

@SuppressWarnings("unused")
public abstract class AbstractMongoSafeDeleteRepository<ID, E extends BaseDeleteEntity<ID>, D extends BaseDeleteDocument, F extends DeleteFilter> extends AbstractMongoRepository<ID, E, D, F> implements SafeDeleteCRUDRepository<ID, E, F> {
    public AbstractMongoSafeDeleteRepository(Class<D> documentClass, ExampleDataMapper<E, D> mapper, MongoTemplate mongoTemplate) {
        super(documentClass, mapper, mongoTemplate);
    }

    @Override
    public Optional<E> findById(ID id) {
        return Optional.ofNullable(mongoTemplate.findOne(FindNotDeleteByIdQuery.of(id).getQuery(), documentClass))
                .map(mapper::inverseMap);
    }

    @Override
    public Optional<E> forceFindById(ID id) {
        return super.findById(id);
    }

    @Override
    public boolean restore(ID id) {
        return mongoTemplate
                .update(documentClass)
                .matching(FindByIdQuery.of(id).getQuery())
                .apply(SetDeletedUpdate.of(false).getUpdate())
                .first()
                .getModifiedCount() > 0;
    }

    @Override
    public Boolean delete(ID id) {
        return mongoTemplate
                .update(documentClass)
                .matching(FindNotDeleteByIdQuery.of(id).getQuery())
                .apply(SetDeletedUpdate.of(true).getUpdate())
                .first()
                .getModifiedCount() > 0;
    }

    @Override
    public boolean permanentDelete(ID id) {
        return super.delete(id);
    }
}

package io.foodtechlab.common.mongo.port;

import com.rcore.commons.mapper.ExampleDataMapper;
import com.rcore.database.mongo.commons.document.BaseDocument;
import com.rcore.domain.commons.entity.BaseEntity;
import io.foodtechlab.common.domain.entities.ExternalProperty;
import io.foodtechlab.common.domain.port.ExternalLinkRepository;
import io.foodtechlab.common.mongo.query.FindByExternalLinkQuery;
import org.springframework.core.ResolvableType;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Optional;

public abstract class AbstractMongoExternalLinkRepository
        <Entity extends BaseEntity<?> & ExternalProperty,
                Query extends FindByExternalLinkQuery,
                Document extends BaseDocument & ExternalProperty
                > implements ExternalLinkRepository<Entity> {

    protected final Class<Document> documentClass;
    protected final ExampleDataMapper<Entity, Document> mapper;
    protected final MongoTemplate mongoTemplate;

    public AbstractMongoExternalLinkRepository(ExampleDataMapper<Entity, Document> mapper, MongoTemplate mongoTemplate) {
        ResolvableType resolvableType = ResolvableType.forClass(AbstractMongoExternalLinkRepository.class, getClass());
        documentClass = (Class<Document>) resolvableType.getGeneric(2).getRawClass();
        this.mapper = mapper;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Optional<Entity> findByExternalLink(String id, String type) {
        return Optional.ofNullable(mongoTemplate.findOne(getQuery(id, type).getQuery(), documentClass)).map(mapper::inverseMap);
    }

    protected abstract Query getQuery(String id, String type);
}

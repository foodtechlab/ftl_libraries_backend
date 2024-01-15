package io.foodtechlab.mongo.port;

import com.rcore.commons.mapper.ExampleDataMapper;
import com.rcore.database.mongo.commons.document.BaseDocument;
import com.rcore.domain.commons.entity.BaseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ResolvableType;
import org.springframework.data.mongodb.core.MongoTemplate;
import io.foodtechlab.domain.entities.ExternalProperty;
import io.foodtechlab.domain.port.ExternalLinkRepository;
import io.foodtechlab.mongo.query.FindByExternalLinkQuery;

import java.util.Optional;

public abstract class AbstractMongoExternalLinkRepository
        <Entity extends BaseEntity<?> & ExternalProperty,
                Query extends FindByExternalLinkQuery,
                Document extends BaseDocument & ExternalProperty
                > implements ExternalLinkRepository<Entity> {

    public AbstractMongoExternalLinkRepository(ExampleDataMapper<Entity, Document> mapper, MongoTemplate mongoTemplate) {
        ResolvableType resolvableType = ResolvableType.forClass(AbstractMongoExternalLinkRepository.class, getClass());
        documentClass = (Class<Document>) resolvableType.getGeneric(2).getRawClass();
        this.mapper = mapper;
        this.mongoTemplate = mongoTemplate;
    }

    protected final Class<Document> documentClass;
    protected final ExampleDataMapper<Entity, Document> mapper;
    protected final MongoTemplate mongoTemplate;

    @Override
    public Optional<Entity> findByExternalLink(String id, String type) {
        return Optional.ofNullable(mongoTemplate.findOne(getQuery(id, type).getQuery(), documentClass)).map(mapper::inverseMap);
    }

    protected abstract Query getQuery(String id, String type);
}

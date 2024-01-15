package io.foodtechlab.domain.port;

import com.rcore.domain.commons.entity.BaseEntity;
import io.foodtechlab.domain.entities.ExternalProperty;

import java.util.Optional;

public interface ExternalLinkRepository<Entity extends BaseEntity<?> & ExternalProperty> {
    Optional<Entity> findByExternalLink(String id, String type);
}

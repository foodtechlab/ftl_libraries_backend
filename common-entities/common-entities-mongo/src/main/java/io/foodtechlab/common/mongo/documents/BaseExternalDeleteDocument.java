package io.foodtechlab.common.mongo.documents;

import io.foodtechlab.common.domain.entities.ExternalLink;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public abstract class BaseExternalDeleteDocument extends BaseDeleteDocument {
    /**
     * ссылки на внешние системы
     */
    @Builder.Default
    protected List<ExternalLink> externalLinks = Collections.emptyList();
}

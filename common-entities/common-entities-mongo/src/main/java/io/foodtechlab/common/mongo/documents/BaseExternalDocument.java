package io.foodtechlab.common.mongo.documents;

import com.rcore.database.mongo.commons.document.BaseDocument;
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
@NoArgsConstructor
@SuperBuilder
public abstract class BaseExternalDocument extends BaseDocument {
    /**
     * ссылки на внешние системы
     */
    @Builder.Default
    protected List<ExternalLink> externalLinks = Collections.emptyList();
}

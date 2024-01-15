package io.foodtechlab.mongo.documents;

import com.rcore.database.mongo.commons.document.BaseDocument;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.index.Indexed;
import io.foodtechlab.domain.entities.ExternalLink;

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

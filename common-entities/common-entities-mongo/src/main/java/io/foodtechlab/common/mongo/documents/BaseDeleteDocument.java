package io.foodtechlab.common.mongo.documents;

import com.rcore.database.mongo.commons.document.BaseDocument;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.Instant;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public abstract class BaseDeleteDocument extends BaseDocument {
    @Indexed
    @Builder.Default
    protected boolean deleted = false;
    protected Instant deletedAt;
}

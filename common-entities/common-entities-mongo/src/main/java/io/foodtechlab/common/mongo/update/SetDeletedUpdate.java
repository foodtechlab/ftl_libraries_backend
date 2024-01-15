package io.foodtechlab.common.mongo.update;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.query.Update;

import java.time.Instant;

@RequiredArgsConstructor(staticName = "of")
public class SetDeletedUpdate {
    private final boolean status;

    public Update getUpdate() {
        return new Update()
                .set("deleted", status)
                .set("deletedAt", Instant.now());
    }
}

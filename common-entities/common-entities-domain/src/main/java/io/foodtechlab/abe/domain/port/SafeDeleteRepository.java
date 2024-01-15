package io.foodtechlab.domain.port;

import com.rcore.domain.commons.port.DeleteRepository;

public interface SafeDeleteRepository<ID> extends DeleteRepository<ID> {
    boolean permanentDelete(ID id);

    boolean restore(ID id);
}

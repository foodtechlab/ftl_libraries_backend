package io.foodtechlab.common.domain.port;

import com.rcore.domain.commons.port.ReadRepository;
import io.foodtechlab.common.domain.entities.BaseDeleteEntity;
import io.foodtechlab.common.domain.port.filters.DeleteFilter;

import java.util.Optional;

public interface SafeReadRepository<ID, E extends BaseDeleteEntity<ID>, F extends DeleteFilter> extends ReadRepository<ID, E, F> {
    Optional<E> forceFindById(ID id);
}

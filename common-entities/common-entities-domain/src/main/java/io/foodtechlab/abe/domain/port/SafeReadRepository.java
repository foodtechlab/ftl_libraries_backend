package io.foodtechlab.domain.port;

import com.rcore.domain.commons.port.ReadRepository;
import io.foodtechlab.domain.entities.BaseDeleteEntity;
import io.foodtechlab.domain.port.filters.DeleteFilter;

import java.util.Optional;

public interface SafeReadRepository<ID, E extends BaseDeleteEntity<ID>, F extends DeleteFilter> extends ReadRepository<ID, E, F> {
    Optional<E> forceFindById(ID id);
}

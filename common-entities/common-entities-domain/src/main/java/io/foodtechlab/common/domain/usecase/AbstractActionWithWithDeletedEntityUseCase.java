package io.foodtechlab.common.domain.usecase;

import com.rcore.domain.commons.port.DeleteRepository;
import io.foodtechlab.common.domain.entities.BaseDeleteEntity;
import io.foodtechlab.common.domain.port.SafeReadRepository;

import java.util.Optional;

public abstract class AbstractActionWithWithDeletedEntityUseCase<ID, R extends SafeReadRepository<ID, E, ?> & DeleteRepository<ID>, E extends BaseDeleteEntity<ID>>
        extends AbstractActionWithEntityUseCase<ID, R, E> {

    public AbstractActionWithWithDeletedEntityUseCase(R repository) {
        super(repository);
    }

    @Override
    protected Optional<E> getEntity(ID id) {
        return repository.forceFindById(id);
    }
}

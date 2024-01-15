package io.foodtechlab.common.domain.usecase;

import com.rcore.domain.commons.usecase.UseCase;
import com.rcore.domain.commons.usecase.model.IdInputValues;
import com.rcore.domain.commons.usecase.model.SingletonOptionalEntityOutputValues;
import io.foodtechlab.common.domain.entities.BaseDeleteEntity;
import io.foodtechlab.common.domain.port.SafeReadRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractForceFindByIdUseCase<ID, E extends BaseDeleteEntity<ID>, R extends SafeReadRepository<ID, E, ?>> extends UseCase<IdInputValues<ID>, SingletonOptionalEntityOutputValues<E>> {
    protected final R repository;

    @Override
    public SingletonOptionalEntityOutputValues<E> execute(IdInputValues<ID> idInputValues) {
        return SingletonOptionalEntityOutputValues.of(repository.forceFindById(idInputValues.getId()));
    }
}

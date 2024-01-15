package io.foodtechlab.common.domain.usecase;

import com.rcore.domain.commons.usecase.UseCase;
import com.rcore.domain.commons.usecase.model.IdInputValues;
import com.rcore.domain.commons.usecase.model.SingleOutput;
import io.foodtechlab.common.domain.port.SafeDeleteRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractRestoreUseCase<ID, R extends SafeDeleteRepository<ID>> extends UseCase<IdInputValues<ID>, SingleOutput<Boolean>> {
    protected final R repository;

    @Override
    public SingleOutput<Boolean> execute(IdInputValues<ID> idIdInputValues) {
        return SingleOutput.of(repository.restore(idIdInputValues.getId()));
    }
}

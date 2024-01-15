package io.foodtechlab.domain.usecase;

import com.rcore.domain.commons.usecase.UseCase;
import com.rcore.domain.commons.usecase.model.IdInputValues;
import com.rcore.domain.commons.usecase.model.SingleOutput;
import lombok.RequiredArgsConstructor;
import io.foodtechlab.domain.port.SafeDeleteRepository;

@RequiredArgsConstructor
public abstract class AbstractPermanentDeleteUseCase<ID, R extends SafeDeleteRepository<ID>> extends UseCase<IdInputValues<ID>, SingleOutput<Boolean>> {
    protected final R repository;

    @Override
    public SingleOutput<Boolean> execute(IdInputValues<ID> idIdInputValues) {
        return SingleOutput.of(repository.permanentDelete(idIdInputValues.getId()));
    }
}

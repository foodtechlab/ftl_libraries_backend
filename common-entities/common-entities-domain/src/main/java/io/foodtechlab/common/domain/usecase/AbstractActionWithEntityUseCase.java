package io.foodtechlab.common.domain.usecase;

import com.rcore.domain.commons.entity.BaseEntity;
import com.rcore.domain.commons.port.ReadRepository;
import com.rcore.domain.commons.usecase.UseCase;
import com.rcore.domain.commons.usecase.model.IdInputValues;
import com.rcore.domain.commons.usecase.model.SingleOutput;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

/**
 * Удалить после релиза ркора 1.0.25
 */
@Deprecated
@RequiredArgsConstructor
public abstract class AbstractActionWithEntityUseCase<ID, R extends ReadRepository<ID, E, ?>, E extends BaseEntity<ID>> extends UseCase<IdInputValues<ID>, SingleOutput<Boolean>> {
    protected final R repository;

    @Override
    public final SingleOutput<Boolean> execute(IdInputValues<ID> input) {
        Optional<E> eOpt = getEntity(input.getId());
        if (eOpt.isEmpty())
            return SingleOutput.of(false);

        E e = eOpt.get();
        boolean result;

        result = execute(e);
        if (result)
            after(e);

        return SingleOutput.of(result);
    }

    protected Optional<E> getEntity(ID id) {
        return repository.findById(id);
    }

    protected abstract boolean execute(E entity);

    /**
     * Действие вызываемое после основого. Здесь можно выкинуть евент
     */
    protected void after(E e) {
    }
}

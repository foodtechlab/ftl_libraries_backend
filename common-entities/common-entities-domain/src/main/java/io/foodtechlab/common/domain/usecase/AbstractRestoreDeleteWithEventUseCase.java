package io.foodtechlab.common.domain.usecase;

import com.rcore.event.driven.EventDispatcher;
import io.foodtechlab.common.domain.entities.BaseDeleteEntity;
import io.foodtechlab.common.domain.events.AbstractEntityRestoreEvent;
import io.foodtechlab.common.domain.port.SafeDeleteRepository;
import io.foodtechlab.common.domain.port.SafeReadRepository;

public abstract class AbstractRestoreDeleteWithEventUseCase<ID, R extends SafeReadRepository<ID, E, ?> & SafeDeleteRepository<ID>, E extends BaseDeleteEntity<ID>> extends AbstractActionWithWithDeletedEntityUseCase<ID, R, E> {
    private final EventDispatcher eventDispatcher;

    public AbstractRestoreDeleteWithEventUseCase(R repository, EventDispatcher eventDispatcher) {
        super(repository);
        this.eventDispatcher = eventDispatcher;
    }

    @Override
    protected boolean execute(E entity) {
        entity.setDeleted(true);
        return repository.permanentDelete(entity.getId());
    }

    @Override
    protected final void after(E e) {
        eventDispatcher.dispatch(restoreEvent(e));
    }

    protected abstract AbstractEntityRestoreEvent<E> restoreEvent(E e);
}

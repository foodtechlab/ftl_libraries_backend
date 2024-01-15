package io.foodtechlab.domain.usecase;

import com.rcore.event.driven.EventDispatcher;
import io.foodtechlab.domain.entities.BaseDeleteEntity;
import io.foodtechlab.domain.events.AbstractEntityRestoreEvent;
import io.foodtechlab.domain.port.SafeDeleteRepository;
import io.foodtechlab.domain.port.SafeReadRepository;

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

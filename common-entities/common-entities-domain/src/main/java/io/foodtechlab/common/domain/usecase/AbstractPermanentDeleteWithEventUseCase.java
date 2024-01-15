package io.foodtechlab.common.domain.usecase;

import com.rcore.domain.commons.port.ReadRepository;
import com.rcore.event.driven.EventDispatcher;
import io.foodtechlab.common.domain.entities.BaseDeleteEntity;
import io.foodtechlab.common.domain.events.AbstractEntityPermanentDeleteEvent;
import io.foodtechlab.common.domain.port.SafeDeleteRepository;
import io.foodtechlab.common.domain.port.SafeReadRepository;

public abstract class AbstractPermanentDeleteWithEventUseCase<ID, R extends ReadRepository<ID, E, ?> & SafeDeleteRepository<ID> & SafeReadRepository<ID, E, ?>, E extends BaseDeleteEntity<ID>> extends AbstractActionWithWithDeletedEntityUseCase<ID, R, E> {
    private final EventDispatcher eventDispatcher;

    public AbstractPermanentDeleteWithEventUseCase(R repository, EventDispatcher eventDispatcher) {
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
        eventDispatcher.dispatch(permanentDeleteEvent(e));
    }

    protected abstract AbstractEntityPermanentDeleteEvent<E> permanentDeleteEvent(E e);
}

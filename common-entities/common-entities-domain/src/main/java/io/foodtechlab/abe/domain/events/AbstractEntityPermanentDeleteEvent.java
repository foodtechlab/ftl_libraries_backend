package io.foodtechlab.domain.events;

import com.rcore.event.driven.AbstractEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import io.foodtechlab.domain.entities.BaseDeleteEntity;

@Getter
@RequiredArgsConstructor
public abstract class AbstractEntityPermanentDeleteEvent<E extends BaseDeleteEntity<?>> extends AbstractEvent {
    protected final E entity;
}

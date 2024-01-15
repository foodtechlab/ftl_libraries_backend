package io.foodtechlab.common.domain.events;

import com.rcore.event.driven.AbstractEvent;
import io.foodtechlab.common.domain.entities.BaseDeleteEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class AbstractEntityRestoreEvent<E extends BaseDeleteEntity<?>> extends AbstractEvent {
    protected final E entity;
}

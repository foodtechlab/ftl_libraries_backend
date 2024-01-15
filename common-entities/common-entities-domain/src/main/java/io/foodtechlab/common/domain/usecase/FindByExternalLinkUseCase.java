package io.foodtechlab.common.domain.usecase;

import com.rcore.domain.commons.entity.BaseEntity;
import com.rcore.domain.commons.usecase.UseCase;
import com.rcore.domain.commons.usecase.model.SingletonOptionalEntityOutputValues;
import io.foodtechlab.common.domain.entities.ExternalProperty;
import io.foodtechlab.common.domain.port.ExternalLinkRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class FindByExternalLinkUseCase<Entity extends BaseEntity<?> & ExternalProperty, Repository extends ExternalLinkRepository<Entity>> extends UseCase<FindByExternalLinkUseCase.InputValues, SingletonOptionalEntityOutputValues<Entity>> {

    private final Repository repository;

    @Override
    public SingletonOptionalEntityOutputValues<Entity> execute(InputValues input) {
        return SingletonOptionalEntityOutputValues.of(repository.findByExternalLink(input.id, input.type));
    }

    @AllArgsConstructor(staticName = "of")
    @NoArgsConstructor(staticName = "empty")
    @Builder
    public static class InputValues implements UseCase.InputValues {

        /**
         * Идентификатор сущности во внешней системе
         */
        private String id;

        /**
         * Тип внешней системы
         */
        private String type;
    }
}

package io.foodtechlab.common.domain.port;

import com.rcore.domain.commons.port.CRUDRepository;
import io.foodtechlab.common.domain.entities.BaseDeleteEntity;
import io.foodtechlab.common.domain.port.filters.DeleteFilter;

/**
 * Репозитории которые имплементируют это, на самом деле не удаляют сущности, вместо этого они по умолчанию не отдают сущности с флагом deleted = true
 */
public interface SafeDeleteCRUDRepository<ID, ENTITY extends BaseDeleteEntity<ID>, FILTERS extends DeleteFilter>
        extends CRUDRepository<ID, ENTITY, FILTERS>,
        SafeDeleteRepository<ID>,
        SafeReadRepository<ID, ENTITY, FILTERS> {
}
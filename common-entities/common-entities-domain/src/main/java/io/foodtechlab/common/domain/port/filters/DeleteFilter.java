package io.foodtechlab.common.domain.port.filters;

import com.rcore.domain.commons.port.dto.SearchFilters;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class DeleteFilter extends SearchFilters {
    protected Boolean deleted;
}

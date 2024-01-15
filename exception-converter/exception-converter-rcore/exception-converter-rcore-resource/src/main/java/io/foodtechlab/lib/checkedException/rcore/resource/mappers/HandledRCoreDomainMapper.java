package ru.foodtechlab.lib.checkedException.rcore.resource.mappers;

import com.rcore.commons.mapper.ExampleDataMapper;
import org.mapstruct.Mapper;
import io.foodtechlab.aeh.core.Error;
import ru.foodtechlab.lib.checkedException.rcore.domain.exceptions.HandledRCoreDomainException;

@Mapper(componentModel = "spring")
public interface HandledRCoreDomainMapper extends ExampleDataMapper<HandledRCoreDomainException.RCoreError, Error> {
}

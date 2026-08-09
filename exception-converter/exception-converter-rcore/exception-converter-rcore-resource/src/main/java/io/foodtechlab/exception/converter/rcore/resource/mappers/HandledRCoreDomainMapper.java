package io.foodtechlab.exception.converter.rcore.resource.mappers;

import com.rcore.commons.mapper.ExampleDataMapper;
import io.foodtechlab.exception.converter.rcore.domain.exceptions.HandledRCoreDomainException;
import io.foodtechlab.exceptionhandler.core.Error;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HandledRCoreDomainMapper extends ExampleDataMapper<HandledRCoreDomainException.RCoreError, Error> {
}

package io.foodtechlab.exception.converter;

import io.foodtechlab.exception.converter.core.init.EnableCheckedExceptionService;
import io.foodtechlab.exception.converter.handler.init.EnableCheckedExceptionHandler;
import io.foodtechlab.exception.converter.rcore.client.init.EnableCheckedExceptionRCoreErrorDecoderConfiguration;
import io.foodtechlab.exception.converter.rcore.handler.init.EnableCheckedExceptionRCoreHandler;
import io.foodtechlab.exception.converter.rcore.resource.init.EnableCheckedExceptionRCoreResource;

@EnableCheckedExceptionHandler
@EnableCheckedExceptionService
@EnableCheckedExceptionRCoreErrorDecoderConfiguration
@EnableCheckedExceptionRCoreResource
@EnableCheckedExceptionRCoreHandler
class CheckedExceptionConfiguration {
}

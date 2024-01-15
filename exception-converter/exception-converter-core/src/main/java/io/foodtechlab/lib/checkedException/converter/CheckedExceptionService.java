package ru.foodtechlab.lib.checkedException.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import ru.foodtechlab.lib.checkedException.domain.CheckedDomainException;
import ru.foodtechlab.lib.checkedException.domain.UnknownCheckedDomainException;
import ru.foodtechlab.lib.checkedException.resource.CheckedExceptionResponse;


import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Log4j2
public class CheckedExceptionService {
    /**
     * Ключ домен, значение конвертеры, для более быстрого поиска в runtime
     */
    private final Map<String, Collection<CheckedExceptionApiConverter<?, ?>>> mappers;
    private final ObjectMapper objectMapper;

    public CheckedExceptionService(Collection<CheckedExceptionApiConverter<?, ?>> mappers, ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        //noinspection unchecked,rawtypes
        this.mappers = (Map<String, Collection<CheckedExceptionApiConverter<?, ?>>>) (Map) mappers
                .stream()
                .collect(Collectors.groupingBy(CheckedExceptionApiConverter::getDomain));
    }

    public CheckedExceptionApiConverter<?, ?> findConverter(String domain, String reason) {
        return mappers
                .getOrDefault(domain, Collections.emptySet())
                .stream()
                .filter(m -> m.getReason().equals(reason))
                .findFirst()
                .orElse(null);
    }

    private UnknownCheckedDomainException createUnknownDomainException(CheckedExceptionResponse response) {
        return new UnknownCheckedDomainException(response.getDomain(), response.getReason(), response.getDetails());
    }

    private CheckedExceptionResponse createUnknownResponse(CheckedDomainException exception) {
        return CheckedExceptionResponse.builder()
                .details(exception.getDetails())
                .domain(exception.getDomain())
                .reason(exception.getReason())
                .build();
    }

    /**
     * Парсит ошибку в домен из запроса
     */
    public CheckedDomainException parse(CheckedExceptionResponse response) {
        CheckedExceptionApiConverter<?, ?> exceptionConverter = findConverter(response.getDomain(), response.getReason());

        if (exceptionConverter == null)
            return createUnknownDomainException(response);
        try {
            return _parse(exceptionConverter, response.getData());
        } catch (Exception exception) {
            log.info("Fail parse " + response);
            exception.printStackTrace();

            return createUnknownDomainException(response);
        }
    }

    /**
     * Конвертирует домен в API модель
     */
    public CheckedExceptionResponse convert(CheckedDomainException exception) {
        CheckedExceptionApiConverter<?, ?> converter = findConverter(exception.getDomain(), exception.getReason());
        if (converter == null)
            return createUnknownResponse(exception);
        try {
            return _convert(converter, exception);
        } catch (Exception ex) {
            log.warn("Failing convert " + exception.getClass().getName() + " to response");
            ex.printStackTrace();

            return createUnknownResponse(exception);
        }
    }

    private <E extends CheckedDomainException, D> CheckedExceptionResponse _convert(CheckedExceptionApiConverter<?, ?> converter, E exception) {
        //noinspection unchecked
        D data = ((CheckedExceptionApiConverter<E, D>) converter).writeResponse(exception);
        Map<String, Object> convertedData;
        if (data != null)
            //noinspection unchecked
            convertedData = objectMapper.convertValue(data, Map.class);
        else
            convertedData = null;

        return CheckedExceptionResponse.builder()
                .reason(exception.getReason())
                .domain(exception.getDomain())
                .details(exception.getDetails())
                .data(convertedData)
                .build();
    }

    private <E extends CheckedDomainException, D> E _parse(CheckedExceptionApiConverter<E, D> exceptionConverter, Map<String, Object> map) {
        D data;

        if (map == null)
            data = null;
        else
            data = objectMapper.convertValue(map, exceptionConverter.getDataType());

        return exceptionConverter.readResponse(data);
    }

}

package io.foodtechlab.exception.converter.rcore.domain.rcoreExceptions;


import com.rcore.domain.commons.exception.DomainException;
import io.foodtechlab.exception.converter.domain.CheckedDomainException;

/**
 * По сути адаптер, конвертирует checked в обычный rcore
 */
public class UnknownCheckedDomainRCoreException extends DomainException {
    public UnknownCheckedDomainRCoreException(CheckedDomainException exception) {
        super(exception.getDomain(), exception.getMessage(), exception.getDetails());
    }
}

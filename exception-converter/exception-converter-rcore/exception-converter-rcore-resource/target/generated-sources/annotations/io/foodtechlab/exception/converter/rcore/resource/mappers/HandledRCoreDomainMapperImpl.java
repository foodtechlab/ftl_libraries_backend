package io.foodtechlab.exception.converter.rcore.resource.mappers;

import io.foodtechlab.exception.converter.rcore.domain.exceptions.HandledRCoreDomainException;
import io.foodtechlab.exceptionhandler.core.Error;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-15T21:30:04+0300",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class HandledRCoreDomainMapperImpl implements HandledRCoreDomainMapper {

    @Override
    public Error map(HandledRCoreDomainException.RCoreError arg0) {
        if ( arg0 == null ) {
            return null;
        }

        Error error = new Error();

        error.setPresentationData( presentationDataToPresentationData( arg0.getPresentationData() ) );
        error.setDomain( arg0.getDomain() );
        error.setReason( arg0.getReason() );
        error.setDetails( arg0.getDetails() );

        return error;
    }

    @Override
    public HandledRCoreDomainException.RCoreError inverseMap(Error arg0) {
        if ( arg0 == null ) {
            return null;
        }

        HandledRCoreDomainException.RCoreError.RCoreErrorBuilder rCoreError = HandledRCoreDomainException.RCoreError.builder();

        rCoreError.domain( arg0.getDomain() );
        rCoreError.reason( arg0.getReason() );
        rCoreError.details( arg0.getDetails() );
        rCoreError.presentationData( presentationDataToPresentationData1( arg0.getPresentationData() ) );

        return rCoreError.build();
    }

    protected Error.PresentationData presentationDataToPresentationData(HandledRCoreDomainException.PresentationData presentationData) {
        if ( presentationData == null ) {
            return null;
        }

        Error.PresentationData presentationData1 = new Error.PresentationData();

        presentationData1.setTitle( presentationData.getTitle() );
        presentationData1.setMessage( presentationData.getMessage() );

        return presentationData1;
    }

    protected HandledRCoreDomainException.PresentationData presentationDataToPresentationData1(Error.PresentationData presentationData) {
        if ( presentationData == null ) {
            return null;
        }

        HandledRCoreDomainException.PresentationData.PresentationDataBuilder presentationData1 = HandledRCoreDomainException.PresentationData.builder();

        presentationData1.title( presentationData.getTitle() );
        presentationData1.message( presentationData.getMessage() );

        return presentationData1.build();
    }
}

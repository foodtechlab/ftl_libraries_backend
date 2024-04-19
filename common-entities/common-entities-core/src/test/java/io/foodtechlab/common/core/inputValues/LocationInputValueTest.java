package io.foodtechlab.common.core.inputValues;

import com.rcore.domain.commons.exception.DomainException;
import com.rcore.domain.commons.exception.ValidationPayload;
import io.foodtechlab.common.core.entities.exception.CommonErrorReason;
import io.foodtechlab.common.core.entities.exception.Domain;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LocationInputValueTest {
    private final Validator validator;


    public LocationInputValueTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
        factory.close();
    }

    public static DomainException.Error buildError(ConstraintViolation<LocationInputValue> c) {
        AtomicReference<DomainException.Error> error = new AtomicReference(new DomainException.Error("SERVER", "UNKNOWN", c.getMessage()));

        c.getConstraintDescriptor().getPayload().forEach((p) -> {
            if (ValidationPayload.class.isAssignableFrom(p)) {
                try {
                    ValidationPayload payload = (ValidationPayload) p.getDeclaredConstructor().newInstance();
                    error.set(new DomainException.Error(payload.getDomain(), payload.getReason(), c.getMessage()));
                } catch (Exception var4) {
                    var4.printStackTrace();
                }
            }
        });

        return error.get();
    }

    @Test
    public void successCreateZero() {
        double longitude = 0;
        double latitude = 0;

        LocationInputValue location = LocationInputValue.of(latitude, longitude);
        List<DomainException.Error> errors = validate(location);

        assertTrue(errors.isEmpty());
    }

    @Test
    public void successCreateMax() {
        double longitude = 180;
        double latitude = 90;

        LocationInputValue location = LocationInputValue.of(latitude, longitude);
        List<DomainException.Error> errors = validate(location);

        assertTrue(errors.isEmpty());
    }

    @Test
    public void successCreateMin() {
        double longitude = -180;
        double latitude = -90;

        LocationInputValue location = LocationInputValue.of(latitude, longitude);
        List<DomainException.Error> errors = validate(location);

        assertTrue(errors.isEmpty());
    }

    @Test
    public void longitudeReachMax() {
        double longitude = 181;
        double latitude = 45.2456;

        String domain = Domain.LOCATION.name();
        String reason = CommonErrorReason.LONGITUDE_REACH_MAX.name();

        LocationInputValue location = LocationInputValue.of(latitude, longitude);
        List<DomainException.Error> errors = validate(location);

        assertNotNull(
                errors.stream()
                        .filter(error -> domain.equals(error.getDomain()) && reason.equals(error.getReason()))
                        .findFirst()
        );
    }

    @Test
    public void latitudeReachMax() {
        double longitude = 48.261651;
        double latitude = 91;

        String domain = Domain.LOCATION.name();
        String reason = CommonErrorReason.LATITUDE_REACH_MAX.name();

        LocationInputValue location = LocationInputValue.of(latitude, longitude);
        List<DomainException.Error> errors = validate(location);

        assertNotNull(
                errors.stream()
                        .filter(error -> domain.equals(error.getDomain()) && reason.equals(error.getReason()))
                        .findFirst()
        );
    }

    @Test
    public void longitudeReachMin() {
        double longitude = -181;
        double latitude = 45.2456;

        String domain = Domain.LOCATION.name();
        String reason = CommonErrorReason.LONGITUDE_REACH_MIN.name();

        LocationInputValue location = LocationInputValue.of(latitude, longitude);
        List<DomainException.Error> errors = validate(location);

        assertNotNull(
                errors.stream()
                        .filter(error -> domain.equals(error.getDomain()) && reason.equals(error.getReason()))
                        .findFirst()
        );
    }

    @Test
    public void latitudeReachMin() {
        double longitude = 48.261651;
        double latitude = -91;

        String domain = Domain.LOCATION.name();
        String reason = CommonErrorReason.LATITUDE_REACH_MIN.name();

        LocationInputValue location = LocationInputValue.of(latitude, longitude);
        List<DomainException.Error> errors = validate(location);

        assertNotNull(
                errors.stream()
                        .filter(error -> domain.equals(error.getDomain()) && reason.equals(error.getReason()))
                        .findFirst()
        );
    }

    public List<DomainException.Error> validate(LocationInputValue input) {

        return validator.validate(input)
                .stream()
                .map(LocationInputValueTest::buildError)
                .collect(Collectors.toList());
    }
}

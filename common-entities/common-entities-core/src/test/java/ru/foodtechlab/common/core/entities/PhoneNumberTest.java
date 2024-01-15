package ru.foodtechlab.common.core.entities;

import com.google.i18n.phonenumbers.NumberParseException;
import io.foodtechlab.common.core.entities.CountryCode;
import io.foodtechlab.common.core.entities.PhoneNumber;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PhoneNumberTest {

    @ParameterizedTest
    @ArgumentsSource(TestData.CreatePhoneNumber_OK.class)
    public void create(String stingNumber) throws NumberParseException {
        PhoneNumber phone = PhoneNumber.of(stingNumber);

        assertEquals("79023602131", phone.getPhoneNumber());
        assertEquals(true, phone.getValid());
        Assertions.assertEquals(CountryCode.RUSSIA, phone.getCountryCode());

    }

    @ParameterizedTest
    @ArgumentsSource(TestData.CreatePhoneNumber_OK.class)
    public void correctPrimitiveString(String stingNumber) throws NumberParseException {
        PhoneNumber phone = PhoneNumber.of(stingNumber);

        assertEquals("79023602131", phone.toString());
        assertEquals(true, phone.getValid());
        assertEquals(CountryCode.RUSSIA, phone.getCountryCode());

    }

    private static class TestData {
        public static class CreatePhoneNumber_OK implements ArgumentsProvider {

            @Override
            public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
                return Stream.of(
                        Arguments.of("+79023602131"),
                        Arguments.of("79023602131"),
                        Arguments.of("89023602131"),

                        Arguments.of("+7 902 360-21-31"),
                        Arguments.of("7 (9023) 60-21-31"),
                        Arguments.of("8 902 (360)-21-31"),

                        Arguments.of("+7 902 360 21 31"),
                        Arguments.of("7 902 360 21 31"),
                        Arguments.of("8 902 36021 31")
                );
            }
        }
    }
}

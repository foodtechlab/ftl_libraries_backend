package ru.foodtechlab.common.core.entities;

import io.foodtechlab.common.core.entities.Location;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocationTest {
    @Test
    public void createEmpty() {
        Location location = Location.empty();

        assertEquals(0, location.getLatitude());
        assertEquals(0, location.getLongitude());

        Location builder = Location.builder().build();

        assertEquals(0, builder.getLatitude());
        assertEquals(0, builder.getLongitude());

    }


    @Test
    public void invalidLongitude() {
        double longitude = 222.2555555;
        double latitude = 48.557514555;

        Location location = Location.of(latitude, longitude);

        assertEquals(180, location.getLongitude());
        assertEquals(latitude, location.getLatitude());
    }

    @Test
    public void invalidLongitudeViaBuilder() {
        double longitude = 222.2555555;
        double latitude = 48.557514555;

        Location builder = Location.builder()
                .longitude(longitude)
                .latitude(latitude)
                .build();


        assertEquals(180, builder.getLongitude());
        assertEquals(latitude, builder.getLatitude());
    }

    @Test
    public void invalidLatitudeViaBuilder() {
        double longitude = 44.84812;
        double latitude = 91.55151;

        Location builder = Location.builder()
                .longitude(longitude)
                .latitude(latitude)
                .build();


        assertEquals(longitude, builder.getLongitude());
        assertEquals(90, builder.getLatitude());
    }


    @Test
    public void invalidLatitude() {
        double longitude = 44.84812;
        double latitude = 91.55151;

        Location location = Location.of(latitude, longitude);

        assertEquals(longitude, location.getLongitude());
        assertEquals(90, location.getLatitude());
    }

    @Test
    public void negativeLatitude() {
        double longitude = 44.84812;
        double latitude = -91.55151;

        Location location = Location.of(latitude, longitude);

        assertEquals(longitude, location.getLongitude());
        assertEquals(-90, location.getLatitude());

        Location builder = Location.builder()
                .longitude(longitude)
                .latitude(latitude)
                .build();


        assertEquals(longitude, builder.getLongitude());
        assertEquals(-90, builder.getLatitude());
    }

    @Test
    public void negativeLatitudeViaBuilder() {
        double longitude = 44.84812;
        double latitude = -91.55151;

        Location builder = Location.builder()
                .longitude(longitude)
                .latitude(latitude)
                .build();

        assertEquals(longitude, builder.getLongitude());
        assertEquals(-90, builder.getLatitude());
    }

    @Test
    public void negativeLongitude() {
        double longitude = -222.2555555;
        double latitude = 48.557514555;

        Location location = Location.of(latitude, longitude);

        assertEquals(-180, location.getLongitude());
        assertEquals(latitude, location.getLatitude());
    }


    @Test
    public void equalsLocation() {
        double longitude = 44.68461618;
        double latitude = 48.557514555;

        Location first = Location.of(latitude, longitude);
        Location second = Location.of(latitude, longitude);

        assertEquals(first, second);
    }

    @Test
    public void negativeLongitudeViaBuilder() {
        double longitude = -222.2555555;
        double latitude = 48.557514555;

        Location builder = Location.builder()
                .longitude(longitude)
                .latitude(latitude)
                .build();


        assertEquals(-180, builder.getLongitude());
        assertEquals(latitude, builder.getLatitude());
    }

    @Test
    public void testBorderViaBuilder() {
        double longitude = 180;
        double latitude = 90;

        Location builder = Location.builder()
                .longitude(longitude)
                .latitude(latitude)
                .build();


        assertEquals(180, builder.getLongitude());
        assertEquals(90, builder.getLatitude());
    }

    @Test
    public void testBorder() {
        double longitude = 180;
        double latitude = 90;

        Location location = Location.of(latitude, longitude);

        assertEquals(180, location.getLongitude());
        assertEquals(90, location.getLatitude());
    }

    @Test
    public void testNegativeBorderViaBuilder() {
        double longitude = -180;
        double latitude = -90;

        Location builder = Location.builder()
                .longitude(longitude)
                .latitude(latitude)
                .build();


        assertEquals(-180, builder.getLongitude());
        assertEquals(-90, builder.getLatitude());
    }

    @Test
    public void testNegativeBorder() {
        double longitude = -180;
        double latitude = -90;

        Location location = Location.of(latitude, longitude);

        assertEquals(-180, location.getLongitude());
        assertEquals(-90, location.getLatitude());
    }
}

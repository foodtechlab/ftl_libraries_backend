package io.foodtechlab.common.core.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class Location {
    private double latitude;
    private double longitude;

    public Location(double latitude, double longitude) {
        this.latitude = latitude > 90 ? 90 : latitude < -90 ? -90 : latitude;
        this.longitude = longitude > 180 ? 180 : longitude < -180 ? -180 : longitude;
    }

    public static Location empty() {
        return new Location(0, 0);
    }

    public static Location of(double latitude, double longitude) {
        return new Location(latitude, longitude);
    }

    public static Builder builder() {
        return new Builder();
    }

    @Setter
    public static class Builder {
        public double longitude;
        public double latitude;

        public Builder longitude(double longitude) {
            this.longitude = longitude;
            return this;
        }

        public Builder latitude(double latitude) {
            this.latitude = latitude;
            return this;
        }

        public Location build() {
            return Location.of(latitude, longitude);
        }
    }
}

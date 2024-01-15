package io.foodtechlab.common.core.entities;

import com.rcore.commons.utils.StringUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Setter
@SuperBuilder
public class FullName {
    protected String firstName;
    protected String middleName;
    protected String lastName;

    public FullName(String firstName, String middleName, String lastName) {
        this.firstName = firstName != null ? firstName : "";
        this.middleName = middleName != null ? middleName : "";
        this.lastName = lastName != null ? lastName : "";
    }

    public FullName() {
        this.firstName = "";
        this.middleName = "";
        this.lastName = "";
    }

    public static FullName of(String firstName, String middleName, String lastName) {
        return new FullName(firstName, middleName, lastName);
    }

    public static FullName empty() {
        return new FullName("", "", "");
    }

    @Override
    public String toString() {
        return Stream.of(firstName, lastName, middleName)
                .filter(StringUtils::hasText)
                .collect(Collectors.joining(" "));
    }

    public boolean isEmpty() {
        return !StringUtils.hasText(firstName) && !StringUtils.hasText(middleName) && !StringUtils.hasText(lastName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FullName fullName = (FullName) o;
        return Objects.equals(firstName, fullName.firstName) && Objects.equals(middleName, fullName.middleName) && Objects.equals(lastName, fullName.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, middleName, lastName);
    }
}

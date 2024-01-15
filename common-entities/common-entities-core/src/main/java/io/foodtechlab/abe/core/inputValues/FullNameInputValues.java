package io.foodtechlab.core.inputValues;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import io.foodtechlab.core.entities.FullName;

@Getter
@SuperBuilder
@RequiredArgsConstructor(staticName = "of")
public class FullNameInputValues {
    private final String firstName;
    private final String middleName;
    private final String lastName;

    public FullName toDomain() {
        return new FullName(firstName, middleName, lastName);
    }
}

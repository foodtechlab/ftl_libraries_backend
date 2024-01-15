package io.foodtechlab.common.core.inputValues;

import io.foodtechlab.common.core.entities.FullName;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

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

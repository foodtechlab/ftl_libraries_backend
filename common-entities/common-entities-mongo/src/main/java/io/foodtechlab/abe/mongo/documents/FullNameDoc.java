package io.foodtechlab.mongo.documents;

import com.rcore.commons.utils.StringUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import io.foodtechlab.core.entities.FullName;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Setter
@NoArgsConstructor
public class FullNameDoc {
    private String firstName;
    private String middleName;
    private String lastName;

    @Indexed
    private String fullName;

    public FullNameDoc(String firstName, String middleName, String lastName) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.fullName = toString();
    }

    public static FullNameDoc empty() {
        return new FullNameDoc("", "", "");
    }

    public static FullNameDoc of(FullName fullName) {
        return new FullNameDoc(
                fullName.getFirstName(),
                fullName.getMiddleName(),
                fullName.getLastName()
        );
    }

    public FullName toDomain() {
        return new FullName(firstName, middleName, lastName);
    }

    @Override
    public String toString() {
        return Stream.of(lastName, firstName, middleName)
                .filter(StringUtils::hasText)
                .collect(Collectors.joining(" "));
    }
}

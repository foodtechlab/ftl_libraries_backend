package io.foodtechlab.mongo.documents;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(staticName = "of")
@Deprecated(forRemoval = true)
public class PhoneNumberDoc {
    // NOTE: не смейте расскоментировать. Вместо этого нужно юзать - io.foodtechlab.core.entities.PhoneNumber
    // Либо если очень хочется напишите maslovra
//    long phoneNumber;
//    PhoneNumber.CountryCode countryCode;
//    @Indexed
//    String phoneString;
//
//    public static PhoneNumberDoc of(PhoneNumber phone) {
//        if (phone == null)
//            return null;
//
//        return PhoneNumberDoc.of(phone.getPhoneNumber(), phone.getCountryCode(), phone.toString());
//    }
//
//    public PhoneNumber toDomain() {
//        return PhoneNumber.of(phoneNumber, countryCode);
//    }
}

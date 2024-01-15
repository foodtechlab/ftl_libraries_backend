package io.foodtechlab.core.entities;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;


@NoArgsConstructor
@Getter
public class PhoneNumber {
    protected String phoneNumber;
    protected CountryCode countryCode;
    // PhoneNumberFormat.INTERNATIONAL
    protected String phoneNumberInternational;
    // PhoneNumberFormat.E164
    protected String phoneNumberE164;
    protected Boolean valid;


    public static String format(String value) {
        if(value == null) return "";

        String formatted = value.replaceAll("[^0-9]", "");
        if (formatted.startsWith("89")) formatted = "7" + formatted.substring(1);

        return formatted;
    }

    public static boolean isValid(String challenge) {
        try {
            String formattedValue = format(challenge);
            CountryCode countryCode = CountryCode.parse(formattedValue);

            PhoneNumberUtil pnu = PhoneNumberUtil.getInstance();
            Phonenumber.PhoneNumber pn = pnu.parse(formattedValue, countryCode.countryCode());
            pnu.format(pn, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
            pnu.format(pn, PhoneNumberUtil.PhoneNumberFormat.E164);
            return true;
        } catch (Exception _ignore) {
            return false;
        }
    }

    public void setPhoneNumber(String phoneNumber) {
        String formattedValue = format(phoneNumber);

        this.phoneNumber = formattedValue;
        try {
            this.countryCode = CountryCode.parse(formattedValue);

            PhoneNumberUtil pnu = PhoneNumberUtil.getInstance();
            Phonenumber.PhoneNumber pn = pnu.parse(formattedValue, countryCode.countryCode());
            this.phoneNumberInternational = pnu.format(pn, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
            this.phoneNumberE164 = pnu.format(pn, PhoneNumberUtil.PhoneNumberFormat.E164);
            this.valid = true;
        } catch (Exception _ignore) {
            this.valid = false;
            this.phoneNumberInternational = null;
            this.phoneNumberE164 = null;
        }
    }

    public static PhoneNumber of(String value) throws NumberParseException {
        if(value == null) throw new NumberParseException(NumberParseException.ErrorType.NOT_A_NUMBER, "PhoneNumber of exeption");

        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setPhoneNumber(value);
        if (phoneNumber.valid == false)
            throw new NumberParseException(NumberParseException.ErrorType.NOT_A_NUMBER, "PhoneNumber of exeption");

        return phoneNumber;
    }

    @Override
    public String toString() {
        return phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhoneNumber that = (PhoneNumber) o;
        return Objects.equals(phoneNumber, that.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phoneNumber);
    }


}

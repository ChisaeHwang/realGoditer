package realGoditer.example.realGoditer.domain.member.domain.converter;

import jakarta.persistence.AttributeConverter;
import realGoditer.example.realGoditer.domain.member.domain.EncodedPassword;


public class PasswordConverter implements AttributeConverter<EncodedPassword, String> {

    @Override
    public String convertToDatabaseColumn(final EncodedPassword encodedPassword) {
        return encodedPassword.getValue();
    }

    @Override
    public EncodedPassword convertToEntityAttribute(final String encodedPassword) {
        return EncodedPassword.from(encodedPassword);
    }
}

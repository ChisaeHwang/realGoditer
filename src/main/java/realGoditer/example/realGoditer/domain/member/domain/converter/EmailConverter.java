package realGoditer.example.realGoditer.domain.member.domain.converter;

import jakarta.persistence.AttributeConverter;
import realGoditer.example.realGoditer.domain.member.domain.Email;

public class EmailConverter implements AttributeConverter<Email, String> {

    @Override
    public String convertToDatabaseColumn(final Email email) {
        return email.getValue();
    }

    @Override
    public Email convertToEntityAttribute(final String email) {
        return Email.from(email);
    }
}

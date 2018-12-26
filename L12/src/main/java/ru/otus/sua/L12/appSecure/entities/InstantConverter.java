package ru.otus.sua.L12.appSecure.entities;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.Instant;
import java.util.Date;

/**
 * JPA Convention to automatically convert Instant Java 8 Time API into Database (PostgreSQL) and
 * vice versa.
 */
@Converter(autoApply = true)
public class InstantConverter implements AttributeConverter<Instant, Date> {

    @Override
    public Date convertToDatabaseColumn(final Instant instant) {
        return (instant == null) ? null : Date.from(instant);
    }

    @Override
    public Instant convertToEntityAttribute(final Date dbData) {
        return (dbData == null) ? null : dbData.toInstant();
    }

}

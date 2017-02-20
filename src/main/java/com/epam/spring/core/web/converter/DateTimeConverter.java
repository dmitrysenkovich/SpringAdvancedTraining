package com.epam.spring.core.web.converter;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeConverter implements Converter<String, Date> {
    private final SimpleDateFormat simpleDateFormat;

    public DateTimeConverter(String dateFormat) {
        this.simpleDateFormat = new SimpleDateFormat(dateFormat);
    }

    @Override
    public Date convert(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }

        try {
            return simpleDateFormat.parse(source);
        } catch (ParseException e) {
            return null;
        }
    }
}

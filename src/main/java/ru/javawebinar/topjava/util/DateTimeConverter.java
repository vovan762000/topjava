package ru.javawebinar.topjava.util;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeConverter {
    @Component
    public static class DateConverter implements Converter<String, LocalDate> {

        @Override
        public LocalDate convert(String source) {
            return LocalDate.parse(source, DateTimeFormatter.ISO_LOCAL_DATE);
        }
    }

    @Component
    public static class TimeConverter implements Converter<String, LocalTime> {

        @Override
        public LocalTime convert(String source) {
            return LocalTime.parse(source, DateTimeFormatter.ISO_LOCAL_TIME);
        }
    }
}

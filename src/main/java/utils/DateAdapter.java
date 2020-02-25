package utils;

import io.vavr.control.Try;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public abstract class DateAdapter {
    private static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.ENGLISH);

    public static String format(LocalDate date) {
        return Try.of(() -> date.format(DEFAULT_FORMATTER)).getOrElse("");
    }

    public static LocalDate parse(String stringDate) {
        return Try.of(() -> LocalDate.parse(stringDate, DEFAULT_FORMATTER)).getOrNull();
    }
}

package com.netler.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public final class DateRangeParser {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    private DateRangeParser() {
    }

    public static List<String> getDateRange(String startDate, String endDate) throws IllegalArgumentException {
        List<String> dateList = new ArrayList<>();

        LocalDate start = DateRangeParser.validateAndParse(startDate, "startDate");
        LocalDate end = DateRangeParser.validateAndParse(endDate, "endDate");
        DateRangeParser.validateDateRange(start, end);
        while (!start.isAfter(end)) {
            dateList.add(start.toString());
            start = start.plusDays(1);
        }

        return dateList;
    }

    private static LocalDate validateAndParse(String date, String paramName) {
        try {
            return LocalDate.parse(date, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                paramName + " is not a valid date. Expected format: YYYY-MM-DD");
        }
    }

    private static void validateDateRange(LocalDate startDate, LocalDate endDate) {
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("end date cannot be before start date");
        }
    }
}

package com.netler.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

class DateRangeParserTest {
    @Test
    @Timeout(2)
    void endDateBeforeStartDateShouldGiveError() {
        IllegalArgumentException exception =
            assertThrows(IllegalArgumentException.class,
                () -> DateRangeParser.getDateRange("2021-12-05", "2021-12-04"));
        assertEquals("end date cannot be before start date", exception.getMessage());
    }

    @Test
    @Timeout(2)
    void wrongDateFormatShouldGiveError() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> DateRangeParser.getDateRange("1O-04-2021", "1O-05-2021"));
        assertEquals("startDate is not a valid date. Expected format: YYYY-MM-DD", exception.getMessage());
    }

    @Test
    @Timeout(2)
    void wrongStartDateShouldGiveError() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> DateRangeParser.getDateRange("2021-1O-04", "2021-12-05"));
        assertEquals("startDate is not a valid date. Expected format: YYYY-MM-DD", exception.getMessage());
    }

    @Test
    @Timeout(2)
    void malformedEndDateShouldGiveError() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> DateRangeParser.getDateRange("2021-12-05", "2021-l2-06"));
        assertEquals("endDate is not a valid date. Expected format: YYYY-MM-DD", exception.getMessage());
    }

    @Test
    @Timeout(2)
    void sameStartEndDateShouldWork() {
        String date = "2021-12-05";
        List<String> actual = DateRangeParser.getDateRange(date, date);
        assertEquals(1, actual.size());
        assertEquals(date, actual.getFirst());
    }
}
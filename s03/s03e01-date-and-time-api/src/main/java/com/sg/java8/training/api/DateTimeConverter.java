package com.sg.java8.training.api;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

// DRY - don't repeat yourself
final class DateTimeConverter {

    static Date convertLocalDate(final LocalDate localDate) {
        Objects.requireNonNull(localDate);
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault())
                                  .toInstant());
    }

    static LocalDate convertDate(final Date date) {
        Objects.requireNonNull(date);
        return date.toInstant()
                   .atZone(ZoneId.systemDefault())
                   .toLocalDate();
    }
}

package com.sg.java8.training.api;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

/**
 * A few {@link java.time.LocalDate}s and {@link java.time.LocalTime}s usage samples
 */
public class DateTimeAPIMain {

    public static void main(String[] args) {
        localDateSamples();

        localTimeSamples();

        localDateTimeSamples();

        zoneSamples();
    }

    private static void localDateSamples() {
        final LocalDate localDate = LocalDate.now();
        System.out.println("The current local date is " + localDate);

        final LocalDate then = LocalDate.of(2017, Month.FEBRUARY, 10);
        System.out.println("The date then was " + then);

        final DayOfWeek dayOfWeek = then.getDayOfWeek();
        System.out.println("The day of the week was " + dayOfWeek);
    }

    private static void localTimeSamples() {
        final LocalTime localTime = LocalTime.now();
        System.out.println("The local time is " + localTime);

        final LocalTime thenTime = LocalTime.of(10, 20);
        System.out.println("The time was then " + thenTime);
    }

    private static void localDateTimeSamples() {
        final LocalDateTime localDateTime = LocalDateTime.now();

        final LocalDateTime then = LocalDateTime.of(
                LocalDate.of(2017, Month.JANUARY, 15),
                LocalTime.of(10, 15)
        );

        System.out.println(localDateTime.isAfter(then));
    }

    private static void zoneSamples() {
        final ZoneId romanianZone = ZoneId.of("Europe/Bucharest");
        final ZoneId japanZone = ZoneId.of("Asia/Tokyo");

        final LocalTime localTime = LocalTime.now(romanianZone);
        final LocalTime japanTime = LocalTime.now(japanZone);
        System.out.println(japanTime.isAfter(localTime));

        final ZoneOffset zoneOffset = ZoneOffset.ofHours(5);
        final OffsetTime anotherTime = localTime.atOffset(zoneOffset);
        System.out.println(anotherTime.toLocalTime());
    }
}
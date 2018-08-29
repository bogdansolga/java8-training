package net.safedata.java8.training.api;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.OffsetTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

/**
 * A few {@link java.time.LocalDate}s and {@link java.time.LocalTime}s usage samples
 */
public class DateTimeAPIMain {

    public static void main(String[] args) {
        localDateSamples();

        localTimeSamples();

        localDateTimeSamples();

        periodDurationAndClockSamples();

        zoneSamples();
    }

    private static void localDateSamples() {
        final LocalDate localDate = LocalDate.now();
        System.out.println("The current local date is " + localDate);

        final LocalDate then = LocalDate.of(2017, Month.FEBRUARY, 10);
        System.out.println("The date then was " + then);

        final Date date = new Date();
        final LocalDate convertedDate = DateTimeConverter.convertDate(date);
        System.out.println("Converted date - " + convertedDate);

        final Date convertedLocalDate = DateTimeConverter.convertLocalDate(convertedDate);
        System.out.println("Converted local date is " + convertedLocalDate);

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

    private static void periodDurationAndClockSamples() {
        final Period holiday = Period.ofDays(40);
        System.out.println(holiday);

        final LocalDate localDate = LocalDate.now();
        final LocalDate theNextDate = localDate.plus(holiday);
        System.out.println(theNextDate);

        final Duration duration = Duration.ofDays(3);
        System.out.println(duration);

        final Clock clock = Clock.system(ZoneId.of("Europe/Bucharest"));
        System.out.println("The clock is " + clock.instant());
    }

    private static void zoneSamples() {
        //ZoneId.getAvailableZoneIds().forEach(zoneId -> System.out.println(zoneId));

        final ZoneId romanianZone = ZoneId.of("Europe/Bucharest");
        final ZoneId japanZone = ZoneId.of("Asia/Tokyo");

        final LocalTime localTime = LocalTime.now(romanianZone);
        System.out.println("RO time - " + localTime);

        final LocalTime japanTime = LocalTime.now(japanZone);
        System.out.println("JP time - " + japanTime);

        System.out.println(japanTime.isAfter(localTime));

        final ZoneOffset zoneOffset = ZoneOffset.ofHours(5);
        final OffsetTime anotherTime = localTime.atOffset(zoneOffset);
        System.out.println(anotherTime.toLocalTime());
    }
}
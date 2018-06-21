package com.sg.java8.training.jool;

import org.jooq.lambda.Unchecked;
import org.jooq.lambda.fi.util.function.CheckedFunction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * A few {@link org.jooq.lambda.Unchecked} usage samples
 */
@SuppressWarnings("unused")
public class UncheckedMain {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public static void main(String[] args) {
        final String convertedDate = "2017-04-20";
        final Date date = dateConversionFunction().apply(convertedDate);
        System.out.println("The converted date is " + date);

        uncheckedConversion(convertedDate);

        final List<String> datesToBeParsed = Arrays.asList("2017-04-20", "2017-04-21");
        uncheckedPredicate(datesToBeParsed);
        uncheckedConsumer(datesToBeParsed);
        uncheckedFunction(datesToBeParsed);
        uncheckedSupplier(convertedDate);
    }

    private static Function<String, Date> dateConversionFunction() {
        return date -> {
            try {
                return parseDate(date);
            } catch (final ParseException e) {
                throw new IllegalArgumentException("Cannot parse '" + date + "'");
            }
        };
    }

    private static Date uncheckedConversion(final String date) {
        return Unchecked.function(it -> parseDate(it.toString()), exceptionHandler())
                        .apply(date);
    }

    private static CheckedFunction<String, Date> uncheckedConversion() {
        return date -> Unchecked.function(it -> parseDate(it.toString()), exceptionHandler())
                                .apply(date);
    }

    private static boolean uncheckedPredicate(final List<String> datesToBeParsed) {
        final Date today = new Date();
        return datesToBeParsed.stream()
                              .anyMatch(Unchecked.predicate(value -> parseDate(value).equals(today)));
    }

    private static void uncheckedConsumer(final List<String> datesToBeParsed) {
        final Consumer<String> consumer =
                Unchecked.consumer(it -> System.out.println(parseDate(it)), exceptionHandler());
        datesToBeParsed.forEach(consumer);
    }

    private static void uncheckedFunction(final List<String> datesToBeParsed) {
        final Set<Date> parsedDates = datesToBeParsed.stream()
                                                     .map(Unchecked.function(UncheckedMain::parseDate))
                                                     .collect(Collectors.toSet());
        parsedDates.forEach(System.out::println);
    }

    private static Date uncheckedSupplier(final String convertedDate) {
        return Unchecked.supplier(() -> parseDate(convertedDate), exceptionHandler())
                        .get();
    }

    private static Date parseDate(final String date) throws ParseException {
        return DATE_FORMAT.parse(date);
    }

    private static Consumer<Throwable> exceptionHandler() {
        return exception -> {
            final String exceptionMessage = exception.getMessage();
            System.err.println(exceptionMessage);

            throw new RuntimeException(exceptionMessage);
        };
    }
}
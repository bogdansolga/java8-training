package com.sg.java8.training.bi.functional.interfaces;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

/**
 * A few bi- functional interfaces usage samples
 */
public class BiFunctionalInterfacesMain {

    public static void main(String[] args) {
        biPredicatesSamples();

        biFunctionsSamples();

        biConsumersSamples();
    }

    private static void biPredicatesSamples() {
        final BiPredicate<String, Integer> biPredicate = (first, second) -> first.length() > 10 && second > 0;
        System.out.println(biPredicate.test("something", 5));
    }

    private static void biFunctionsSamples() {
        final BiFunction<String, Integer, String> biFunction = (string, integer) -> "The value of " + string + " and " + integer;
        System.out.println(biFunction.apply("another one", 20));

        // new methods added in the Map interface
        final Map<Integer, String> months = getMonths();
        months.forEach((key, value) -> System.out.println(key + " -> " + value)); // easier iteration

        months.compute(4, (key, value) -> "April");

        months.computeIfPresent(2, (key, value) -> "The shortest month");

        months.replaceAll((key, value) -> {
            if (key > 2) {
                return value.toUpperCase();
            } else {
                final String theNewValue = value + " added something";
                return theNewValue;
            }
        });
    }

    private static void biConsumersSamples() {
        final Map<Integer, String> months = getMonths();

        months.forEach((key, value) -> System.out.println(key + " -> " + value));
    }

    private static Map<Integer, String> getMonths() {
        final Map<Integer, String> months = new HashMap<>(5);

        months.put(1, "January");
        months.put(2, "February");
        months.put(3, "March");

        return months;
    }
}
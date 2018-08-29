package net.safedata.java8.training.bi.functional.interfaces;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
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
        final BiPredicate<String, Integer> biPredicate = (first, second) ->
                first.length() > 10 && second > 0; // whatever the business logic requires
        System.out.println(biPredicate.test("something", 5));
    }

    private static void biFunctionsSamples() {
        final BiFunction<String, Integer, String> biFunction =
                (first, second) -> "The value of " + first + " and " + second;
        System.out.println(biFunction.apply("another one", 20));

        // new methods added in the Map interface
        final Map<Integer, String> months = getMonths();

        months.compute(4, (key, value) -> "April or something else");

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

        // iteration - imperative mode
        final Set<Map.Entry<Integer, String>> entries = months.entrySet();
        for (Map.Entry<Integer, String> entry : entries) {
            processEntry(entry);
        }

        // iteration - functional mode
        months.forEach((key, value) -> System.out.println(key + " -> " + value)); // easier iteration
    }

    private static void processEntry(final Map.Entry<Integer, String> entry) {
        System.out.println(entry.getKey() + " -> " + entry.getValue());
    }

    private static Map<Integer, String> getMonths() {
        final Map<Integer, String> months = new HashMap<>();

        months.put(1, "January");
        months.put(2, "February");
        months.put(3, "March");

        return months;
    }
}
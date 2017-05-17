package com.sg.java8.training.integration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * A few examples of the differences between the Java 8 integration in various IDEs
 *
 * @author bogdan.solga
 */
public class Java8IDEsIntegration {

    private static final List<String> words = new ArrayList<>(Arrays.asList("I want a holiday, not just a weekend".split("\\s")));

    // Double Brace initialization - http://stackoverflow.com/questions/1958636/what-is-double-brace-initialization-in-java
    // http://blog.jooq.org/2014/12/08/dont-be-clever-the-double-curly-braces-anti-pattern/
    private static final Map<Integer, String> holidayLengths = new HashMap<Integer, String>() {{
        put( 3, "too short");
        put(10, "decent");
    }};

    private static final List<Integer> freeMonthNumbers = Arrays.asList(1, 5, 9);

    public static void main(String[] args) {
        collectionsUsage();

        comparatorUsage();

        streamCleanup();

        optionalUsage();

        mapUsage();
    }

    private static void collectionsUsage() {
        for (String word : words) {
            System.out.println(word);
        }

        final Iterator<String> iterator = words.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    private static void comparatorUsage() {
        words.sort(new Comparator<String>() {
            @Override
            public int compare(String first, String second) {
                return first.compareTo(second);
            }
        });
    }

    private static void streamCleanup() {
        words.stream().forEach(item -> System.out.println(item));

        words.removeIf(item -> item.isEmpty());

        freeMonthNumbers.stream().forEach(item -> {
            Optional.ofNullable(item).ifPresent(it -> System.out.println(it));
        });

        if (!freeMonthNumbers.stream()
                             .filter(monthId -> monthId > 10)
                             .findFirst()
                             .isPresent()) {
            System.out.println("Do operation on it");
        }
    }

    private static void optionalUsage() {
        words.stream().filter(item -> item.length() > 2).findAny().get();

        words.stream().max((first, second) -> first.compareTo(second)).get();
    }

    private static void mapUsage() {
        holidayLengths.computeIfAbsent(1, new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) {
                return "way too short";
            }
        });

        holidayLengths.forEach((key, value) -> System.out.println(key + " is " + value));
    }
}

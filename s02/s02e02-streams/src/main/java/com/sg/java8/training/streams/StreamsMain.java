package com.sg.java8.training.streams;

import com.sg.java8.training.bootstrap.StoreSetup;
import com.sg.java8.training.model.Product;
import com.sg.java8.training.model.Store;
import com.sg.java8.training.model.StoreSection;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * A few {@link java.util.stream.Stream}s usage samples
 */
public class StreamsMain {

    private static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();

    private static final List<String> STRINGS = Arrays.asList("I want a holiday, not just a weekend".split(" "));

    public static void main(String[] args) {
        simpleStreams();

        findOperations();

        reduceOperations();

        averageOnStrings();

        parallelStreams();

        simpleStreamsTests();

        streamOperations();

        flatMapOperations();

        collectorsSamples();

        numbersStreams();

        mapOperations();
    }

    private static void averageOnStrings() {
        final OptionalDouble average = STRINGS.stream()
                                              .mapToDouble(value -> value.length())
                                              .average();
        average.ifPresent(value -> System.out.println("The average words length is " + value));
    }

    private static void reduceOperations() {
        final Optional<String> reduce = STRINGS.stream()
                                               .reduce((primul, alDoilea) -> primul + "|" + alDoilea);
        reduce.ifPresent(value -> System.out.println("The reduced value is " + value));

        final String reduceWithIdentity = STRINGS.stream()
                                                 .reduce("{", (x, y) -> x + "*" + y);
        System.out.println(reduceWithIdentity);
    }

    private static void findOperations() {
        final Optional<String> first = STRINGS.stream()
                                              .filter(word -> word.length() > 5)
                                              .findFirst();
        first.ifPresent(value -> System.out.println(value));

        boolean hasLongerThan5CharWords = STRINGS.stream()
                                                 .anyMatch(word -> word.length() > 5);
        System.out.println("The text has words longer than 5 chars - " + hasLongerThan5CharWords);
    }

    private static void simpleStreams() {
        //STRINGS.stream().forEach(item -> System.out.println(item));

        Set<Integer> wordsLengths = STRINGS.stream()
                                           .map(value -> value.length()) // only if a conversion is needed
                                           .collect(Collectors.toSet());

        Set<Integer> wordsLongerThan3Chars = STRINGS.stream()
                                                    .filter(value -> value.length() > 3) // only if a filtering is needed
                                                    .map(value -> value.length())
                                                    .collect(Collectors.toSet());

        Set<String> words = STRINGS.stream()
                                   .sorted()
                                   .collect(Collectors.toSet());

        Set<List<char[]>> wordsLetters = STRINGS.stream()
                                                .map(value -> Arrays.asList(value.toCharArray()))
                                                .collect(Collectors.toSet());
        System.out.println(wordsLetters.size());
        wordsLetters.forEach(array -> array.forEach(value ->
                                            System.out.println(Arrays.toString(value) + ",")));

        Store store = StoreSetup.getDefaultStore();

        List<Product> allProducts = store.getStoreSections()
                                         .stream()
                                         .flatMap(section -> section.getProducts().stream())
                                         .collect(Collectors.toList());
    }

    private static void simpleStreamsTests() {
        //System.out.println(STRINGS);

        final Set<String> shortWords = STRINGS.stream()
                                              .filter(word -> word.length() < 5)
                                              .collect(Collectors.toSet());
        //System.out.println(shortWords);

        final List<Integer> wordsLengths = STRINGS.stream()
                                                  .filter(word -> word.length() < 7) // 1st pipeline stage
                                                  .map(word -> word.length())        // 2nd pipeline stage
                                                  .collect(Collectors.toList());     // terminal operation
        System.out.println(wordsLengths);
    }

    private static void streamOperations() {
        final List<String> holiday = Arrays.asList("I want a STRINGS, not just a weekend".split(" "));

        final Predicate<String> longWordPredicate = word -> word.length() > 5;
        final Optional<String> longWord = holiday.stream()
                                                 .filter(longWordPredicate)
                                                 .findFirst();

        final boolean containsLongWords = holiday.stream()
                                                 .anyMatch(longWordPredicate);

        final TreeSet<String> distinctWords = holiday.stream()
                                                     .distinct()
                                                     .collect(Collectors.toCollection(TreeSet::new));
        //System.out.println(distinctWords);

        final String reAssembledString = holiday.stream()
                                                .reduce("", (first, second) -> first + " " + second);
        System.out.println(reAssembledString.trim());
    }

    private static void flatMapOperations() {
        final Store store = StoreSetup.getDefaultStore();
        final Set<Product> tablets = store.getStoreSections()
                                          .stream()
                                          .filter(section -> section.getName().equals(StoreSection.Tablets))
                                          .flatMap(section -> section.getProducts().stream())
                                          .filter(product -> product.getName().contains("Apple"))
                                          .collect(Collectors.toSet());

        tablets.forEach(tablet -> System.out.println(tablet.getName()));

        final Stream<List<String>> listStream = Stream.of(Arrays.asList("some default values".split(" ")));
        final Set<String> collect = listStream.flatMap(Collection::stream)
                                              .collect(Collectors.toSet());
        System.out.println(collect);
    }

    private static void parallelStreams() {
        STRINGS.parallelStream()
               .forEach(item -> System.out.println(Thread.currentThread().getName() + ": " + item));

        final ForkJoinPool forkJoinPool = new ForkJoinPool(AVAILABLE_PROCESSORS / 2);
        final ForkJoinTask<?> forkJoinTask = forkJoinPool.submit(() ->
                                                                         System.out.println(Thread.currentThread().getName() + " - something"));
        if (forkJoinTask.isDone()) {
            try {
                System.out.println(forkJoinTask.get());
            } catch (final ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void collectorsSamples() {
        final Map<String, Integer> wordsLength = STRINGS.stream()
                                                        .distinct()
                                                        .collect(Collectors.toMap(value -> value, String::length));
        System.out.println(wordsLength);

        final Map<String, Long> collect = STRINGS.stream()
                                                 .collect(Collectors.groupingBy(
                                                         Function.identity(), Collectors.counting()));
        System.out.println(collect);
    }

    private static void numbersStreams() {
        final long sum = LongStream.range(0L, 50L)
                                   .sum();
        System.out.println("Sum of first 50 numbers is " + sum);

        // for (int i = 0; i <= 100; i++) {}
        IntStream.rangeClosed(0, 100)
                 .forEach(value -> System.out.println("->" + value));
    }

    private static void mapOperations() {
        final Map<String, Integer> wordsLength = STRINGS.stream()
                                                        .distinct()
                                                        .collect(Collectors.toMap(value -> value, String::length));
        wordsLength.putIfAbsent("something", 10);

        final Map<Integer, String> months = new HashMap<>();
        months.put(1, "Jan");
        months.put(2, "Feb");
    }
}
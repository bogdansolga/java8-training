package com.sg.java8.training.streams;

import com.sg.java8.training.bootstrap.StoreSetup;
import com.sg.java8.training.model.Product;
import com.sg.java8.training.model.Section;
import com.sg.java8.training.model.Store;
import com.sg.java8.training.model.StoreSection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * A few {@link java.util.stream.Stream}s usage samples
 */
@SuppressWarnings("unused")
public class StreamsMain {

    private static final long BYTES_IN_MB = 1048576;

    private static final List<String> STRINGS = Arrays.asList("I want a holiday, not just a weekend".split(" "));

    public static void main(String[] args) {
        simpleStreams();

        loopFusionTest();

        findOperations();

        reduceOperations();

        averageOnStrings();

        parallelStreams();

        streamOperations();

        flatMapOperations();

        collectorsSamples();

        numbersStreams();

        mapOperations();

        streamDebuggerTest();
    }

    private static void simpleStreams() {
        Set<Integer> wordsLengths = STRINGS.stream()
                                           .map(value -> value.length()) // only if a conversion is needed
                                           .collect(Collectors.toSet());
        System.out.println(wordsLengths);

        Set<Integer> wordsLongerThan3Chars = STRINGS.stream()
                                                    .filter(value -> value.length() > 3) // only if a filtering is needed
                                                    .map(value -> value.length())
                                                    .collect(Collectors.toSet());
        System.out.println(wordsLongerThan3Chars);

        Set<String> words = STRINGS.stream()
                                   .sorted()
                                   .collect(Collectors.toCollection(TreeSet::new));
        System.out.println(words);

        Set<List<char[]>> wordsLetters = STRINGS.stream()
                                                .map(value -> Arrays.asList(value.toCharArray()))
                                                .collect(Collectors.toSet());
        System.out.println(wordsLetters.size());
        wordsLetters.forEach(array -> array.forEach(value ->
                System.out.println(Arrays.toString(value) + ",")));
    }

    private static void loopFusionTest() {
        System.out.println("There are " + STRINGS.size() + " words to be streamed");
        final Optional<Integer> first = STRINGS.stream()
                                               .peek(value -> System.out.println("m: " + value))
                                               .map(value -> value.length())
                                               .peek(value -> System.out.println("f: " + value))
                                               .filter(value -> value > 2)
                                               .peek(value -> System.out.println("a: " + value))
                                               .findFirst();
        first.ifPresent(value -> System.out.println("The value is " + value));
    }

    private static void averageOnStrings() {
        final OptionalDouble average = STRINGS.stream()
                                              .mapToDouble(value -> value.length())
                                              .average();
        average.ifPresent(value -> System.out.println("The average words length is " + value));
    }

    private static void reduceOperations() {
        final Optional<String> reduce = STRINGS.stream()
                                               .reduce((first, second) -> first + "|" + second);
        reduce.ifPresent(value -> System.out.println("The reduced value is " + value));

        final String reduceWithIdentity = STRINGS.stream()
                                                 .reduce("{", (x, y) -> x + "," + y);

        IntStream.rangeClosed(0, 50)
                 .sum();

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

    private static void streamOperations() {
        final Predicate<String> longWordPredicate = word -> word.length() > 5;
        final Optional<String> longWord = STRINGS.stream()
                                                 .filter(longWordPredicate)
                                                 .findFirst();
        longWord.ifPresent(value -> System.out.println(value));
    }

    private static void flatMapOperations() {
        final Store store = StoreSetup.getDefaultStore();
        final Set<Product> tablets = store.getStoreSections()
                                          .stream()
                                          .filter(section -> section.getName().equals(StoreSection.Tablets))
                                          .flatMap(section -> section.getProducts()
                                                                     .orElse(new ArrayList<>())
                                                                     .stream())
                                          .filter(product -> product.getName().contains("Apple"))
                                          .collect(Collectors.toSet());

        System.out.println("We have " + tablets.size() + " products");
        tablets.forEach(tablet -> System.out.println("\t" + tablet.getName()));

        final Stream<List<String>> listStream = Stream.of(Arrays.asList("some default values".split(" ")));
        final Set<String> collect = listStream.flatMap(Collection::stream)
                                              .collect(Collectors.toSet());
        System.out.println(collect);
    }

    private static void parallelStreams() {
        final int availableProcessors = Runtime.getRuntime().availableProcessors();

        final String value = Integer.toString(availableProcessors / 4);
        System.out.println("Running with " + value + " cores");

        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", value);

        STRINGS.parallelStream()
               .forEach(item -> System.out.println(Thread.currentThread().getName() + ": " + item));

        Stream<String> dynamicParallelStream =
                StreamSupport.stream(STRINGS.spliterator(), STRINGS.size() > 100);
    }

    private static void collectorsSamples() {
        final Map<String, Integer> wordsLength = STRINGS.stream()
                                                        .distinct()
                                                        .collect(Collectors.toMap(Function.identity(), String::length));
        System.out.println(wordsLength);

        final Map<String, Long> collect = STRINGS.stream()
                                                 .collect(Collectors.groupingBy(
                                                         Function.identity(), Collectors.counting()));
        System.out.println(collect);

        final TreeSet<String> distinctWords = STRINGS.stream()
                                                     .collect(Collectors.toCollection(TreeSet::new));
        System.out.println("The distinct words are " + distinctWords);
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
        // three ways to apply streams on maps
        wordsLength.keySet().stream();
        wordsLength.values().stream();
        wordsLength.entrySet().stream();
    }

    private static void streamDebuggerTest() {
        Store store = StoreSetup.getDefaultStore();
        List<Product> allProducts = store.getStoreSections()
                                         .stream()
                                         .filter(section -> section.getId() > 2)
                                         .flatMap(section -> section.getProducts()
                                                                    .orElse(new ArrayList<>())
                                                                    .stream())
                                         .collect(Collectors.toList());
        System.out.println(allProducts);
    }
}
package com.sg.java8.training.streams;

import com.sg.java8.training.bootstrap.StoreSetup;
import com.sg.java8.training.model.Product;
import com.sg.java8.training.model.Store;
import com.sg.java8.training.model.StoreSection;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * A few {@link java.util.stream.Stream}s usage samples
 */
public class StreamsMain {

    private static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();

    private static final List<String> STRINGS = Arrays.asList("I want a holiday, not just a weekend".split(" "));

    public static void main(String[] args) {
        //simpleStreamsTests();

        //streamOperations();

        //flatMapOperations();

        //parallelStreams();

        //collectorsSamples();

        //numbersStreams();

        mapOperations();
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
        //System.out.println(wordsLength);

        final Map<String, Long> collect = STRINGS.stream()
                                                 .collect(Collectors.groupingBy(
                                                         Function.identity(), Collectors.counting()));
        System.out.println(collect);
    }

    private static void numbersStreams() {
        final long sum = LongStream.range(0L, 50L)
                                   .sum();
        System.out.println("Sum of first 50 numbers is " + sum);
    }

    private static void mapOperations() {
        final Map<String, Integer> wordsLength = STRINGS.stream()
                                                        .distinct()
                                                        .collect(Collectors.toMap(value -> value, String::length));
        wordsLength.putIfAbsent("something", 10);
    }
}
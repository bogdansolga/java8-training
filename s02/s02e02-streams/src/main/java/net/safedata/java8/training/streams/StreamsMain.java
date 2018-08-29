package net.safedata.java8.training.streams;

import net.safedata.java8.training.bootstrap.StoreSetup;
import net.safedata.java8.training.model.Product;
import net.safedata.java8.training.model.Store;
import net.safedata.java8.training.model.StoreSection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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

    private static final List<String> DAILY_WISH = Arrays.asList("I want a holiday, not just a weekend".split(" "));

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
        Set<Integer> wordsLengths = DAILY_WISH.stream()
                                              .map(value -> value.length()) // only if a conversion is needed
                                              .collect(Collectors.toSet());
        System.out.println(wordsLengths);

        Set<Integer> wordsLongerThan3Chars = DAILY_WISH.stream()
                                                       .filter(value -> value.length() > 3) // only if a filtering is needed
                                                       .map(value -> value.length())
                                                       .collect(Collectors.toSet());
        System.out.println(wordsLongerThan3Chars);

        Set<String> words = DAILY_WISH.stream()
                                      .sorted()
                                      .collect(Collectors.toCollection(TreeSet::new));
        System.out.println(words);

        Set<List<char[]>> wordsLetters = DAILY_WISH.stream()
                                                   .map(value -> Arrays.asList(value.toCharArray()))
                                                   .collect(Collectors.toSet());
        System.out.println(wordsLetters.size());
        wordsLetters.forEach(array -> array.forEach(value ->
                System.out.println(Arrays.toString(value) + ",")));
    }

    private static void loopFusionTest() {
        System.out.println("There are " + DAILY_WISH.size() + " words to be streamed");
        final Optional<Integer> first = DAILY_WISH.stream()
                                                  .peek(value -> System.out.println("m: " + value))
                                                  .map(value -> value.length())
                                                  .peek(value -> System.out.println("f: " + value))
                                                  .filter(value -> value > 2)
                                                  .peek(value -> System.out.println("a: " + value))
                                                  .findFirst();
        first.ifPresent(value -> System.out.println("The value is " + value));
    }

    private static void averageOnStrings() {
        final OptionalDouble average = DAILY_WISH.stream()
                                                 .mapToDouble(value -> value.length())
                                                 .average();
        average.ifPresent(value -> System.out.println("The average words length is " + value));
    }

    private static void reduceOperations() {
        final Optional<String> reduce = DAILY_WISH.stream()
                                                  .reduce((first, second) -> first + "|" + second);
        reduce.ifPresent(value -> System.out.println("The reduced value is " + value));

        final String reduceWithIdentity = DAILY_WISH.stream()
                                                    .reduce("{", (x, y) -> x + "," + y);

        IntStream.rangeClosed(0, 50)
                 .sum();

        System.out.println(reduceWithIdentity);
    }

    private static void findOperations() {
        final Optional<String> first = DAILY_WISH.stream()
                                                 .filter(word -> word.length() > 5)
                                                 .findFirst();
        first.ifPresent(value -> System.out.println(value));

        boolean hasLongerThan5CharWords = DAILY_WISH.stream()
                                                    .anyMatch(word -> word.length() > 5);
        System.out.println("The text has words longer than 5 chars - " + hasLongerThan5CharWords);
    }

    private static void streamOperations() {
        final Predicate<String> longWordPredicate = word -> word.length() > 5;
        final Optional<String> longWord = DAILY_WISH.stream()
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

        final String usedCores = Integer.toString(availableProcessors / 2);
        System.out.println("Running with " + usedCores + " cores");

        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", usedCores);

        DAILY_WISH.parallelStream()
                  .forEach(item -> System.out.println(Thread.currentThread().getName() + ": " + item));

        Stream<String> dynamicParallelStream =
                StreamSupport.stream(DAILY_WISH.spliterator(), DAILY_WISH.size() > 100);
    }

    private static void collectorsSamples() {
        final Map<String, Integer> wordsLength = DAILY_WISH.stream()
                                                           .distinct()
                                                           .collect(Collectors.toMap(Function.identity(), String::length));
        System.out.println(wordsLength);

        final Map<String, Long> collect = DAILY_WISH.stream()
                                                    .collect(Collectors.groupingBy(
                                                         Function.identity(), Collectors.counting()));
        System.out.println(collect);

        final TreeSet<String> distinctWords = DAILY_WISH.stream()
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

        IntStream.of(20, 30, 50, 20)
                 .filter(value -> value > 30)
                 .forEach(value -> System.out.println(value));
    }

    private static void mapOperations() {
        final Map<String, Integer> wordsLength = DAILY_WISH.stream()
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
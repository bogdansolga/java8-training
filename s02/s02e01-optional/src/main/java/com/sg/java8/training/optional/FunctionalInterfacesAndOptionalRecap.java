package com.sg.java8.training.optional;

import java.util.Optional;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class FunctionalInterfacesAndOptionalRecap {

    @SuppressWarnings("unused")
    public static void main(String[] args) {
        functionalInterfaces();

        optional();
    }

    private static void functionalInterfaces() {
        // 1 - Predicate
        Predicate<String> isNullOrEmpty = value -> value == null || value.isEmpty();
        System.out.println(isNullOrEmpty.test("some"));

        Predicate<Integer> isEven = value -> value % 2 == 0;
        System.out.println(isEven.test(20));

        // 2 - Consumer
        Consumer<String> displayer = System.out::println;
        //Consumer<String> displayer = value -> System.out.println(value);
        displayer.accept("something");

        // 3 - Function
        Function<String, Integer> stringLength = value -> value.length();
        System.out.println(stringLength.apply("orice cuvânt"));

        // 4 - Supplier
        Supplier<Integer> randomIntSupplier = () -> new Random().nextInt();
        System.out.println(randomIntSupplier.get());

        Supplier<RuntimeException> exSupplier = () ->
                new IllegalArgumentException("Some exception");
        System.out.println(exSupplier.get().getMessage());
    }

    private static void optional() {
        // Optional
        String nullableValue = System.currentTimeMillis() % 2 == 0 ? "some" : null;
        Optional<String> optional = Optional.ofNullable(nullableValue);

        // 1 - .map + .orElse / .orElseGet: using a Function + a Supplier
        int lengthOrZero = optional.map(value -> value.length())
                                   .orElseGet(() -> new Random().nextInt()); //.orElse(0);
        System.out.println(lengthOrZero);

        // 2 - .map + .orElseThrow: using a Function + a Supplier
        int lengthOrThrow = optional.map(String::length) // method reference
                                    .orElseThrow(() -> new IllegalArgumentException("Nope"));
        System.out.println(lengthOrThrow);

        // 3 - .ifPresent: using a Consumer
        optional.ifPresent(value -> System.out.println("Do something with " + value));

        // 4 - .isPresent + .get
        if (optional.isPresent()) {
            String unWrapped = optional.get();
            // + use unWrapped
        }

        // 5 - .flatMap
        Optional<Integer> optionalLength = optional.flatMap(value -> Optional.of(value.length()));
        optionalLength.ifPresent(it -> System.out.println("The length is " + it));
    }
}

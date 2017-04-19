package com.sg.java8.training.optional;

import java.util.Optional;

/**
 * A few {@link java.util.Optional} usage samples
 */
public class OptionalMain {

    public static void main(String[] args) {
        final String nullable = "8"; // in a real use-case, it would be a value obtained from some other processing

        final int parsedValue = Optional.ofNullable(nullable)
                                        .map(Integer::parseInt)
                                        .orElse(0);

        final int imperativeValue;
        if (nullable != null) {
            imperativeValue = Integer.parseInt(nullable);
        } else {
            imperativeValue = 0;
        }

        // recommended way of using Optional
        final int parsedPositiveValue = Optional.ofNullable(nullable)
                                                .map(Integer::parseInt)
                                                .orElseThrow(() -> new IllegalArgumentException("Cannot parse"));

        final String nullableValue = System.currentTimeMillis() % 2 == 0 ? null : "some value";
        final Optional<String> optionalString = Optional.ofNullable(nullableValue);

        // two ways of using the optional wrapped value

        // 1: use the .isPresent & .get methods
        if (optionalString.isPresent()) {
            final String wrappedValue = optionalString.get();
            // use the unwrapped value
        }

        // 2: apply a Consumer on the wrapped value
        optionalString.ifPresent(value -> System.out.println(value));

        Optional<Integer> integerOptional = Optional.empty();
        System.out.println(integerOptional.get()); // throws a NoSuchElementException
    }
}
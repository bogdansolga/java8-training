package com.sg.java8.training.optional;

import com.sg.java8.training.bootstrap.StoreSetup;
import com.sg.java8.training.model.Store;

import java.util.Optional;

public class OptionalSamples {

    public static void main(String[] args) {
        simpleOptionalSamples();

        productOptionals();
    }

    private static void productOptionals() {
        Store store = Optional.ofNullable(StoreSetup.getDefaultStore())
                              .orElseThrow(() -> new IllegalArgumentException("No store is found"));
    }

    private static void simpleOptionalSamples() {
        final String someValue = null; // consider it returned by a method call

        final int mandatoryValue = Optional.ofNullable(someValue)
                                           .map(Integer::parseInt)
                                           .orElseThrow(() -> new IllegalArgumentException("Cannot use a null value"));

        System.out.println(mandatoryValue);

        final int alternateValue = Optional.ofNullable(someValue)
                                           .map(Integer::parseInt)
                                           .orElse(50);
        System.out.println(alternateValue);

        Optional<String> optional = Optional.ofNullable("some random value");
        optional.ifPresent(value -> process(value));

        if (optional.isPresent()) {
            String wrappedValue = optional.get();
        }
    }

    private static void process(String value) {

    }
}

package com.sg.java8.training.streams;

import com.sg.java8.training.model.Product;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SortingSamples {

    public static void main(String[] args) {

        final List<String> strings = Arrays.asList("some", "random", null,
                                                   "strings", "and", null,
                                                   "values");

        final List<String> notNullValues = strings.stream()
                                                  .filter(Objects::nonNull)
                                                  .sorted()
                                                  .collect(Collectors.toList());
        System.out.println(notNullValues);

        final List<Product> products = Arrays.asList(
                new Product(10, "Tablet", 50),
                new Product(20, "Monitor", 600)
        );

        products.stream()
                .sorted(Comparator.comparing(Product::getName))
                .forEach(System.out::println);
    }
}

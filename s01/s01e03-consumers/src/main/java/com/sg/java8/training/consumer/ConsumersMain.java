package com.sg.java8.training.consumer;

import com.sg.java8.training.consumer.service.ProductService;
import com.sg.java8.training.model.Product;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * A few {@link java.util.function.Consumer} usage samples
 */
public class ConsumersMain {

    public static void main(String[] args) {
        simpleConsumers();

        productConsumers();

        sectionConsumers();

        managerConsumers();
    }

    private static void simpleConsumers() {
        final Consumer<Integer> integerConsumer = System.out::println;
        integerConsumer.accept(25);

        final Consumer<String> display = value -> System.out.println(value);
        display.accept("something");

        final List<String> list = Arrays.asList("I want a holiday, not just a weekend".split(" "));

        // imperative way
        for (final String item : list) {
            System.out.println(item);
        }

        // functional way
        list.forEach(System.out::println);
        list.forEach(item -> System.out.println(item)); // just a matter of taste :)

        final Consumer<Boolean> booleanConsumer = it -> System.out.println("The boolean value is " + Boolean.valueOf(it));
        booleanConsumer.accept(Boolean.TRUE);

        final Consumer<String> aMoreComplexConsumer = value -> {
            if (value.isEmpty()) {
                System.err.println("Cannot process an empty value");
            } else {
                System.out.println("The received value is " + value);
            }
        };

        final Consumer<String> displayedRecommendationConsumer = value -> {
            System.out.println("The value is " + value);
        };

        // TODO using Consumers as methods and with more than one statements

        // TODO iterating over the values of a Collection

        // TODO try other simple consumers - String, Boolean, ...
    }

    private static void productConsumers() {
        final Product product = new Product(10, "iSomething", 500);
        final Consumer<Product> productConsumer = it ->
                System.out.println("The selected product is " + product);
        productConsumer.accept(product);

        final Consumer<Product> preProcessor = product1 -> System.out.println("Pre-processing the product...");
        preProcessor.andThen(productConsumer)
                    .accept(product);

        final ProductService productService = new ProductService();
        productService.displayAppleTablets();

        // TODO try other Product consumers
    }

    private static void sectionConsumers() {
        // TODO display all the sections which have more than 3 products
    }

    private static void managerConsumers() {
        // TODO display the sections and their manager(s)
    }
}
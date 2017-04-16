package com.sg.java8.training.consumer;

import com.sg.java8.training.model.Product;
import com.sg.java8.training.consumer.service.ProductService;

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
        final Consumer<Integer> integerConsumer = number -> System.out.println(number);
        integerConsumer.accept(25);

        // TODO try other simple consumers - String, Boolean, ...
    }

    private static void productConsumers() {
        final Product product = new Product(10, "iSomething", 500);
        final Consumer<Product> productConsumer = it -> System.out.println("The selected product is " + product);
        productConsumer.accept(product);

        final ProductService productService = new ProductService();
        productService.displayAppleTablets();

        // TODO try other Product consumers
    }

    private static void sectionConsumers() {
        // TODO try a few Section consumers
    }

    private static void managerConsumers() {
        // TODO try a few Manager consumers
    }
}
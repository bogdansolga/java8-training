package com.sg.java8.training.consumer.service;

import com.sg.java8.training.model.Product;
import com.sg.java8.training.model.Section;
import com.sg.java8.training.bootstrap.StoreSetup;
import com.sg.java8.training.model.StoreSection;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * A simple service for managing {@link Product} entities
 *
 * @author bogdan.solga
 */
public class ProductService {

    public void displayAppleTablets() {
        final Section tablets = getTabletsSection();
        processAppleTablets(tablets);
    }

    private Section getTabletsSection() {
        return StoreSetup.getDefaultStore()
                         .getStoreSections()
                         .stream()
                         .filter(tablets())
                         .findFirst()
                         .orElseThrow(() -> new IllegalArgumentException("There's no section named 'Tablets'"));
    }

    private void processAppleTablets(final Section tablets) {
        final List<Product> products = tablets.getProducts()
                                              .orElseThrow(() -> new IllegalArgumentException("There are no products"));

        /*
            Q:  when to use an inline lambda expression and when to use methods?
            A:  • use a method:
                    - if the lambda expression needs to be a statement lambda
                        and / or
                    - if it needs to be reused
                • usa an inline lambda - if the lambda expression can be written on a single line
        */

        // Single Responsibility Principle / Separation of Concerns
        products.stream()                       // 0 - obtaining a stream from the collection
                .filter(appleProducts())        // 1 - filtering stage
                .forEach(consumeProduct());     // 2 - processing / consuming stage
    }

    private Consumer<Product> consumeProduct() {
        return System.out::println;
    }

    private Predicate<Product> appleProducts() {
        return product -> product.getName().contains("Apple");
    }

    private Predicate<Section> tablets() {
        return section -> section.getName().equals(StoreSection.Tablets);
    }
}

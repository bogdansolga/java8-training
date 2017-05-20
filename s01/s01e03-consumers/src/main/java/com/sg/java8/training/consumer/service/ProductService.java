package com.sg.java8.training.consumer.service;

import com.sg.java8.training.model.Product;
import com.sg.java8.training.model.Section;
import com.sg.java8.training.bootstrap.StoreSetup;
import com.sg.java8.training.model.StoreSection;

import java.util.List;
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

    private void processAppleTablets(final Section tablets) {
        final List<Product> products = tablets.getProducts()
                                              .orElseThrow(() -> new IllegalArgumentException("There are no products"));

        products.stream()
                .filter(appleProducts())        // 1 - filtering stage
                .forEach(System.out::println);  // 2 - processing / consuming stage
    }

    private Section getTabletsSection() {
        return StoreSetup.getDefaultStore()
                         .getStoreSections()
                         .stream()
                         .filter(tablets())
                         .findFirst()
                         .orElseThrow(() -> new IllegalArgumentException("There's no section named 'Tablets'"));
    }

    private Predicate<Product> appleProducts() {
        return product -> product.getName().contains("Apple");
    }

    private Predicate<Section> tablets() {
        return section -> section.getName().equals(StoreSection.Tablets);
    }
}

package com.sg.java8.training.function.service;

import com.sg.java8.training.model.Product;
import com.sg.java8.training.model.Section;
import com.sg.java8.training.bootstrap.StoreSetup;
import com.sg.java8.training.model.StoreSection;

import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * A simple service for managing {@link Product} entities
 *
 * @author bogdan.solga
 */
public class ProductService {

    public Set<String> getSamsungTabletDescriptions() {
        final Section tablets = StoreSetup.getDefaultStore()
                                          .getStoreSections()
                                          .stream()
                                          .filter(section -> section.getName().equals(StoreSection.Tablets))
                                          .findFirst()
                                          .orElseThrow(() -> new IllegalArgumentException("There's no section named 'Tablets'"));

        return tablets.getProducts()
                      .stream()
                      .filter(productFilter())
                      .map(productMapper())
                      .collect(Collectors.toSet());
    }

    private Predicate<Product> productFilter() {
        return product -> product.getName().contains("Samsung");
    }

    private Function<Product, String> productMapper() {
        return product -> product.toString() + " another something";
    }
}

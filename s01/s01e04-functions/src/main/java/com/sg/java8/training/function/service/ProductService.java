package com.sg.java8.training.function.service;

import com.sg.java8.training.model.Product;
import com.sg.java8.training.model.Section;
import com.sg.java8.training.bootstrap.StoreSetup;
import com.sg.java8.training.model.StoreSection;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
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
        final Section tabletsSection = StoreSetup.getDefaultStore()
                                                 .getStoreSections()
                                                 .stream()
                                                 .filter(section -> section.getName().equals(StoreSection.Tablets))
                                                 .findFirst()
                                                 .orElseThrow(() -> new IllegalArgumentException("There's no section named 'Tablets'"));

        final List<Product> tablets = tabletsSection.getProducts()
                                                    .orElseThrow(() -> new IllegalArgumentException("There are no products"));

        return tablets.stream()
                      .filter(samsungProductsFilter())
                      .map(convertProductToString())
                      .peek(traceItem())
                      .collect(Collectors.toSet());
    }

    private Consumer<String> traceItem() {
        return System.out::println;
    }

    private Predicate<Product> samsungProductsFilter() {
        return product -> product.getName().contains("Samsung");
    }

    private Function<Product, String> convertProductToString() {
        return product -> "The product is: " + product.toString();
    }
}

package com.sg.java8.training.predicate.service;

import com.sg.java8.training.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A simple service for managing {@link Product} entities
 *
 * @author bogdan.solga
 */
public class ProductService {

    private static final List<Product> PRODUCTS = new ArrayList<>();

    static {
        PRODUCTS.add(new Product(1, "Nexus 7 2012", 200));
        PRODUCTS.add(new Product(2, "Nexus 7 2013", 300));
        PRODUCTS.add(new Product(3, "Nexus 10 2013", 350));
        PRODUCTS.add(new Product(4, "Nexus 7 2016", 400));

        PRODUCTS.add(new Product(5, "Samsung S2", 340));
        PRODUCTS.add(new Product(6, "Samsung S6", 500));
    }

    public List<Product> getNexusTablets() {
        return PRODUCTS.stream()
                       .filter(product -> product.getName().toLowerCase().contains("nexus"))
                       .collect(Collectors.toList());
    }
}

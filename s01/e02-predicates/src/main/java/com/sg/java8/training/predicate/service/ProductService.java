package com.sg.java8.training.predicate.service;

import com.sg.java8.training.model.Product;
import com.sg.java8.training.model.Section;
import com.sg.java8.training.bootstrap.StoreSetup;

import java.util.List;
import java.util.stream.Collectors;

/**
 * A simple service for managing {@link Product} entities
 *
 * @author bogdan.solga
 */
public class ProductService {

    public List<Product> getNexusTablets() {
        final Section tablets = StoreSetup.getDefaultStore()
                                          .getStoreSections()
                                          .stream()
                                          .filter(section -> section.getSectionName().equals("Tablets"))
                                          .findFirst()
                                          .orElseThrow(() -> new IllegalArgumentException("There's no section named 'Tablets'"));

        return tablets.getProducts()
                      .stream()
                      .filter(product -> product.getName().toLowerCase().contains("nexus"))
                      .collect(Collectors.toList());
    }
}

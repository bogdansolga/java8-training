package com.sg.java8.training.predicate.service;

import com.sg.java8.training.model.Product;
import com.sg.java8.training.model.Section;
import com.sg.java8.training.bootstrap.StoreSetup;
import com.sg.java8.training.model.StoreSection;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * A simple service for managing {@link Product} entities
 *
 * @author bogdan.solga
 */
public class ProductService {

    public List<Product> getNexusTablets() {
        final Section tablets = getTablets();

        final List<Product> products = getProductsOrThrow(tablets);

        return getNexusTablets(products);
    }

    private Section getTablets() {
        return StoreSetup.getDefaultStore()
                         .getStoreSections()
                         .stream()
                         .filter(tabletsSection())
                         .findFirst()
                         .orElseThrow(() -> new IllegalArgumentException("There's no section named 'Tablets'"));
    }

    private List<Product> getProductsOrThrow(final Section tablets) {
        return tablets.getProducts()
                      .orElseThrow(() -> new IllegalArgumentException("There are no available tablets"));
    }

    private List<Product> getNexusTablets(final List<Product> products) {
        return products.stream()
                       .filter(product -> product.getName().toLowerCase().contains("nexus"))
                       .collect(Collectors.toList());
    }

    private Predicate<Section> tabletsSection() {
        return section -> section.getName().equals(StoreSection.Tablets);
    }
}

package com.socgen.java8.training.predicate;

import com.socgen.java8.training.model.Product;
import com.socgen.java8.training.predicate.service.ProductService;

import java.util.List;
import java.util.function.Predicate;

/**
 * A few {@link java.util.function.Predicate} usage samples
 */
public class PredicatesMain {

    public static void main(String[] args) {
        simplePredicates();

        productPredicates();
    }

    private static void simplePredicates() {
        final Predicate<Integer> isEven = number -> number %2 == 0;
        System.out.println(isEven.test(7));
        System.out.println(isEven.test(8));
    }

    private static void productPredicates() {
        final Product product = new Product(10, "iSomething", 500);
        final Predicate<Product> hasAppleBranding = it -> it.getName().startsWith("i");
        System.out.println(hasAppleBranding.test(product));

        final ProductService productService = new ProductService();
        final List<Product> products = productService.getNexusTablets();
        System.out.println("There are " + products.size() + " Nexus tablets");
    }
}
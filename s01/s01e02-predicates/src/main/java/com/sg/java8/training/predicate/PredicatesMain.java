package com.sg.java8.training.predicate;

import com.sg.java8.training.model.Product;
import com.sg.java8.training.predicate.service.ProductService;

import java.util.List;
import java.util.function.Predicate;

/**
 * A few {@link java.util.function.Predicate} usage samples
 */
public class PredicatesMain {

    public static void main(String[] args) {
        simplePredicates();

        productPredicates();

        sectionPredicates();

        managerPredicates();
    }

    private static void simplePredicates() {
        final Predicate<Integer> isEven = number -> number %2 == 0;
        final Predicate<Integer> isBiggerThan100 = number -> number > 100;

        System.out.println(isEven.test(7));
        System.out.println(isEven.test(8));

        System.out.println(isEven.and(isBiggerThan100).test(130));

        // TODO remove the elements of a collection - imperative and functional

        // TODO using Predicates as methods and with more than one statements

        // TODO try other simple predicates - Integer, String, ...
    }

    private static void productPredicates() {
        final Product product = new Product(10, "iSomething", 500);
        final Predicate<Product> hasAppleBranding = it -> it.getName().startsWith("i");
        System.out.println(hasAppleBranding.test(product));

        final ProductService productService = new ProductService();
        final List<Product> products = productService.getNexusTablets();
        System.out.println("There are " + products.size() + " Nexus tablets");

        // TODO try other Product predicates
    }

    private static void sectionPredicates() {
        // TODO try a few Section predicates
    }

    private static void managerPredicates() {
        // TODO try a few Manager predicates
    }
}
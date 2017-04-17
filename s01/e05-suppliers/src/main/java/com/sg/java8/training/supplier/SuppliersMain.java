package com.sg.java8.training.supplier;

import com.sg.java8.training.model.Product;
import com.sg.java8.training.supplier.service.ProductService;

import java.util.Random;
import java.util.function.Supplier;

/**
 * A few {@link java.util.function.Supplier} usage samples
 */
public class SuppliersMain {

    public static void main(String[] args) {
        simpleSuppliers();

        productSuppliers();

        sectionSuppliers();

        managerSuppliers();
    }

    private static void simpleSuppliers() {
        final Supplier<Double> doubleSupplier = () -> new Random(1000).nextDouble();
        System.out.println(doubleSupplier.get());

        final Supplier<String> holidaySupplier = () -> "I want a holiday, not just a weekend :)";
        System.out.println(holidaySupplier.get());

        // TODO try other simple Suppliers - String, Boolean, ...
    }

    private static void productSuppliers() {
        final ProductService productService = new ProductService();

        final Product product = productService.generateRandomProduct().get();
        System.out.println(product);

        // TODO try other Product Suppliers
    }

    private static void sectionSuppliers() {
        // TODO try a few Section Suppliers
    }

    private static void managerSuppliers() {
        // TODO try a few Manager Suppliers
    }
}
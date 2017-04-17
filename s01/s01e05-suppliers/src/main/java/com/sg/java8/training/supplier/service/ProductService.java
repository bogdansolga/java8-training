package com.sg.java8.training.supplier.service;

import com.sg.java8.training.model.Product;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;
import java.util.function.Supplier;

/**
 * A simple service for managing {@link Product} entities
 *
 * @author bogdan.solga
 */
public class ProductService {

    private static final Random RANDOM = new Random(8000);

    private static final NumberFormat NUMBER_FORMAT = new DecimalFormat("##.##");

    public Supplier<Product> generateRandomProduct() {
        return () -> {
            try {
                return new Product(RANDOM.nextInt(50), "A fancy little product",
                                   Double.parseDouble(NUMBER_FORMAT.format(RANDOM.nextDouble() * 100)));
            } catch (final NumberFormatException e) {
                throw new IllegalArgumentException("Invalid number");
            }
        };
    }
}

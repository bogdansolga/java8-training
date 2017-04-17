package com.sg.java8.training.function;

import com.sg.java8.training.model.Product;
import com.sg.java8.training.function.service.ProductService;

import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A few {@link java.util.function.Function} usage samples
 */
public class FunctionsMain {

    public static void main(String[] args) {
        simpleFunctions();

        productFunctions();

        sectionFunctions();

        managerFunctions();
    }

    private static void simpleFunctions() {
        final Function<Double, String> priceDescription = price -> "The price is " + price;
        System.out.println(priceDescription.apply(250d));

        final Function<String, String> lowerCase = String::toLowerCase;
        final Function<String, String> subString = value -> value.substring(0, 7);

        System.out.println(lowerCase.andThen(subString).apply("Testing functions chaining"));

        // TODO try other simple Functions - String, Boolean, ...
    }

    private static void productFunctions() {
        final Product aFancyAppleProduct = new Product(10, "iSomething", 500);
        final Function<Product, String> productPrinter = Product::toString;
        productPrinter.apply(aFancyAppleProduct);

        final ProductService productService = new ProductService();
        final Set<String> descriptions = productService.getSamsungTabletDescriptions();

        // using a Consumer on the retrieved items
        descriptions.forEach(printer());

        // TODO try other Product Functions
    }

    private static void sectionFunctions() {
        // TODO try a few Section Functions
    }

    private static void managerFunctions() {
        // TODO try a few Manager Functions
    }

    private static Consumer<String> printer() {
        return System.out::println;
    }
}
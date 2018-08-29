package net.safedata.java8.training.function;

import net.safedata.java8.training.model.Product;
import net.safedata.java8.training.function.service.ProductService;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A few {@link java.util.function.Function} usage samples
 */
@SuppressWarnings("unused")
public class FunctionsMain {

    public static void main(String[] args) {
        simpleFunctions();

        composingFunctions();

        productFunctions();

        mapFunctions();

        sectionFunctions();

        managerFunctions();

        comparators();
    }

    private static void simpleFunctions() {
        final Function<String, String> lowerCase = String::toLowerCase;
        System.out.println(lowerCase.apply("SOME"));

        final Function<String, String> subString = value -> value.substring(0, 7);
        System.out.println(lowerCase.andThen(subString)
                                    .apply("Testing functions chaining"));

        // using Functions as methods
        System.out.println(aFunctionAsAMethod().apply(20));

        // using Functions with expanded body - multiple statements
        final Function<String, String> processingFunction = value -> {
            if (value.length() > 10) {
                return value.toLowerCase();
            } else {
                value = value + " something";
                return value.toUpperCase();
            }
        };
        System.out.println(processingFunction.apply("there is"));

        // TODO try other simple Functions - String, Boolean, ...
    }

    private static void composingFunctions() {
        Function<String, Integer> first = value -> {
            System.out.println("first " + value.length());
            return value.length();
        };

        Function<Integer, String> second = value -> {
            System.out.println("second - " + value.toString());
            return value.toString();
        };

        Function<String, Integer> third = value -> {
            System.out.println("third - " + value.length() * 10);
            return value.length() * 10;
        };

        System.out.println(first.andThen(second)
                                .andThen(third)
                                .apply("something"));

        System.out.println();

        // first applies the {@code before} function to its input,
        // and then applies this function to the result
        System.out.println(first.compose(second)
                                .compose(third)
                                .apply("f(g(h(x)))")); // any text can go in here
    }

    private static void mapFunctions() {
        final Map<Integer, String> weekDays = new HashMap<>();
        weekDays.put(1, "Monday");
        weekDays.put(2, "Tuesday");

        // new methods were added to the Map interface
        weekDays.computeIfAbsent(3, value -> "The 3rd day"); // aka Wednesday

        weekDays.forEach((key, value) -> System.out.println(key + " -> " + value));
    }

    private static void productFunctions() {
        final Product aFancyAppleProduct = new Product(10, "iSomething", 500);
        final Function<Product, String> productPrinter = it -> it.toString(); //Product::toString
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

    private static Function<Integer, String> aFunctionAsAMethod() {
        return value -> "The value is " + value;
    }

    private static void comparators() {
        Comparator<Product> productComparator = Comparator.comparingInt(Product::getId);
        Comparator<Product> comparator = (first, second) -> first.getId() - second.getId();
    }
}
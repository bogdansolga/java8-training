package com.sg.java8.training.optional;

import com.sg.java8.training.bootstrap.StoreSetup;
import com.sg.java8.training.model.Product;
import com.sg.java8.training.model.Section;
import com.sg.java8.training.model.Store;
import com.sg.java8.training.model.StoreSection;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class OptionalSamples {

    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        optionalUsageModes();

        productOptionals();
    }

    private static void optionalUsageModes() {
        final String nullableValue = getNullableDayOfWeek();
        final Optional<String> optionalDay = Optional.ofNullable(nullableValue);

        // 1st way - map + orElse / orElseGet - apply a processing if the value exists, return another value otherwise
        mapOrElse(optionalDay);

        // 2nd way - map + orElseThrow - apply a processing if the value exists, throw an (unchecked) exception otherwise
        mapOrElseThrow(optionalDay);

        // 3rd way - ifPresent - apply a Consumer to the wrapped value, if it exists
        ifPresent(optionalDay);

        // 4th way - isPresent + get - verify if the value is present, get the wrapped value using .get() if it exists
        // *always* use .isPresent() before .get()
        isPresent(optionalDay);

        // 5th way - flatMap - returning another optional type from an existing Optional
        flatMap(optionalDay);
    }

    private static void mapOrElse(final Optional<String> optionalDay) {
        // use a fallback / default value if the wrapped value does not exist
        final int unWrappedValue = optionalDay.map(Integer::parseInt)
                                              .orElse(0);
        System.out.println("The mapOrElse value is " + unWrappedValue);

        // or use a Supplier to return a value
        final int unWrappedUsingOrElseGet = optionalDay.map(Integer::parseInt)
                                                       .orElseGet(() -> RANDOM.nextInt(20));
        System.out.println("The mapOrElseGet value is " + unWrappedUsingOrElseGet);
    }

    private static void mapOrElseThrow(final Optional<String> optionalDay) {
        final int unWrappedOrThrow = optionalDay.map(Integer::parseInt)
                                                .orElseThrow(() -> new IllegalArgumentException("Cannot be null"));
        System.out.println("The unWrapped value is " + unWrappedOrThrow);
    }

    private static void ifPresent(final Optional<String> optionalDay) {
        optionalDay.ifPresent(it -> System.out.println("The wrapped value is " + it));
    }

    private static void isPresent(final Optional<String> optionalDay) {
        // optionalDay.get(); --> NOT recommended, as it may throw a NoSuchElementException exception

        if (optionalDay.isPresent()) {
            final String unWrapped = optionalDay.get();
            process(unWrapped); // process the unwrapped value
        } else {
            // do another processing
            System.out.println("The wrapped value is not present");
        }
    }

    private static void flatMap(final Optional<String> optionalDay) {
        // converting the wrapped value into an Optional double, if it exists
        final Optional<Double> doubleOptional = optionalDay.flatMap(it -> Optional.of(Double.parseDouble(it)));

        // use it
        doubleOptional.ifPresent(value -> System.out.println("-> " + value));

        // or further flatMap it
        doubleOptional.flatMap(value -> Optional.of(value * 50));
    }

    private static void productOptionals() {
        // assuming the store may be null
        final Optional<Store> optionalStore = Optional.ofNullable(StoreSetup.getDefaultStore());

        // getting a section which may be null
        final Optional<Section> optionalTabletsSection = optionalStore.flatMap(store -> getTabletsSection(store));

        // the section may not contain any products
        final Optional<List<Product>> optionalProducts = optionalTabletsSection.flatMap(section -> section.getProducts());

        optionalProducts.flatMap(products -> getAppleTablet(products))  // the list of products may not contain an Apple tablet
                        .flatMap(product -> product.getDiscount())      // the tablet may not have a discount
                        .ifPresent(discount -> System.out.println("The Apple tablet has the discount " + discount));

        // everything in one processing
        chainedFlatMapping();
    }

    private static void chainedFlatMapping() {
        // monadic processing - the execution chain is completed only if all the optional values are present
        Optional.ofNullable(StoreSetup.getDefaultStore())
                .flatMap(OptionalSamples::getTabletsSection)
                .flatMap(Section::getProducts)
                .flatMap(OptionalSamples::getAppleTablet)
                .flatMap(Product::getDiscount)
                .flatMap(discount -> Optional.of(discount.getValue()))
                .ifPresent(value -> System.out.println("The discount value is " + value));
    }

    private static String getNullableDayOfWeek() {
        return System.currentTimeMillis() % 2 == 0 ? Integer.toString(LocalDate.now().getDayOfWeek().getValue()) : null;
    }

    private static void process(final String value) {
        System.out.println("Processing the value '" + value + "'...");
    }

    private static Optional<Section> getTabletsSection(final Store store) {
        return store.getStoreSections()
                    .stream()
                    .filter(section -> section.getName().equals(StoreSection.Tablets))
                    .findFirst();
    }

    private static Optional<Product> getAppleTablet(final List<Product> products) {
        return products.stream()
                       .filter(product -> product.getName().contains("Apple"))
                       .findFirst();
    }
}

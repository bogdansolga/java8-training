package com.sg.java8.training.completable.future;

import com.sg.java8.training.bootstrap.StoreSetup;
import com.sg.java8.training.model.Section;
import com.sg.java8.training.model.Store;
import org.jooq.lambda.Unchecked;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

class ProductProcessor {

    CompletableFuture<Long> getProductsStock(final String productName) {
        return CompletableFuture.supplyAsync(() -> {
            displayThreadName();
            sleepALittle(500);

            final Store store = StoreSetup.getDefaultStore();
            return store.getStoreSections()
                        .stream()
                        .map(Section::getProducts)
                        .flatMap(Collection::stream)
                        .filter(product -> product.getName().contains(productName))
                        .count();
        });
    }

    Function<Long, CompletableFuture<Double>> getProductsPrice() {
        return productsStock -> {
            displayThreadName();
            sleepALittle(400);

            return CompletableFuture.supplyAsync(() -> productsStock * 230d);
        };
    }

    Function<Double, CompletableFuture<String>> getDisplayedText() {
        return productsPrice -> {
            displayThreadName();
            sleepALittle(200);

            return CompletableFuture.supplyAsync(() -> "The price of the products is " +
                    productsPrice);
        };
    }

    // TODO return a Map of the products and their stock, using a grouping collector

    private void sleepALittle(final int sleepDuration) {
        Unchecked.consumer(it -> Thread.sleep(sleepDuration))
                 .accept(null);
    }

    private void displayThreadName() {
        System.out.println(Thread.currentThread().getName());
    }
}

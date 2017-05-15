package com.sg.java8.training.completable.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A few {@link java.util.concurrent.CompletableFuture} usage samples
 */
public class CompletableFutureMain {

    private static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();

    private static final Executor EXECUTOR = Executors.newWorkStealingPool(AVAILABLE_PROCESSORS / 2);

    public static void main(String[] args) {
        helloSimpleCompletableFutures();

        simpleCompletableFutures();

        chainedCompletionStages();

        productsOperations();

        shutdownExecutor();
    }

    private static void helloSimpleCompletableFutures() {
        final CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            displayCurrentThread();
            return "I will run on Saturday";
        });
        //System.out.println(completableFuture.join());

        completableFuture.thenAcceptAsync(value -> {
            displayCurrentThread();
            System.out.println("The received value is " + value);
        });

        completableFuture.exceptionally(ex -> "Some exception occurred");

        String processingResult = completableFuture.join();
        System.out.println("The processing returned " + processingResult);
    }

    private static void simpleCompletableFutures() {
        final CompletableFuture<String> completableFuture =
                CompletableFuture.supplyAsync(() -> "a very simple text");

        final Consumer<String> stringConsumer = stringPrinter();
        completableFuture.thenAcceptAsync(stringConsumer);

        final CompletableFuture<String> anotherFuture =
                CompletableFuture.supplyAsync(() -> "another text");

        completableFuture.thenCompose(value -> anotherFuture);
        final CompletableFuture<String> completableFuture1 =
                anotherFuture.thenApplyAsync(value -> value);

        completableFuture.exceptionally(throwable -> "Thrown: " + throwable.getMessage());

        completableFuture.thenApplyAsync(String::toUpperCase, Executors.newCachedThreadPool());
        completableFuture.acceptEither(anotherFuture, stringConsumer);
    }

    private static void chainedCompletionStages() {
        CompletableFuture<String> first = CompletableFuture.supplyAsync(() -> {
            displayCurrentThread();
            return "first";
        }, EXECUTOR);

        CompletableFuture<String> second = CompletableFuture.supplyAsync(() -> {
            displayCurrentThread();
            return "second";
        }, EXECUTOR);

        CompletableFuture<Integer> third = CompletableFuture.supplyAsync(() -> {
            displayCurrentThread();
            return 7;
        }, EXECUTOR);

        final CompletableFuture<Integer> future =
                first.thenComposeAsync(value -> second)
                     .thenComposeAsync(value -> third);

        System.out.println(future.join());

        final CompletableFuture<Void> finalFuture = CompletableFuture.allOf(first, second);
        finalFuture.thenAccept(value -> notifyFinishedTasks());
    }

    private static void productsOperations() {
        final ProductProcessor productProcessor = new ProductProcessor();

        final CompletableFuture<Long> getProductsStock = productProcessor.getProductsStock("iPad");
        final Function<Long, CompletableFuture<Double>> getProductsPrice = productProcessor.getProductsPrice();
        final Function<Double, CompletableFuture<String>> getProductsDisplayText = productProcessor.getDisplayedText();

        /*
            The three processing stages:
                - 1) get products stock
                - 2) get products price, for the resulted stock
                - 3) get displayed text, for the products price and stock
        */

        final String productsText = getProductsStock.thenComposeAsync(getProductsPrice, EXECUTOR)
                                                    .thenComposeAsync(getProductsDisplayText, EXECUTOR)
                                                    .exceptionally(Throwable::getMessage)
                                                    .join();
        System.out.println(productsText);
    }

    private static void displayCurrentThread() {
        System.out.println(Thread.currentThread().getName());
    }

    private static void notifyFinishedTasks() {
        System.out.println(Thread.currentThread().getName() + " - All good");
    }

    private static Consumer<String> stringPrinter() {
        return value -> System.out.println(Thread.currentThread().getName() + " - " + value);
    }

    private static void shutdownExecutor() {
        ((ExecutorService) EXECUTOR).shutdown();
        //System.out.println("The executor was properly shutdown");
    }
}
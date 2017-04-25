package com.sg.java8.training.completable.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * A few {@link java.util.concurrent.CompletableFuture} usage samples
 */
public class CompletableFutureMain {
    private static final Executor EXECUTOR = Executors.newFixedThreadPool(5);
    private static final ExecutorCompletionService COMPLETION_SERVICE =
            new ExecutorCompletionService(EXECUTOR);

    public static void main(String[] args) {
        //simpleCompletableFutures();

        CompletableFuture<String> first = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            return "first";
        }, EXECUTOR);

        CompletableFuture<String> second = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            return "second";
        }, EXECUTOR);

        CompletableFuture<Integer> third = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            return 7;
        });

        final CompletableFuture<Integer> future =
                first.thenCompose(value -> second)
                     .thenComposeAsync(value -> third);

        System.out.println(future.join());

        final CompletableFuture<Void> finalFuture = CompletableFuture.allOf(first, second);
        finalFuture.thenAccept(value -> notifyFinishedTasks());

        ((ExecutorService) EXECUTOR).shutdown();

        //first.thenCombine(second, (x, y) -> x + y);
    }

    private static void notifyFinishedTasks() {
        System.out.println(Thread.currentThread().getName() + " - All good");
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

    private static Consumer<String> stringPrinter() {
        return value -> System.out.println(Thread.currentThread().getName() + " - " + value);
    }
}
package com.sg.java8.training.completable.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

/**
 * A few {@link java.util.concurrent.CompletableFuture} usage samples
 */
public class CompletableFutureMain {

    public static void main(String[] args) {
        final CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "a very simple text");
        completableFuture.thenAcceptAsync(value -> System.out.println(Thread.currentThread().getName() + " - " + value));

        final CompletableFuture<String> anotherFuture = CompletableFuture.supplyAsync(() -> "another text");
        completableFuture.thenCompose(value -> anotherFuture);
        anotherFuture.thenAcceptAsync(value -> System.out.println(Thread.currentThread().getName() + " - " + value));

        completableFuture.exceptionally(throwable -> "Thrown: " + throwable.getMessage());

        completableFuture.thenApplyAsync(String::toUpperCase, Executors.newCachedThreadPool());
        //completableFuture.acceptEitherA()
    }
}
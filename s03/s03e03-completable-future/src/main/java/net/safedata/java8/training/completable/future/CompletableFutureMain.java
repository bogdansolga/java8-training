package net.safedata.java8.training.completable.future;

import java.math.BigInteger;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * A few {@link java.util.concurrent.CompletableFuture} usage samples
 */
public class CompletableFutureMain {

    private static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();

    private static final Executor EXECUTOR = Executors.newFixedThreadPool(AVAILABLE_PROCESSORS / 4);

    public static void main(String[] args) {
        simpleCompletableFutures();

        chainedCompletionStages();

        simpleProductsOperations();

        moreComplexProductsOperations();

        parallelFactorial();

        shutdownExecutor();
    }

    private static void simpleCompletableFutures() {
        CompletableFuture<Void> runningCF = CompletableFuture.runAsync(() -> System.out.println("Hello, CompletableFuture!"));
        runningCF.join();

        CompletableFuture<String> simpleCFAsync = CompletableFuture.supplyAsync(() -> "Returning from " + getCurrentThreadName());
        simpleCFAsync.thenAcceptAsync(value -> System.out.println(getCurrentThreadName() + " - Got the value: " + value));

        CompletableFuture<String> simpleCF = CompletableFuture.supplyAsync(() -> "Returning from " + getCurrentThreadName());
        simpleCF.thenAccept(value -> System.out.println(getCurrentThreadName() + " - Got the value: " + value));
    }

    private static void chainedCompletionStages() {
        CompletableFuture<String> first = CompletableFuture.supplyAsync(() -> {
            displayCurrentThread();
            System.out.println("Processing the first CompletableFuture...");
            return "first";
        });

        CompletableFuture<String> second = CompletableFuture.supplyAsync(() -> {
            displayCurrentThread();
            System.out.println("Processing the second CompletableFuture...");
            return "second";
        }, EXECUTOR);

        CompletableFuture<Integer> third = CompletableFuture.supplyAsync(() -> {
            displayCurrentThread();
            System.out.println("Processing the third CompletableFuture...");
            return new Random().nextInt(400);
        });

        final CompletableFuture<Integer> chainedFuture = first.thenComposeAsync(value -> second)
                                                              .thenComposeAsync(value -> third);
        System.out.println("The processing result is " + chainedFuture.join());

        multipleCallsProcessing(first, second, third);

        shutdownExecutor();
    }

    private static void multipleCallsProcessing(final CompletableFuture<String> first,
                                                final CompletableFuture<String> second,
                                                final CompletableFuture<Integer> third) {

        // will return a CompletableFuture<Void> when all the tasks will be finished --> no further processing can be made
        final CompletableFuture<Void> allOfFuture = CompletableFuture.allOf(first, second, third);
        allOfFuture.thenAccept(value -> notifyFinishedTasks());

        // will return a CompletableFuture<Object> when any of the tasks will be finished --> can chain further processing(s)
        final CompletableFuture<Object> anyOfFuture = CompletableFuture.anyOf(first, second, third);
        anyOfFuture.thenAccept(returnValue -> System.out.println("Processing the value '" + returnValue + "'..."));

        final Object result = anyOfFuture.join(); // the first finished processing
        System.out.println("The first finished processing is '" + result + "'");
    }

    private static void simpleProductsOperations() {
        final ProductProcessor productProcessor = new ProductProcessor();

        final CompletableFuture<Long> getProductsStock = productProcessor.getProductsStock("iPad");
        final Function<Long, CompletableFuture<Double>> getProductsPrice = productProcessor.getProductsPrice();
        final Function<Double, CompletableFuture<String>> getProductsDisplayText = productProcessor.getDisplayedText();

        /*
            The three processing stages:
                - 1) get products stock
                - 2) get products price, for the resulted stock
                - 3) get the displayed text, for the products price and stock
        */

        final String displayedText = getProductsStock.thenComposeAsync(getProductsPrice)
                                                     .thenComposeAsync(getProductsDisplayText)
                                                     .exceptionally(Throwable::getMessage)
                                                     .join();
        System.out.println("Got the text '" + displayedText + "'");

        shutdownExecutor();
    }

    private static void moreComplexProductsOperations() {
        final ProductProcessor productProcessor = new ProductProcessor();

        final CompletableFuture<Long> getProductsStock = productProcessor.getProductsStock("iPad");
        final CompletableFuture<Long> getReserveStock = productProcessor.getReserveStock("iPad");
        final Function<Long, CompletableFuture<Double>> getProductsPrice = productProcessor.getProductsPrice();
        final Function<Double, CompletableFuture<String>> getProductsDisplayText = productProcessor.getDisplayedText();

        /*
            The five processing stages:
                - 1) get products stock OR get the reserve stock (whichever finishes first)
                - 2) get products price, for the resulted stock
                - 3) get the displayed text, for the products price and stock
                - 4) when either the displayed text or an exception is returned, complete the stage asynchronously
        */

        // --> use the final call as a sync / async calls orchestration
        final String displayedText = getProductsStock.applyToEitherAsync(getReserveStock, Function.identity(), EXECUTOR)
                                                     .thenComposeAsync(getProductsPrice, EXECUTOR)
                                                     .thenComposeAsync(getProductsDisplayText, EXECUTOR)
                                                     .whenCompleteAsync(CompletableFutureMain::processResult, EXECUTOR)
                                                     .exceptionally(Throwable::getMessage)
                                                     .completeOnTimeout("timed-out", 5000, TimeUnit.MILLISECONDS)
                                                     .join();
        System.out.println("Got the text '" + displayedText + "'");

        shutdownExecutor();
    }

    private static void processResult(final String result, final Throwable exception) {
        //System.out.println("Processing the result - " + result + ", " + exception);
        if (exception != null) {
            throw new RuntimeException(exception.getMessage());
        } else {
            CompletableFuture.supplyAsync(() -> result, EXECUTOR);
        }
    }

    private static void displayCurrentThread() {
        System.out.println(getCurrentThreadName());
    }

    private static String getCurrentThreadName() {
        return Thread.currentThread().getName();
    }

    private static void notifyFinishedTasks() {
        System.out.println(Thread.currentThread().getName() + " - All good");
    }

    private static void parallelFactorial() {
        System.out.println(divideAndConquer(10, 20).join());
    }

    private static CompletableFuture<BigInteger> divideAndConquer(int from, int to) {
        if (from == to) {
            if (from == 0) return CompletableFuture.completedFuture(BigInteger.ONE);
            return CompletableFuture.completedFuture(BigInteger.valueOf(from));
        }

        int middle = (from + to) >>> 1;

        return divideAndConquer(from, middle).thenCombineAsync(divideAndConquer(middle + 1, to), BigInteger::multiply);
    }

    private static void shutdownExecutor() {
        ((ExecutorService) EXECUTOR).shutdown();
        System.out.println("The executor was properly shutdown");
    }
}
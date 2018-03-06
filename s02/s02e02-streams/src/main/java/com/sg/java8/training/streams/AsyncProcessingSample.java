package com.sg.java8.training.streams;

import com.sg.java8.training.model.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * A small sample of a pre JDK 1.8 parallel processing
 *
 * @author bogdan.solga
 */
public class AsyncProcessingSample {

    private static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();

    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        // 0 - create the ExecutorService and ExecutorCompletionService objects

        /* An ExecutorService provides methods to manage termination and methods that produce Future wrapped objects,
            for tracking progress of one or more asynchronous tasks. It is the entry point into concurrent handling code in Java.
            A few predefined implementations are available through static methods in the Executors class.
         */
        final ExecutorService executorService = Executors.newFixedThreadPool(AVAILABLE_PROCESSORS / 2);

        final ExecutorCompletionService<Integer> executorCompletionService =
                new ExecutorCompletionService<>(executorService);

        final List<Integer> depositIds = Arrays.asList(10, 20, 30, 25, 21, 54, 35, 213, 45, 65, 76, 34);
        final long now = System.currentTimeMillis();

        // 1 - submit tasks --> forking (/ mapping) phase
        int submittedTasks = 0;
        for (final Integer depositId : depositIds) {
            executorCompletionService.submit(new ProductProcessor(depositId));
            submittedTasks++;
        }

        /*
            - a Future object is the result of an asynchronous computation, acting as a wrapper around the computed value
            - the result can *only* be retrieved using the .get() method, when the computation has completed,
            blocking (if necessary) until it is ready
        */
        Future<Integer> productStock;

        // 2 - poll the executor completion service for async results --> joining (/ reducing) phase
        final List<Integer> productStocks = new ArrayList<>(submittedTasks);
        try {
            for (int i = 0; i < submittedTasks; i++) {
                productStock = executorCompletionService.poll(2000, TimeUnit.MILLISECONDS);

                if (productStock != null && productStock.isDone()) {
                    productStocks.add(productStock.get());
                }
            }
        } catch (final ExecutionException | InterruptedException ex) { // catching .get() thrown exceptions
            ex.printStackTrace();
        }

        System.out.println();
        System.out.println("The entire processing took " + (System.currentTimeMillis() - now) + " ms");

        final int totalStock = productStocks.stream()
                                            .mapToInt(Integer::intValue)
                                            .sum();
        System.out.println("The total stock of products is " + totalStock);

        // 3 - if needed - shutdown the executorService
        final Optional<List<Runnable>> unfinishedTasks = Optional.ofNullable(executorService.shutdownNow());
        unfinishedTasks.ifPresent(tasks -> System.err.println("There are " + tasks.size() + " unfinished tasks"));
    }

    /**
     * The product processor queries several deposits for their stock of {@link Product}s
     */
    private static class ProductProcessor implements Callable<Integer> {
        private Integer depositId;

        ProductProcessor(final Integer depositId) {
            this.depositId = depositId;
        }

        @Override
        public Integer call() throws Exception {
            final long now = System.currentTimeMillis();
            Thread.sleep(RANDOM.nextInt(2000));
            System.out.println("[" + Thread.currentThread().getName() + "] Got the stock from the deposit "
                                       + depositId + " in " + (System.currentTimeMillis() - now) + " ms");

            return depositId * RANDOM.nextInt(200);
        }
    }
}

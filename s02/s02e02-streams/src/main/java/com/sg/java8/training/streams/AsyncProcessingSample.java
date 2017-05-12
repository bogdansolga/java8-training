package com.sg.java8.training.streams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
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

    public static void main(String[] args) {
        // 0 - create ExecutorService and ExecutorCompletionService

        /* An ExecutorService provides methods to manage termination and methods that can produce a
            Future for tracking progress of one or more asynchronous tasks.
            It is the entry point into concurrent handling code in Java.
            Implementations of this interface - as well are more specialized ones,
            can be obtained through static methods in the Executors class.
         */
        final ExecutorService executorService = Executors.newFixedThreadPool(AVAILABLE_PROCESSORS);
        //final ExecutorService executorService = new ForkJoinPool(AVAILABLE_PROCESSORS);

        final ExecutorCompletionService<Integer> executorCompletionService =
                new ExecutorCompletionService<>(executorService);

        final List<Integer> integers = Arrays.asList(10, 20, 30, 25, 21, 54, 35, 213, 45, 65, 76, 34);

        // 1 - submit tasks --> fork phase
        int submittedTasks = 0;
        for (Integer integer : integers) {
            executorCompletionService.submit(new NumberProcessor(integer));
            submittedTasks++;
        }

        /* A Future is the result of an asynchronous computation.
         The result can *only* be retrieved using method get when the computation has completed,
         blocking if necessary until it is ready". In other words, it represents a wrapper
         around a value, where this value is the outcome of a calculation.
        */
        Future<Integer> result;

        // 2 - poll for async results --> join phase
        final List<Integer> joinedResults = new ArrayList<>(submittedTasks);
        try {
            for (int i = 0; i < submittedTasks; i++) {
                result = executorCompletionService.poll(1000, TimeUnit.MILLISECONDS);

                if (result != null && result.isDone()) {
                    joinedResults.add(result.get());
                }
            }
        } catch (final Exception ex) { // catching .get() thrown exceptions
            ex.printStackTrace();
        }

        joinedResults.forEach(System.out::println);

        // 3 - if needed - shutdown the executorService
        executorService.shutdown();
    }

    private static class NumberProcessor implements Callable<Integer> {
        private Integer integer;

        NumberProcessor(Integer integer) {
            this.integer = integer;
        }

        @Override
        public Integer call() throws Exception {
            System.out.println(Thread.currentThread().getName());
            Thread.sleep(300);
            return integer * integer;
        }
    }
}

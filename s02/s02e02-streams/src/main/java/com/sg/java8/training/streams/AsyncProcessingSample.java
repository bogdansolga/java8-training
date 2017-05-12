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

        // 2 - poll for async results --> join phase
        final List<Integer> joinedResults = new ArrayList<>(submittedTasks);
        try {
            for (int i = 0; i < submittedTasks; i++) {
                final Future<Integer> result = executorCompletionService.poll(1000, TimeUnit.MILLISECONDS);

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

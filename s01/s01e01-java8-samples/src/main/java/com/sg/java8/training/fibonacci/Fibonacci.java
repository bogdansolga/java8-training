package com.sg.java8.training.fibonacci;

import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Computing the Fibonacci numbers in imperative and functional ways
 *
 * @author bogdan.solga
 */
class Fibonacci {

    static void imperative(final int until) {
        for (int i = 1; i <= until; i++) {
            System.out.print(fibonacci(i) + " ");
        }
    }

    static void functional(final int until) {
        IntStream fibonacciStream = Stream.iterate(new int[]{1, 1}, value -> new int[] {value[1], value[0] + value[1]})
                                          .mapToInt(number -> number[0]);
        fibonacciStream.limit(until)
                       .forEach(number -> System.out.print(number + " "));
    }

    private static int fibonacci(int number) {
        int first = 1;
        int second = 1;
        int fibonacci = first;
        for (int i = 2; i < number; i++) {
            fibonacci = first + second;
            first = second;
            second = fibonacci;
        }

        return fibonacci;
    }
}

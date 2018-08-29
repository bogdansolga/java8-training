package net.safedata.java8.training.operators;

import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

/**
 * A few {@link java.util.function.UnaryOperator}s and {@link java.util.function.BinaryOperator}s interfaces usage samples
 */
public class OperatorsMain {

    public static void main(String[] args) {
        unaryOperators();

        binaryOperators();
    }

    private static void unaryOperators() {
        final UnaryOperator<String> lowerCase = String::toLowerCase;
        System.out.println(lowerCase.apply("Somewhere"));

        final List<String> values = Arrays.asList("Some", "Random", "Values");
        values.replaceAll(lowerCase);
    }

    private static void binaryOperators() {
        final BinaryOperator<Double> squareRoot = (x, y) -> Math.sqrt(x * y);
        System.out.println(squareRoot.apply(20.5, 7.2));
    }
}
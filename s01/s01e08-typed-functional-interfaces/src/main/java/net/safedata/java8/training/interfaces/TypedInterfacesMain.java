package net.safedata.java8.training.interfaces;

import java.util.function.DoubleFunction;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntToLongFunction;

/**
 * A few typed interfaces samples
 */
public class TypedInterfacesMain {

    public static void main(String[] args) {
        intFunctionalInterfaces();

        doubleFunctionalInterfaces();
    }

    private static void intFunctionalInterfaces() {
        IntConsumer intConsumer = System.out::println;
        intConsumer.accept(55);

        IntFunction<String> intFunction = some -> "The value is " + some;
        System.out.println(intFunction.apply(33));

        IntToLongFunction converter = value -> (long) value;
        System.out.println(converter.applyAsLong(235));
    }

    private static void doubleFunctionalInterfaces() {
        DoubleFunction<String> doubleFunction = value -> "The double value is " + value;
        System.out.println(doubleFunction.apply(55));
    }
}
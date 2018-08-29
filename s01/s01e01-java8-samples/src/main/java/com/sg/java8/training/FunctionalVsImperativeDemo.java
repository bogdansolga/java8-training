package com.sg.java8.training;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class FunctionalVsImperativeDemo {

    public static void main(String[] args) {
        final List<String> strings = Arrays.asList("I want a holiday, not just a weekend".split(" "));

        // imperative processing --> how
        final Iterator<String> iterator = strings.iterator();
        while (iterator.hasNext()) {
            String currentValue = iterator.next();
            if (currentValue.isEmpty()) {
                iterator.remove();
            }
        }

        // functional processing --> what
        strings.removeIf(String::isEmpty); // value -> value.isEmpty()
    }
}

package com.sg.java8.training.jool;

import org.jooq.lambda.Seq;

/**
 * A short sample of using {@link org.jooq.lambda.Seq}
 */
public class SeqMain {

    public static void main(String[] args) {
        final Seq<String> seq = Seq.of("some", "values", "and", "some", "others");
        seq.distinct()
           .sorted()
           .forEach(System.out::println);
    }
}

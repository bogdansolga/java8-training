package com.sg.java8.training.jool;

import org.jooq.lambda.Seq;
import org.jooq.lambda.tuple.Tuple2;


/**
 * A short sample of using {@link org.jooq.lambda.Seq}
 */
public class SeqMain {

    public static void main(String[] args) {
        final Seq<String> seq = getSequence();
        seq.distinct()
           .sorted()
           .forEach(System.out::println);

        System.out.println("------------------------------------------------------");

        final String folded = getSequence().foldRight("{", (first, second) -> first + "|" + second);
        System.out.println(folded);

        System.out.println("------------------------------------------------------");

        final Seq<Tuple2<String, Long>> zipWithIndex = getSequence().zipWithIndex();
        System.out.println(zipWithIndex);
    }

    private static Seq<String> getSequence() {
        return Seq.of("I want a holiday, not just a weekend".split(" "));
    }
}

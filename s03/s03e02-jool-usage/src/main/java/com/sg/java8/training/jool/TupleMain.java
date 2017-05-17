package com.sg.java8.training.jool;

import com.sg.java8.training.model.Product;
import org.jooq.lambda.tuple.Tuple2;
import org.jooq.lambda.tuple.Tuple3;

/**
 * A short sample of using {@link org.jooq.lambda.tuple.Tuple}s
 */
public class TupleMain {

    public static void main(String[] args) {
        // recommended usage - *only* as lambda expression parameters

        Tuple2<String, Integer> tuple = new Tuple2<>("value", 6);
        System.out.println(tuple.v2());

        Tuple3<Integer, Product, String> tuple3 =
                new Tuple3<>(5, new Product(1, "iSomething", 600d), "a new gadget");
        System.out.println("The product is " + tuple3.v2());
    }
}

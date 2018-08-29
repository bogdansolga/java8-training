package net.safedata.java8.training.model;

import java.io.Serializable;

public class Discount implements Serializable {

    private final int value;
    private final Discount.Type discountType;

    public Discount(final int value, final Type discountType) {
        this.value = value;
        this.discountType = discountType;
    }

    public int getValue() {
        return value;
    }

    public Type getDiscountType() {
        return discountType;
    }

    public enum Type {
        Percent,
        Value
    }

    @Override
    public String toString() {
        return value + " " + discountType;
    }
}

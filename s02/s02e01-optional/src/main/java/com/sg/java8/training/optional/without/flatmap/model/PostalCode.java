package com.sg.java8.training.optional.without.flatmap.model;

public class PostalCode {

    private int number;
    private String prefix;

    public PostalCode(int number, String prefix) {
        this.number = number;
        this.prefix = prefix;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}

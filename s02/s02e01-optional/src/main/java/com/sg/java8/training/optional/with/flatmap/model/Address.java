package com.sg.java8.training.optional.with.flatmap.model;

import java.util.Optional;

public class Address {

    private int nr;
    private String street;
    private String country;
    private String city;
    private Optional<PostalCode> postalCode =  Optional.empty();

    public Address(int nr, String street, String country, String city) {
        this.nr = nr;
        this.street = street;
        this.country = country;
        this.city = city;
    }

    public int getNr() {
        return nr;
    }

    public void setNr(int nr) {
        this.nr = nr;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Optional<PostalCode> getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(PostalCode postalCode) {
        this.postalCode = Optional.of(postalCode);
    }
}

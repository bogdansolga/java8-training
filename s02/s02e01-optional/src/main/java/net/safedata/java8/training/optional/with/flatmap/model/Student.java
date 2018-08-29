package net.safedata.java8.training.optional.with.flatmap.model;

import java.util.Optional;

public class Student {

    private String name;
    private Address address;
    private Optional<Address> temporaryAddress = Optional.empty();

    public Student(String name, Address address) {
        this.address = address;
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Optional<Address> getTemporaryAddress() {
        return temporaryAddress;
    }

    public void setTemporaryAddress(Address temporaryAddress) {
        this.temporaryAddress = Optional.ofNullable(temporaryAddress);
    }

    public String getName() {
        return name;
    }
}

package net.safedata.java8.training.optional.without.flatmap.model;

public class Student {

    private String name;
    private Address address;
    private Address temporaryAddress;

    public Student(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Address getTemporaryAddress() {
        return temporaryAddress;
    }

    public void setTemporaryAddress(Address temporaryAddress) {
        this.temporaryAddress = temporaryAddress;
    }
}

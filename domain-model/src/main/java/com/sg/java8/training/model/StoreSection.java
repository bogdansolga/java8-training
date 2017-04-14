package com.sg.java8.training.model;

import java.util.Objects;
import java.util.Set;

public class StoreSection extends AbstractEntity {

    private int id;

    private String sectionName;

    private Store store;

    private Set<Product> products;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StoreSection)) return false;
        StoreSection that = (StoreSection) o;
        return id == that.id &&
                Objects.equals(sectionName, that.sectionName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sectionName);
    }
}

package com.sg.java8.training.model;

import java.util.List;
import java.util.Objects;

public class Section extends AbstractEntity {

    private final int id;
    private final String sectionName;
    private final List<Product> products;

    public Section(final int id, final String sectionName, final List<Product> products) {
        this.id = id;
        this.sectionName = sectionName;
        this.products = products;
    }

    public int getId() {
        return id;
    }

    public String getSectionName() {
        return sectionName;
    }

    public List<Product> getProducts() {
        return products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Section)) return false;
        Section that = (Section) o;
        return id == that.id &&
                Objects.equals(sectionName, that.sectionName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sectionName);
    }
}

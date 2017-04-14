package com.socgen.java8.training.model;

import java.util.Objects;
import java.util.Set;

public class Manager extends AbstractEntity {

    private int id;

    private String name;

    private Set<Store> stores;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Store> getStores() {
        return stores;
    }

    public void setStores(Set<Store> stores) {
        this.stores = stores;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Manager)) return false;
        Manager manager = (Manager) o;
        return id == manager.id &&
                Objects.equals(name, manager.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}

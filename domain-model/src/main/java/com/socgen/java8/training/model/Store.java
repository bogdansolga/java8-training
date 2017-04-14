package com.socgen.java8.training.model;

import java.util.Objects;
import java.util.Set;

public class Store extends AbstractEntity {

    private int id;

    private String name;

    private String location;

    private Set<StoreSection> storeSections;

    private Set<Manager> storeManagers;

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

    public String getLocation() {
        return location;
    }

    public void setLocation(final String location) {
        this.location = location;
    }

    public Set<Manager> getStoreManagers() {
        return storeManagers;
    }

    public void setStoreManagers(Set<Manager> storeManagers) {
        this.storeManagers = storeManagers;
    }

    public Set<StoreSection> getStoreSections() {
        return storeSections;
    }

    public void setStoreSections(Set<StoreSection> storeSections) {
        this.storeSections = storeSections;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Store)) return false;
        Store store = (Store) o;
        return id == store.id &&
                Objects.equals(name, store.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}

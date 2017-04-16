package com.sg.java8.training.model;

import java.util.Objects;
import java.util.Set;

public class Store extends AbstractEntity {

    private final int id;
    private final String name;
    private final String location;
    private final Set<Section> storeSections;
    private final Set<Manager> storeManagers;

    public Store(final int id, final String name, final String location,
                 final Set<Section> storeSections, final Set<Manager> storeManagers) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.storeSections = storeSections;
        this.storeManagers = storeManagers;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public Set<Manager> getStoreManagers() {
        return storeManagers;
    }

    public Set<Section> getStoreSections() {
        return storeSections;
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

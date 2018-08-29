package net.safedata.java8.training.model;

import java.util.Objects;

public class Manager extends AbstractEntity {

    private final int id;
    private final String name;

    public Manager(final int id, final String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
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

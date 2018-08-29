package net.safedata.java8.training.model;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Section extends AbstractEntity {

    private final int id;
    private final StoreSection name;
    private final List<Product> products;

    public Section(final int id, final StoreSection name, final List<Product> products) {
        this.id = id;
        this.name = name;
        this.products = products;
    }

    public int getId() {
        return id;
    }

    public StoreSection getName() {
        return name;
    }

    public Optional<List<Product>> getProducts() {
        return Optional.ofNullable(products);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Section)) return false;
        Section that = (Section) o;
        return id == that.id &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}

package net.safedata.java8.training.model;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Product extends AbstractEntity {

    private final int id;
    private final String name;
    private final double price;
    private final Discount discount;
    private final List<String> tags;

    public Product(final int id, final String name, final double price, final Discount discount,
                   final List<String> tags) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.discount = discount;
        this.tags = tags;
    }

    public Product(final int id, final String name, final double price, final Discount discount) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.discount = discount;
        this.tags = null;
    }

    public Product(final int id, final String name, final double price) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.discount = null;
        this.tags = null;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public Optional<Discount> getDiscount() {
        return Optional.ofNullable(discount);
    }

    public Optional<List<String>> getTags() {
        return Optional.ofNullable(tags);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return id == product.id &&
                Objects.equals(name, product.name) &&
                Objects.equals(price, product.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price);
    }

    @Override
    public String toString() {
        return id + ", " + name + ": " + price;
    }
}

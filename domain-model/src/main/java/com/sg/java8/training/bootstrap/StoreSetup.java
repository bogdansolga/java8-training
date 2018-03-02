package com.sg.java8.training.bootstrap;

import com.sg.java8.training.model.Discount;
import com.sg.java8.training.model.Manager;
import com.sg.java8.training.model.Product;
import com.sg.java8.training.model.Section;
import com.sg.java8.training.model.Store;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static com.sg.java8.training.model.StoreSection.Monitors;
import static com.sg.java8.training.model.StoreSection.Laptops;
import static com.sg.java8.training.model.StoreSection.Tablets;

public final class StoreSetup {

    private static Store defaultStore;

    static {
        final Section tabletsSection = new Section(1, Tablets, buildDefaultTablets());
        final Section monitorsSection = new Section(2, Monitors, buildDefaultMonitors());
        final Section laptopsSection = new Section(3, Laptops, buildDefaultLaptops());

        final Manager john = new Manager(1, "John Doe");
        final Manager jane = new Manager(2, "Jane Charming");

        defaultStore = new Store(1, "Goodies", "Over there",
                                 new HashSet<>(Arrays.asList(tabletsSection, monitorsSection, laptopsSection)),
                                 new HashSet<>(Arrays.asList(john, jane)));
    }

    public static Store getDefaultStore() {
        return defaultStore;
    }

    private static List<Product> buildDefaultTablets() {
        final List<Product> tablets = new ArrayList<>();

        tablets.add(new Product(1, "Google Nexus 7 2013", 200, new Discount(50, Discount.Type.Value)));
        tablets.add(new Product(2, "Apple iPad Pro 9.7", 300, new Discount(10, Discount.Type.Percent)));
        tablets.add(new Product(3, "Samsung Galaxy Tab S2", 350));
        tablets.add(new Product(4, "Microsoft Surface Pro 4", 400));

        return tablets;
    }

    private static List<Product> buildDefaultMonitors() {
        final List<Product> monitors = new ArrayList<>();

        monitors.add(new Product(5, "Samsung CF791", 500));
        monitors.add(new Product(6, "LG 32UD99", 550));
        monitors.add(new Product(7, "Samsung CH711", 600));

        return monitors;
    }

    private static List<Product> buildDefaultLaptops() {
        return Arrays.asList(
                new Product(10, "Lenovo X11", 1500),
                new Product(11, "Apple MacBook Pro", 2000)
        );
    }
}

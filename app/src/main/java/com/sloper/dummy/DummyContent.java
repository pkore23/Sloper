package com.sloper.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) Products.
     */
    public static final List<DummyProduct> PRODUCTS = new ArrayList<DummyProduct>();

    /**
     * A map of sample (dummy) Products, by ID.
     */
    public static final Map<String, DummyProduct> Product_MAP = new HashMap<String, DummyProduct>();

    private static final int COUNT = 25;

    static {
        // Add some sample Products.
        for (int i = 1; i <= COUNT; i++) {
            addProduct(createDummyProduct(i));
        }
    }

    private static void addProduct(DummyProduct Product) {
        PRODUCTS.add(Product);
        Product_MAP.put(Product.id, Product);
    }

    private static DummyProduct createDummyProduct(int position) {
        return new DummyProduct(String.valueOf(position), "Product " + position, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Product: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy Product representing a piece of content.
     */
    public static class DummyProduct {
        public final String id;
        public final String content;
        public final String details;

        public DummyProduct(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}

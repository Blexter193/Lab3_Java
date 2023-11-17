package Shop;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Store {
    private List<Product> products;

    public Store() {
        this.products = new ArrayList<>();
    }

    public Store(List<Product> products) {
        this.products = products;
    }

    public List<Product> getAllProducts() {
        return products;
    }

    public void addProduct(final Product product) {
        products.add(product);
    }

    public void sellProduct(final Product product) {
        if (products.contains(product)) {
            products.remove(product);
        } else {
            throw new Exceptions.ProductNotFoundException("Product not found in the store");
        }
    }

    public List<Product> orderProduct(final String category, final double maxPrice) {
        List<Product> orderedProducts = products.stream()
                .filter(product -> product.getCategory().equalsIgnoreCase(category) && product.getPrice() <= maxPrice)
                .collect(Collectors.toList());

        if (category.equalsIgnoreCase("meat") || category.equalsIgnoreCase("fish")) {
            final String comment = "Don't forget to keep the products in the refrigerator: " +
                    orderedProducts.stream().map(Product::getName).collect(Collectors.joining(", "));
            System.out.println(comment);
        }

        if (!(category.equalsIgnoreCase("fruits") || category.equalsIgnoreCase("vegetables"))) {
            try (final FileWriter fileWriter = new FileWriter("receipt.txt")) {
                for (final Product product : orderedProducts) {
                    fileWriter.write(product.getName() + " - $" + product.getPrice() + "\n");
                }
            } catch (final IOException e) {
                System.err.println("Error writing to the receipt file.");
            }
        }

        return orderedProducts;
    }

    public void editProductPrice(final String productName, final double newPrice) {
        products.stream()
                .filter(product -> product.getName().equals(productName))
                .findFirst()
                .ifPresent(product -> product.setPrice(newPrice));
    }

    public double calculateAveragePrice() {
        if (products.isEmpty()) {
            return 0;
        }
        return products.stream()
                .mapToDouble(Product::getPrice)
                .average()
                .orElse(0);
    }

    public void editReceipt(final Receipt receipt, final List<Product> newProducts) {
        receipt.updatePurchasedProducts(newProducts);
    }
}

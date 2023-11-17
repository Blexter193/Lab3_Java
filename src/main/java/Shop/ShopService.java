package Shop;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ShopService {
    public List<Product> filterAndSortProductsByPrice(final List<Product> products) {
        return products.stream()
                .sorted((p1, p2) -> Double.compare(p1.getPrice(), p2.getPrice()))
                .collect(Collectors.toList());
    }

    public double calculateAveragePrice(final List<Product> products) {
        return products.stream()
                .mapToDouble(Product::getPrice)
                .average()
                .orElse(0);
    }

    public double calculateUserExpenses(final Customer customer, final List<Receipt> receipts) {
        return receipts.stream()
                .filter(receipt -> receipt.isPaid() && receipt.getPurchasedProducts().stream().anyMatch(product -> product.getCategory().equals("categoryName")))
                .mapToDouble(receipt -> receipt.getPurchasedProducts().stream()
                        .mapToDouble(Product::getPrice)
                        .sum())
                .sum();
    }

    public Map<Product, Long> getProductsCountForCustomer(final Customer customer, final List<Receipt> receipts) {
        return receipts.stream()
                .filter(receipt -> receipt.isPaid())
                .flatMap(receipt -> receipt.getPurchasedProducts().stream())
                .collect(Collectors.groupingBy(p -> p, Collectors.counting()));
    }

    public Product findMostPopularProduct(final List<Receipt> receipts) {
        final Map<Product, Long> productCount = receipts.stream()
                .filter(Receipt::isPaid)
                .flatMap(receipt -> receipt.getPurchasedProducts().stream())
                .collect(Collectors.groupingBy(p -> p, Collectors.counting()));

        return productCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public double findMaxDailyIncome(final List<Receipt> receipts) {
        return receipts.stream()
                .filter(Receipt::isPaid)
                .collect(Collectors.groupingBy(Receipt::getPurchaseDate, Collectors.summingDouble(r -> r.getPurchasedProducts().stream().mapToDouble(Product::getPrice).sum())))
                .values().stream()
                .mapToDouble(Double::doubleValue)
                .max()
                .orElse(0);
    }

    public Map<Product, Long> getProductsQuantityForCustomer(final Customer customer, List<Receipt> receipts) {
        return receipts.stream()
                .filter(receipt -> receipt.isPaid() && receipt.getCustomer().equals(customer))
                .flatMap(receipt -> receipt.getPurchasedProducts().stream())
                .collect(Collectors.groupingBy(p -> p, Collectors.counting()));
    }
}

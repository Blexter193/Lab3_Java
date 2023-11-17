package Shop;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        FileService fileService = new FileService();
        List<Product> products = fileService.loadDataFromFile2("products.txt");
        Store store = new Store(products);
        Customer customer1 = new Customer("Oleksiy", "Babych", 18);
        Customer customer2 = new Customer("Vlad", "Kostyk", 20);

        System.out.println("All available products:");
        products.forEach(product -> System.out.println(product.getName()));

        Receipt receipt1 = new Receipt();
        receipt1.addProductToReceipt(products.get(3));
        receipt1.addProductToReceipt(products.get(1));
        receipt1.addProductToReceipt(products.get(2));
        receipt1.addProductToReceipt(products.get(4));
        receipt1.addCustomer(customer1);
        receipt1.markAsPaid();
        PurchaseHistory purchaseHistory = new PurchaseHistory();
        purchaseHistory.addPurchaseToHistory(customer1, receipt1);

        System.out.println("Before editing price:");
        System.out.println(products.get(0).getPrice());
        store.editProductPrice(products.get(0).getName(), 15.0);
        System.out.println("After editing price:");
        System.out.println(products.get(0).getPrice());

        List<Product> orderedProducts = store.orderProduct("fruits", 5.0);

        fileService.generateOrderReceipt("receipt.txt", receipt1);

        System.out.println("Receipt comment:");
        System.out.println(purchaseHistory.generateReceiptComment(receipt1));

        ShopService shopService = new ShopService();
        List<Product> sortedProducts = shopService.filterAndSortProductsByPrice(products);
        double averagePrice = shopService.calculateAveragePrice(products);
        double userExpenses = shopService.calculateUserExpenses(customer1, List.of(receipt1));
        Map<Product, Long> productsCount = shopService.getProductsCountForCustomer(customer1, List.of(receipt1));
        Product mostPopularProduct = shopService.findMostPopularProduct(List.of(receipt1));
        double maxDailyIncome = shopService.findMaxDailyIncome(List.of(receipt1));

        System.out.println("Sorted products by price:");
        sortedProducts.forEach(product -> System.out.println(product.getName()));

        System.out.println("Average price of all products: " + averagePrice);

        System.out.println("Expenses of " + customer1.getFirstName() + ": " + userExpenses);

        System.out.println("Products count for " + customer1.getFirstName() + ": " + productsCount);

        System.out.println("Most popular product: " + mostPopularProduct.getName());

        System.out.println("Max daily income: " + maxDailyIncome);

        Product product1 = new Product("Apple", 2.0, "Fruit");
        Product product2 = new Product("Beef", 6.5, "Meat");
        Product product3 = new Product("Tomato", 1.0, "Vegetable");

        List<Product> unpaidProducts = new ArrayList<>();
        unpaidProducts.add(product1);
        unpaidProducts.add(product2);

        List<Product> paidProducts = new ArrayList<>();
        paidProducts.add(product2);
        paidProducts.add(product3);

        Receipt unpaidReceipt = new Receipt(unpaidProducts);
        Receipt paidReceipt = new Receipt(paidProducts);
        paidReceipt.markAsPaid();

        System.out.println("Editing unpaid receipt:");
        unpaidReceipt.updatePurchasedProducts(paidProducts);

        System.out.println("\nEditing paid receipt:");
        paidReceipt.updatePurchasedProducts(unpaidProducts);

        List<Receipt> receipts = new ArrayList<>();
        receipts.add(receipt1);

        Customer customer = new Customer("Ім'я Користувача", "ID Користувача", 22);
        Customer selectedCustomer = customer1;
        Map<Product, Long> productsQuantity = shopService.getProductsQuantityForCustomer(selectedCustomer, receipts);
        System.out.println("Products Quantity for " + selectedCustomer.getName() + ": " + productsQuantity);
    }


}


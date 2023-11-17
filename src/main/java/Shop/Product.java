package Shop;

public class Product {
    private String name;
    private double price;
    private String category;

    public Product(final String name, final double price, final String category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(final double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(final String category) {
        this.category = category;
    }
    @Override
    public String toString() {
        return "Product: " + this.getName() + ", Price: " + this.getPrice();
    }
}

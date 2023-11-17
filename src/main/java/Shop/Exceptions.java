package Shop;

public class Exceptions extends RuntimeException {
    public Exceptions(String message) {
        super(message);
    }

    public static class ProductNotFoundException extends Exceptions {
        public ProductNotFoundException(String message) {
            super(message);
        }
    }

    public static class InvalidCategoryException extends Exceptions {
        public InvalidCategoryException(String message) {
            super(message);
        }
    }
}

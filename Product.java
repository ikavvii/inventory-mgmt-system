public class Product {
    public int id;
    public String name;
    public int quantity;
    public double price;
    
    public Product(int productId, String productName, int productQuantity, double productPrice) throws InvalidProductException {
        if (productId <= 0) {
            throw new InvalidProductException("Invalid product ID");
        }
        if (productName == null || productName.trim().isEmpty()) {
            throw new InvalidProductException("Invalid product name");
        }
        if (productQuantity < 0) {
            throw new InvalidProductException("Invalid product quantity");
        }
        if (productPrice < 0) {
            throw new InvalidProductException("Invalid product price");
        }
        id = productId;
        name = productName;
        quantity = productQuantity;
        price = productPrice;
    }
}
public class Product {
    public int id;
    public String name;
    public int quantity;
    public double price;
    
    public Product(int productId, String productName, int productQuantity, double productPrice) {
        id = productId;
        name = productName;
        quantity = productQuantity;
        price = productPrice;
    }
}
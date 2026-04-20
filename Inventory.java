import java.util.LinkedList;

public class Inventory<T extends Product> {
    public LinkedList<T> products = new LinkedList<>();

    public void addProduct(T product) {
        products.add(product);
    }

    public void removeProduct(T product) {
        products.remove(product);
    }

    public T updateProduct(T product, String update) {
        if (update.startsWith("#")) {
            product.quantity = Integer.parseInt(update.substring(1));
        } else if (update.startsWith("$")) {
            product.price = Double.parseDouble(update.substring(1));
        } else {
            return null;
        }
        return product;
    }

    public T searchById(int id) {
        return products.stream().filter(p -> p.id == id).findFirst().orElse(null);
    }

    public T searchByName(String name){
        for (T  product : products) {
            if (product.name.toLowerCase().contains(name)) {
                return product;
            }
        }
        return null;
    }

    public void displayProducts() {
        for (T  product : products) {
            System.out.println(product);
        }
        System.out.println("### END ###");
    }
}

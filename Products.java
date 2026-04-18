import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.LinkedList;
import java.util.Scanner;

public class Products {

    public static LinkedList<Product> products = new LinkedList<>();

    public static void write(String destination) {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(destination, true))) {
            // write to file

            for (Product product : products) {
                dos.writeInt(product.id);
                dos.writeBytes(product.name);
                dos.writeInt(product.quantity);
                dos.writeDouble(product.price);
            }

            System.out.println("done");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void read(String source) {

        try (
                Scanner sc = new Scanner(new File(source))) {
            sc.useDelimiter(",");

            // read the seed and load products
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] data = line.split(", ");
                int id = Integer.parseInt(data[0]);
                String name = data[1];
                int quantity = Integer.parseInt(data[2]);
                double price = Double.parseDouble(data[3]);
                products.add(new Product(id, name, quantity, price));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

import java.io.*;
import java.util.EmptyStackException;
import java.util.Scanner;
import java.util.Stack;

public class InventoryManager {

    public Stack<Operations> operationsHistory = new Stack<>();
    public Stack<Product> productHistory = new Stack<>();

    public Inventory<Product> inventory = new Inventory<>();

    public void write(String destination) {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(destination))) {

            // write to file
            for (Product product : inventory.products) {
                dos.writeInt(product.id);
                dos.writeUTF(product.name);
                dos.writeInt(product.quantity);
                dos.writeDouble(product.price);
            }

            System.out.println("WRITE | Products data");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void read(String source) throws FileNotFoundException {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(source))) {
            // read from file
            while (dis.available() > 0) {
                int id = dis.readInt();
                String name = dis.readUTF();
                int quantity = dis.readInt();
                double price = dis.readDouble();
                try {
                    Product p = new Product(id, name, quantity, price);
                    inventory.addProduct(p);

                } catch (InvalidProductException e) {
                    System.out.println(e.getMessage());
                }
            }
            System.out.println("READ | Products data");
        } catch (Exception e) {
            if (e instanceof FileNotFoundException) {
                throw new FileNotFoundException("Data not found. Seeding sample data...");
            }
            System.out.println(e.getMessage());
        }
    }

    public void seed(String source) {

        try (Scanner sc = new Scanner(new File(source))) {
            sc.useDelimiter(",");

            // read the seed and load products
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] data = line.split(", ");
                int id = Integer.parseInt(data[0]);
                String name = data[1];
                int quantity = Integer.parseInt(data[2]);
                double price = Double.parseDouble(data[3]);
                Product product = new Product(id, name, quantity, price);
                inventory.addProduct(product);
            }
            System.out.println("READ | Seed data");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void menu() {
        int choice;
        Scanner sc = new Scanner(System.in);
        try {
            read("products.dat");
        } catch (FileNotFoundException e) {
            seed("seed.csv");
        }
        System.out.println();
        System.out.println("______________________________________");
        while (true) {
            System.out.println("***** INVENTORY MANAGEMENT SYSTEM ****");
            System.out.println("______________________________________");
            System.out.println();
            System.out.println("1. ADD Product(id,name,quantity,price)");
            System.out.println("2. REMOVE Product(id)-----------------");
            System.out.println("3. UPDATE Product(id, price/quantity)-");
            System.out.println("4. SEARCH Product(id/name)------------");
            System.out.println("5. DISPLAY All Products---------------");
            System.out.println("6. UNDO Last Operation----------------");
            System.out.println("______________________________________");
            System.out.println();
            System.out.println("Enter (0 to exit): ");

            choice = sc.nextInt();

            sc.nextLine();

            switch (choice) {
                case 1:
                    try {
                        System.out.println("ADD | Enter product details: ");
                        System.out.println("ADD | Product ID: ");
                        int id = sc.nextInt(); sc.nextLine();
                        System.out.println("ADD | Product NAME: ");
                        String name = sc.nextLine();
                        System.out.println("ADD | Product Quantity: ");
                        int quantity = sc.nextInt(); sc.nextLine();
                        System.out.println("ADD | Product Price: ");
                        double price = sc.nextDouble(); sc.nextLine();
                        Product product = new Product(id, name, quantity, price);
                        inventory.addProduct(product);

                        operationsHistory.add(Operations.ADD);
                        productHistory.add(product);
                        System.out.printf("ADD Product(%d, %s, %d, %f)\n", id, name, quantity, price);

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    System.out.println();
                    break;
                case 2:
                    try {
                        System.out.println("REMOVE | Enter product id: ");
                        int id = sc.nextInt();
                        Product product = inventory.searchById(id);
                        if (product != null) {
                            inventory.removeProduct(product);

                            operationsHistory.add(Operations.REMOVE);
                            productHistory.add(product);
                            System.out.println("REMOVE | " + product);
                        } else {
                            System.out.println("REMOVE | Invalid Product ID.");
                        }

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    System.out.println();
                    break;
                case 3:
                    try {
                        System.out.println("UPDATE | Enter Product ID: ");
                        int id = sc.nextInt(); sc.nextLine();
                        Product product = inventory.searchById(id);
                        if (product != null) {
                            System.out.println("UPDATE | Input as #newQuantity / $newPrice");
                            System.out.println("UPDATE | " + product.name + ": ");
                            String input =  sc.nextLine();
                            System.out.println(input);
                            Product oldProduct = new Product(product);
                            System.out.println(oldProduct);
                            Product newProduct = inventory.updateProduct(product, input);
                            System.out.println(newProduct);
                            if (newProduct != null) {
                                productHistory.add(oldProduct);
                                operationsHistory.add(Operations.UPDATE);
                                System.out.println("UPDATE | " + newProduct);
                            } else {
                                System.out.println("UPDATE | Invalid input format.");
                            }
                        } else {
                            System.out.println("UPDATE | Invalid Product ID.");
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    System.out.println();
                    break;
                case 4:
                    try {
                        System.out.println("SEARCH | Enter Product ID/NAME: ");
                        Product product;
                        if (sc.hasNextInt()) {
                            int id = sc.nextInt();
                            product = inventory.searchById(id);
                        } else {
                            String name = sc.nextLine();
                            product = inventory.searchByName(name);
                        }
                        if (product != null) {
                            System.out.println(product);
                        } else {
                            System.out.println("SEARCH | Product not found");
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    System.out.println();
                    break;
                case 5:
                    System.out.println("DISPLAY | All Products");
                    System.out.println("______________________________________");
                    inventory.displayProducts();
                    System.out.println();
                    break;
                case 6:
                    System.out.println("UNDO | Sure? Y/N: ");
                    String sure = sc.nextLine();
                    if (sure.equalsIgnoreCase("y") || sure.toLowerCase().contains("yes")) {
                        try {
                            Operations op = operationsHistory.pop();
                            if (op == Operations.ADD) {
                                inventory.removeProduct(productHistory.pop());
                                System.out.println("UNDO | Add");
                            } else if (op == Operations.REMOVE) {
                                inventory.addProduct(productHistory.pop());
                                System.out.println("UNDO | Remove");
                            } else if (op == Operations.UPDATE) {
                                Product oldProduct = productHistory.pop();
                                Product currentProduct = inventory.searchById(oldProduct.id);
                                // undo
                                if (currentProduct != null) {
                                    currentProduct.quantity = oldProduct.quantity;
                                    currentProduct.price = oldProduct.price;
                                }
                                System.out.println("UNDO | Update");
                            }
                        } catch (EmptyStackException e) {
                            System.out.println("UNDO | Nothing to undo.");
                        }
                    } else {
                        System.out.println("UNDO | Invalid input format.");
                    }
                    System.out.println();
                    break;
                case 0:
                    write("products.dat");
                    System.out.println("SAVE & EXIT | Products data");
                    System.out.println();
                    return;
                default:
                    System.out.println("=== Invalid choice ===");
                    System.out.println();
            }
        }
    }
}
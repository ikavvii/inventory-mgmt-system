import java.util.Scanner;
import java.util.Stack;

public class InventoryManager<T extends Product> {
    public static Stack<String> operationHistory;

    public static Scanner sc = new Scanner(System.in);

    public static void menu() {
        int choice;
        while (true) {
            System.out.println("***** INVENTORY MANAGEMENT SYSTEM ****");
            System.out.println("______________________________________");
            System.out.println();
            System.out.println("1. ADD Product(id,name,quantity,price)");
            System.out.println("2. REMOVE Product(id)-----------------");
            System.out.println("3. UPDATE Product(id, price/quantity)-");
            System.out.println("4. SEARCH Product(id/name)------------");
            System.out.println("5. DISPLAY All Products---------------");
            System.out.println("______________________________________");
            System.out.println();
            System.out.println("Enter (0 to exit): ");
            
            choice = sc.nextInt();

            sc.nextLine();

            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    removeProduct();
                    break;
                case 3:
                    updateProduct();

                    break;
                case 4: 
                    searchProduct();
                    break;
                case 5: 
                    displayProducts();
                    break;
                case 0:
                    sc.close();
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }

    }

    public static void addProduct() {


    }

    public static void removeProduct() {

    }

    public static void updateProduct() {

    }

    public static void searchProduct() {

    }

    public static void displayProducts() {

    }

}
import java.util.Scanner;
import java.util.Stack;

public class InventoryManager<T> {
    public static Stack<String> operationHistory;

    public static void menu() {
        Scanner sc = new Scanner(System.in);
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
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4: 
                    break;
                case 5: 
                    break;
                case 0:
                    sc.close();
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }

    }

}
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;

public class Inventory_Management_System {
    static ArrayList<String> productNames = new ArrayList<>();
    static ArrayList<Integer> productQuantities = new ArrayList<>();
    static ArrayList<Float> productPrices = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void addProduct() {
        System.out.print("Enter product name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter quantity: ");
        int quantity = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter price per unit: ");
        float price = Float.parseFloat(scanner.nextLine());

        for (int i = 0; i < productNames.size(); i++) {
            if (productNames.get(i).equalsIgnoreCase(name)) {
                productQuantities.set(i, productQuantities.get(i) + quantity);
                System.out.println("Product '" + name + "' updated successfully!\n");
                return;
            }
        }

        productNames.add(name);
        productQuantities.add(quantity);
        productPrices.add(price);
        System.out.println("Product '" + name + "' added successfully!\n");
    }

    public static void viewInventory() {
        if (productNames.isEmpty()) {
            System.out.println("Inventory is empty.\n");
            return;
        }

        System.out.println("Current Inventory:");
        System.out.println("Name\tQuantity\tPrice");
        for (int i = 0; i < productNames.size(); i++) {
            System.out.println(productNames.get(i) + "\t" + productQuantities.get(i) + "\t\t$" + productPrices.get(i));
        }
        System.out.println();
    }

    public static void updateProduct() {
        System.out.print("Enter product name to update: ");
        String name = scanner.nextLine().trim();

        for (int i = 0; i < productNames.size(); i++) {
            if (productNames.get(i).equalsIgnoreCase(name)) {
                System.out.print("Enter new quantity: ");
                int newQty = Integer.parseInt(scanner.nextLine());
                productQuantities.set(i, newQty);
                System.out.println("Updated " + name + " stock to " + newQty + "\n");
                return;
            }
        }

        System.out.println("Product not found!\n");
    }

    public static void deleteProduct() {
        System.out.print("Enter product name to delete: ");
        String name = scanner.nextLine().trim();

        for (int i = 0; i < productNames.size(); i++) {
            if (productNames.get(i).equalsIgnoreCase(name)) {
                productNames.remove(i);
                productQuantities.remove(i);
                productPrices.remove(i);
                System.out.println("Deleted '" + name + "' from inventory\n");
                return;
            }
        }

        System.out.println("Product not found!\n");
    }

    public static void searchProduct() {
        System.out.print("Enter product name to search: ");
        String name = scanner.nextLine().trim();

        for (int i = 0; i < productNames.size(); i++) {
            if (productNames.get(i).equalsIgnoreCase(name)) {
                System.out.println(name + ": Quantity = " + productQuantities.get(i) + ", Price = $" + productPrices.get(i) + "\n");
                return;
            }
        }

        System.out.println("Product not found!\n");
    }

    public static void saveInventory() throws Exception {
        FileWriter writer = new FileWriter("inventory.csv");
        writer.write("Product Name,Quantity,Price\n");

        for (int i = 0; i < productNames.size(); i++) {
            writer.write(productNames.get(i) + "," + productQuantities.get(i) + "," + productPrices.get(i) + "\n");
        }

        writer.close();
        System.out.println("Inventory saved to inventory.csv\n");
    }

    public static void main(String[] args) throws Exception {
        while (true) {
            System.out.println("1. Add Product");
            System.out.println("2. View Inventory");
            System.out.println("3. Update Stock");
            System.out.println("4. Delete Product");
            System.out.println("5. Search Product");
            System.out.println("6. Save & Exit");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                addProduct();
            } else if (choice.equals("2")) {
                viewInventory();
            } else if (choice.equals("3")) {
                updateProduct();
            } else if (choice.equals("4")) {
                deleteProduct();
            } else if (choice.equals("5")) {
                searchProduct();
            } else if (choice.equals("6")) {
                saveInventory();
                System.out.println("Exiting program...");
                break;
            } else {
                System.out.println("Invalid choice! Please try again.\n");
            }
        }
    }
}

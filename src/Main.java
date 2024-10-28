import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Create a Store instance
        Store store = new Store();

        // Create a scanner instance
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the store management system!");
        // Initiate user input to -1 for simplicity
        int userInput = -1;

        // Until user chooses option "Exit the program", continue the program
        while(userInput != 0) {
            // Print out the menu of options
            System.out.println();
            System.out.println("Enter one of these numbers to perform their respective function from the list: ");
            System.out.println("1. Add a new product");
            System.out.println("2. Display all products");
            System.out.println("3. Display total value of the inventory");
            System.out.println("4. Search for a product by its ProductID");
            System.out.println("5. Remove a product by its ProductID");
            System.out.println("0. Exit the program");

            // Ask for user input
            userInput = scanner.nextInt();

            // Perform the respective action based on user's choice
            switch (userInput) {
                case 0:
                    // Say "Goodbye" before the user exits the program
                    System.out.println("Goodbye!");
                    break;
                case 1: {
                    // Before letting the user enter information about the new product,
                    //     check if the store is already full, and let them know
                    if (store.isFull()) {
                        System.out.println("Reached product limit! Please remove at least one item before adding a new one.");
                        break;
                    }
                    // Ask for productID first, and see if there already exists a product with such ID
                    System.out.println("Enter the product ID: ");
                    String productID = scanner.next();
                    // Keep asking the user for a new productID until an existing
                    //     product with such an ID is NOT found
                    while(store.getProduct(productID) != null) {
                        System.out.println("Product with product ID " + productID + " already exists. Please choose a different product ID.");
                        productID = scanner.next();
                    }
                    scanner.nextLine();

                    // Ask for name of new product
                    System.out.println("Enter the name of the product: ");
                    String productName = scanner.nextLine();

                    // Ask for quantity of new product
                    System.out.println("Enter the quantity in stock: ");
                    int quantity = scanner.nextInt();

                    // Ask for price of new product
                    System.out.println("Enter the price of the product: ");
                    double price = scanner.nextDouble();

                    // Try adding new instance of product to the store
                    Product product = store.addProduct(new Product(productID, productName, quantity, price));

                    // If method "addProduct()" returned null, it means there wasn't any empty slots
                    // Otherwise, let the user know their product was added successfully
                    if(product != null) {
                        System.out.println("Product added successfully: ");
                        System.out.println("Product ID: " + product.getProductID() + ";\nProduct Name: " + product.getProductName() + ";\nQuantity: " + product.getQuantity() + ";\nPrice: " + product.getPrice() + ";");
                    } else {
                        System.out.println("Reached product limit!");
                    }
                    break;
                }
                case 2: {
                    // Call the store method to display all products
                    store.displayAllProducts();
                    break;
                }
                case 3: {
                    // Call the store method to calculate the total stock value and print it out
                    System.out.println("The total stock value: " + store.getTotalStockValue());
                    break;
                }
                case 4: {
                    // Ask user for productID
                    System.out.println("Enter the product ID: ");
                    String productID = scanner.next();
                    // Call method to get the product with such ID from the store
                    Product productFound = store.getProduct(productID);
                    // If returned null, no product was found, otherwise, print its attributes
                    if(productFound != null) {
                        System.out.println("Product with product ID " + productID + " was found:");
                        System.out.println("Product Name: " + productFound.getProductName());
                        System.out.println("Product Price: " + productFound.getPrice());
                        System.out.println("Product Quantity: " + productFound.getQuantity());
                    } else {
                        System.out.println("Product with product ID " + productID + " was not found.");
                    }
                    break;
                }
                case 5: {
                    // Ask user for productID
                    System.out.println("Enter the product ID: ");
                    String productID = scanner.next();

                    // Call method to remove the product with such ID from the store
                    Product productRemoved = store.removeProduct(productID);

                    // If returned null, no product with such ID was found, otherwise, print the attributes
                    //     of the product removed
                    if(productRemoved != null) {
                        System.out.println("Product with product ID " + productID + " was removed successfully:");
                        System.out.println("Removed " + productRemoved.getProductName() + "*" + productRemoved.getQuantity() + " $" + productRemoved.getPrice() + " each");
                    } else {
                        System.out.println("Product with product ID " + productID + " was not found.");
                    }
                    break;
                }
                default: {
                    // Let user know their input wasn't valid
                    System.out.println("Invalid user input!");
                    break;
                }
            }
        }
    }
}
// Create the Product class
class Product {
    // Create all necessary attributes and make them inaccessible outside the class
    private String productID;
    private String productName;
    private int quantity;
    private double price;

    // Constructor to allow for creation of instances of the Product class, requiring all attributes
    Product(String productID, String productName, int quantity, double price) {
        this.productID = productID;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    // Public method to allow getting of productID outside the class
    String getProductID() {
        return productID;
    }

    // Public method to allow setting of productID outside the class
    void setProductID(String productID) {
        this.productID = productID;
    }

    // Public method to allow getting of productName outside the class
    String getProductName() {
        return productName;
    }

    // Public method to allow setting of productName outside the class
    void setProductName(String productName) {
        this.productName = productName;
    }

    // Public method to allow getting of quantity outside the class
    int getQuantity() {
        return quantity;
    }

    // Public method to allow setting of quantity outside the class
    void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Public method to allow getting of price outside the class
    double getPrice() {
        return price;
    }

    // Public method to allow setting of price outside the class
    void setPrice(double price) {
        this.price = price;
    }
}

// Create the Store class
class Store {
    // Create the empty list of products of size 5, inaccessible outside the class
    private Product[] products = new Product[5];

    // Empty constructor
    Store() {}

    // Public method to check whether the store has no empty spaces left
    boolean isFull() {
        for (Product product : products) {
            if (product == null) {
                return false;
            }
        }
        return true;
    }

    // Method to add a new product to the store
    Product addProduct(Product newProduct) {
        for (int i = 0; i < products.length; i++) {
            // Go through each product in the list, until we find an empty slot to put the new product in
            if(products[i] == null) {
                products[i] = newProduct;
                return products[i];
            }
        }
        // If no empty slot found, return null to indicate no new product was added
        return null;
    }

    // Public method to display all products in the store
    void displayAllProducts() {
        for (Product product : products) {
            if(product != null) {
                System.out.println(product.getProductID() + ": " + product.getProductName() + "*" + product.getQuantity() + " $" + product.getPrice() + " each;");
            } else {
                System.out.println("Empty slot.");
            }
        }
    }

    // Public method to calculate current total stock value in the store
    double getTotalStockValue() {
        // Declare and initiate a total to 0
        double total = 0;
        for (Product product : products) {
            if(product != null) {
                // Add the product quantity times its price to the total
                total += product.getQuantity() * product.getPrice();
            }
        }
        return total;
    }

    // Public method to allow getting a specific product by its ID
    Product getProduct(String productID) {
        for (Product product : products) {
            // Skip empty slots
            if(product == null) continue;
            // Compare each productID with the given ID, and return the product if found
            if(product.getProductID().equals(productID)) {
                return product;
            }
        }
        // Return null to indicate product with such ID was not found
        return null;
    }

    // Public method to allow removing a specific product by its ID
    Product removeProduct(String productID) {
        for (int i = 0; i < products.length; i++) {
            // Skip empty slots
            if(products[i] == null) continue;
            // Compare each productID with the given ID
            if (products[i].getProductID().equals(productID)) {
                Product removedProduct = products[i];
                // Make the product an empty slot in the store, thereby removing it
                products[i] = null;
                // Return the removed product if found
                return removedProduct;
            }
        }
        // Return null to indicate no product was removed
        return null;
    }
}
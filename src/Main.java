import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Store store = new Store();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the store management system!");
        int userInput = -1;

        while(userInput != 0) {
            System.out.println();
            System.out.println("Enter one of these numbers to perform their respective function from the list: ");
            System.out.println("1. Add a new product");
            System.out.println("2. Display all products");
            System.out.println("3. Display total value of the inventory");
            System.out.println("4. Search for a product by its ProductID");
            System.out.println("5. Remove a product by its ProductID");
            System.out.println("0. Exit the program");

            userInput = scanner.nextInt();

            switch (userInput) {
                case 0:
                    System.out.println("Goodbye!");
                    break;
                case 1: {
                    if (store.isFull()) {
                        System.out.println("Reached product limit! Please remove at least one item before adding a new one.");
                        break;
                    }
                    System.out.println("Enter the product ID: ");
                    String productID = scanner.next();
                    while(store.getProduct(productID) != null) {
                        System.out.println("Product with product ID " + productID + " already exists. Please choose a different product ID.");
                        productID = scanner.next();
                    }
                    scanner.nextLine();
                    System.out.println("Enter the name of the product: ");
                    String productName = scanner.nextLine();
                    System.out.println("Enter the quantity in stock: ");
                    int quantity = scanner.nextInt();
                    System.out.println("Enter the price of the product: ");
                    double price = scanner.nextDouble();

                    Product product = store.addProduct(new Product(productID, productName, quantity, price));

                    if(product != null) {
                        System.out.println("Product added successfully: ");
                        System.out.println("Product ID: " + product.getProductID() + ";\nProduct Name: " + product.getProductName() + ";\nQuantity: " + product.getQuantity() + ";\nPrice: " + product.getPrice() + ";");
                    } else {
                        System.out.println("Reached product limit!");
                    }
                    break;
                }
                case 2: {
                    store.displayAllProducts();
                    break;
                }
                case 3: {
                    System.out.println("The total stock value: " + store.getTotalStockValue());
                    break;
                }
                case 4: {
                    System.out.println("Enter the product ID: ");
                    String productID = scanner.next();
                    Product productFound = store.getProduct(productID);
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
                    System.out.println("Enter the product ID: ");
                    String productID = scanner.next();
                    Product productRemoved = store.removeProduct(productID);
                    if(productRemoved != null) {
                        System.out.println("Product with product ID " + productID + " was removed successfully:");
                        System.out.println("Removed " + productRemoved.getProductName() + "*" + productRemoved.getQuantity() + " $" + productRemoved.getPrice() + " each");
                    } else {
                        System.out.println("Product with product ID " + productID + " was not found.");
                    }
                    break;
                }
                default: {
                    System.out.println("Invalid user input!");
                    break;
                }
            }
        }
    }
}

class Product {
    private String productID;
    private String productName;
    private int quantity;
    private double price;

    Product(String productID, String productName, int quantity, double price) {
        this.productID = productID;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    String getProductID() {
        return productID;
    }

    void setProductID(String productID) {
        this.productID = productID;
    }

    String getProductName() {
        return productName;
    }

    void setProductName(String productName) {
        this.productName = productName;
    }

    int getQuantity() {
        return quantity;
    }

    void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    double getPrice() {
        return price;
    }

    void setPrice(double price) {
        this.price = price;
    }
}

class Store {
    private Product[] products = new Product[5];

    Store() {}

    boolean isFull() {
        for (Product product : products) {
            if (product == null) {
                return false;
            }
        }
        return true;
    }

    Product addProduct(Product newProduct) {
        for (int i = 0; i < products.length; i++) {
            if(products[i] == null) {
                products[i] = newProduct;
                return products[i];
            }
        }
        return null;
    }

    void displayAllProducts() {
        for (Product product : products) {
            if(product != null) {
                System.out.println(product.getProductID() + ": " + product.getProductName() + "*" + product.getQuantity() + " $" + product.getPrice() + " each;");
            } else {
                System.out.println("Empty slot.");
            }
        }
    }

    double getTotalStockValue() {
        double total = 0;
        for (Product product : products) {
            if(product != null) {
                total += product.getQuantity() * product.getPrice();
            }
        }
        return total;
    }

    Product getProduct(String productID) {
        for (Product product : products) {
            if(product == null) continue;
            if(product.getProductID().equals(productID)) {
                return product;
            }
        }
        return null;
    }

    Product removeProduct(String productID) {
        for (int i = 0; i < products.length; i++) {
            if(products[i] == null) continue;
            if (products[i].getProductID().equals(productID)) {
                Product removedProduct = products[i];
                products[i] = null;
                return removedProduct;
            }
        }
        return null;
    }
}
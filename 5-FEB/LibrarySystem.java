import java.util.HashMap;
import java.util.Scanner;

public class LibrarySystem {

    static class Book {
      String author;
      int quantity;

      Book(String author, int quantity){
        this.author = author;
        this.quantity = quantity;
      }
    }

    static HashMap<String, Book> library = new HashMap<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;

        do {
            System.out.println("\n--- Library System ---");
            System.out.println("1. Add Books");
            System.out.println("2. Borrow Books");
            System.out.println("3. Return Books");
            System.out.println("4. Exit");
            System.out.println("Enter Choice:");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> addBooks();
                case 2 -> borrowBooks();
                case 3 -> returnBooks();
                case 4 -> System.out.println("Exiting Library");
                default -> System.out.println("Invalid Choice! Please try again.");
            }
        } while (choice != 4);
    }


    public static void addBooks() {
        System.out.print("Enter book title:");
        String title = sc.nextLine();
        System.out.print("Enter author name:");
        String author = sc.nextLine();
        System.out.print("Enter quantity:");
        int qty = sc.nextInt();
        sc.nextLine();

        if (library.containsKey(title)) {
            library.get(title).quantity += qty;
            System.out.println("Book exits. Updated quantity to" + library.get(title).quantity);
        } else {
            library.put(title, new Book(author, qty));
            System.out.println("Book added successfully!");
        }
    }

    // Borrow Books Method
    public static void borrowBooks() {
        System.out.print("Enter book title to borrow: ");
        String title = sc.nextLine();

        if (library.containsKey(title)) {
            System.out.print("Enter number of books to borrow: ");
            int qty = sc.nextInt();
            sc.nextLine(); // consume newline

            if (library.get(title).quantity >= qty) {
                library.get(title).quantity -= qty;
                System.out.println("Borrowed " + qty + " copies of '" + title + "'.");
            } else {
                System.out.println("Not enough copies available! Available: " + library.get(title).quantity);
            }
        } else {
            System.out.println("Book not found in the library!");
        }
    }

    // Return Books Method
    public static void returnBooks() {
        System.out.print("Enter book title to return: ");
        String title = sc.nextLine();

        if (library.containsKey(title)) {
            System.out.print("Enter number of books to return: ");
            int qty = sc.nextInt();
            sc.nextLine(); // consume newline

            library.get(title).quantity += qty;
            System.out.println("Returned " + qty + " copies of '" + title + "'.");
        } else {
            System.out.println("This book does not belong to this library!");
        }
    }
    
    
}




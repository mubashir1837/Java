import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n===== s =====");
            System.out.println("1. Add New Student");
            System.out.println("2. Update Student Information");
            System.out.println("3. View Student Details");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter ID: ");
                    String id = scanner.nextLine();

                    System.out.print("Enter Age: ");
                    int age = scanner.nextInt();

                    System.out.print("Enter Grade: ");
                    double grade = scanner.nextDouble();
                    scanner.nextLine();

                    StudentManagement.addStudent(name, id, age, grade);
                    break;

                
                case 2:
                    System.out.print("Enter Student ID to Update: ");
                    String updateId = scanner.nextLine();

                    System.out.print("Enter New Name: ");
                    String newName = scanner.nextLine();

                    System.out.print("Enter New Age: ");
                    int newAge = scanner.nextInt();

                    System.out.print("Enter New Grade: ");
                    double newGrade = scanner.nextDouble();
                    scanner.nextLine();

                    StudentManagement.updateStudent(updateId, newName, newAge, newGrade);
                    break;

                case 3:
                    System.out.print("Enter Student ID to View: ");
                    String viewId = scanner.nextLine();
                    StudentManagement.viewStudent(viewId);
                    break;

                case 4:
                    System.out.println("Exiting system...");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 4);

        scanner.close();
    }
}

import java.util.*;

/**
 * VehicleRentalSystem Main Program
 * 
 * This program manages a car rental agency's vehicle fleet. It allows users to:
 * - Create different types of vehicles (Car, Motorcycle, Truck)
 * - Input detailed information for each vehicle
 * - Display comprehensive vehicle information
 * - Handle errors gracefully
 * 
 * The system demonstrates the use of interfaces to enforce consistent behavior
 * across different vehicle types while allowing for type-specific attributes.
 * 
 * @author Car Rental Agency System
 * @version 1.0
 */
public class VehicleRentalSystem {
    
    // Collection to store all vehicles
    private static List<Vehicle> vehicles = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    
    /**
     * Main method - Entry point of the application
     * 
     * @param args - Command line arguments (not used)
     */
    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║   CAR RENTAL AGENCY - VEHICLE SYSTEM     ║");
        System.out.println("║              Version 1.0                  ║");
        System.out.println("╚══════════════════════════════════════════╝\n");
        
        boolean continueProgram = true;
        
        while (continueProgram) {
            displayMainMenu();
            
            try {
                String choice = getUserInput("Enter your choice (1-4): ");
                
                switch (choice) {
                    case "1":
                        addCar();
                        break;
                    case "2":
                        addMotorcycle();
                        break;
                    case "3":
                        addTruck();
                        break;
                    case "4":
                        continueProgram = false;
                        System.out.println("\nThank you for using the Vehicle Rental System. Goodbye!");
                        break;
                    default:
                        System.out.println("❌ Invalid choice. Please enter 1-4.\n");
                }
                
                if (choice.equals("1") || choice.equals("2") || choice.equals("3")) {
                    displayAllVehicles();
                }
            } catch (Exception e) {
                System.out.println("❌ An error occurred: " + e.getMessage());
                System.out.println("Please try again.\n");
            }
        }
        
        scanner.close();
    }
    
    /**
     * Displays the main menu options
     */
    private static void displayMainMenu() {
        System.out.println("═══════════════════════════════════════════");
        System.out.println("           MAIN MENU");
        System.out.println("═══════════════════════════════════════════");
        System.out.println("1. Add a Car");
        System.out.println("2. Add a Motorcycle");
        System.out.println("3. Add a Truck");
        System.out.println("4. Exit");
        System.out.println("═══════════════════════════════════════════\n");
    }
    
    /**
     * Handles the process of adding a car to the system
     * Prompts user for all required car attributes
     */
    private static void addCar() {
        System.out.println("\n--- ADD A NEW CAR ---\n");
        
        try {
            // Get basic vehicle information
            String make = getUserInput("Enter car make (manufacturer): ");
            String model = getUserInput("Enter car model: ");
            int year = getIntInput("Enter year of manufacture: ");
            
            // Create car object
            Car car = new Car(make, model, year);
            
            // Get car-specific information
            int doors = getIntInput("Enter number of doors (minimum 2): ");
            car.setNumberOfDoors(doors);
            
            String fuelType = getUserInput("Enter fuel type (petrol/diesel/electric): ");
            car.setFuelType(fuelType);
            
            // Add to vehicles list
            vehicles.add(car);
            System.out.println("\n✅ Car added successfully!\n");
            
        } catch (IllegalArgumentException e) {
            System.out.println("\n❌ Error: " + e.getMessage() + "\n");
        } catch (InputMismatchException e) {
            System.out.println("\n❌ Error: Invalid input format. Please enter valid values.\n");
            scanner.nextLine(); // Clear the invalid input
        }
    }
    
    /**
     * Handles the process of adding a motorcycle to the system
     * Prompts user for all required motorcycle attributes
     */
    private static void addMotorcycle() {
        System.out.println("\n--- ADD A NEW MOTORCYCLE ---\n");
        
        try {
            // Get basic vehicle information
            String make = getUserInput("Enter motorcycle make (manufacturer): ");
            String model = getUserInput("Enter motorcycle model: ");
            int year = getIntInput("Enter year of manufacture: ");
            
            // Create motorcycle object
            Motorcycle motorcycle = new Motorcycle(make, model, year);
            
            // Get motorcycle-specific information
            int wheels = getIntInput("Enter number of wheels (2 or 3): ");
            motorcycle.setNumberOfWheels(wheels);
            
            String type = getUserInput("Enter motorcycle type (sport/cruiser/off-road): ");
            motorcycle.setMotorcycleType(type);
            
            // Add to vehicles list
            vehicles.add(motorcycle);
            System.out.println("\n✅ Motorcycle added successfully!\n");
            
        } catch (IllegalArgumentException e) {
            System.out.println("\n❌ Error: " + e.getMessage() + "\n");
        } catch (InputMismatchException e) {
            System.out.println("\n❌ Error: Invalid input format. Please enter valid values.\n");
            scanner.nextLine(); // Clear the invalid input
        }
    }
    
    /**
     * Handles the process of adding a truck to the system
     * Prompts user for all required truck attributes
     */
    private static void addTruck() {
        System.out.println("\n--- ADD A NEW TRUCK ---\n");
        
        try {
            // Get basic vehicle information
            String make = getUserInput("Enter truck make (manufacturer): ");
            String model = getUserInput("Enter truck model: ");
            int year = getIntInput("Enter year of manufacture: ");
            
            // Create truck object
            Truck truck = new Truck(make, model, year);
            
            // Get truck-specific information
            double capacity = getDoubleInput("Enter cargo capacity (in tons): ");
            truck.setCargoCapacity(capacity);
            
            String transmission = getUserInput("Enter transmission type (manual/automatic): ");
            truck.setTransmissionType(transmission);
            
            // Add to vehicles list
            vehicles.add(truck);
            System.out.println("\n✅ Truck added successfully!\n");
            
        } catch (IllegalArgumentException e) {
            System.out.println("\n❌ Error: " + e.getMessage() + "\n");
        } catch (InputMismatchException e) {
            System.out.println("\n❌ Error: Invalid input format. Please enter valid values.\n");
            scanner.nextLine(); // Clear the invalid input
        }
    }
    
    /**
     * Displays all vehicles currently in the system
     */
    private static void displayAllVehicles() {
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles in the system.\n");
            return;
        }
        
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║        VEHICLE FLEET INFORMATION           ║");
        System.out.println("╚════════════════════════════════════════════╝\n");
        
        for (int i = 0; i < vehicles.size(); i++) {
            System.out.println("Vehicle #" + (i + 1) + ":");
            System.out.println(vehicles.get(i).toString());
            System.out.println(); // Blank line for readability
        }
        
        System.out.println("─────────────────────────────────────────────");
        System.out.println("Total vehicles in fleet: " + vehicles.size() + "\n");
    }
    
    /**
     * Gets a string input from the user
     * Validates that input is not empty
     * 
     * @param prompt - The prompt message to display
     * @return String - The user input (trimmed)
     */
    private static String getUserInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            
            if (!input.isEmpty()) {
                return input;
            } else {
                System.out.println("❌ Input cannot be empty. Please try again.");
            }
        }
    }
    
    /**
     * Gets an integer input from the user
     * Validates that input is a valid integer
     * 
     * @param prompt - The prompt message to display
     * @return int - The user input as an integer
     */
    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int input = Integer.parseInt(scanner.nextLine().trim());
                return input;
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input. Please enter a valid integer.");
            }
        }
    }
    
    /**
     * Gets a double input from the user
     * Validates that input is a valid double
     * 
     * @param prompt - The prompt message to display
     * @return double - The user input as a double
     */
    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double input = Double.parseDouble(scanner.nextLine().trim());
                return input;
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input. Please enter a valid number.");
            }
        }
    }
}

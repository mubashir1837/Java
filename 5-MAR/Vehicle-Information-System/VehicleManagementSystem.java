import java.util.Scanner;
import classes.Car;
import classes.Motorcycle;
import classes.Truck;

public class VehicleManagementSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean continueProgram = true;

        while (continueProgram) {
            System.out.println("\nSelect Vehicle Type to Add:");
            System.out.println("1. Car");
            System.out.println("2. Motorcycle");
            System.out.println("3. Truck");
            System.out.println("4. Exit");
            System.out.print("Enter your choice (1-4): ");

            try {
                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1:
                        Car car = new Car();
                        System.out.print("Enter Make: ");
                        car.setMake(sc.nextLine());
                        System.out.print("Enter Model: ");
                        car.setModel(sc.nextLine());
                        System.out.print("Enter Year: ");
                        car.setYear(Integer.parseInt(sc.nextLine()));
                        System.out.print("Enter Number of Doors: ");
                        car.setNumberOfDoors(Integer.parseInt(sc.nextLine()));
                        System.out.print("Enter Fuel Type (Petrol/Diesel/Electric): ");
                        car.setFuelType(sc.nextLine());
                        car.displayCarDetails();
                        break;

                    case 2:
                        Motorcycle mc = new Motorcycle();
                        System.out.print("Enter Make: ");
                        mc.setMake(sc.nextLine());
                        System.out.print("Enter Model: ");
                        mc.setModel(sc.nextLine());
                        System.out.print("Enter Year: ");
                        mc.setYear(Integer.parseInt(sc.nextLine()));
                        System.out.print("Enter Number of Wheels: ");
                        mc.setNumberOfWheels(Integer.parseInt(sc.nextLine()));
                        System.out.print("Enter Motorcycle Type (Sport/Cruiser/Off-road): ");
                        mc.setMotorcycleType(sc.nextLine());
                        mc.displayMotorcycleDetails();
                        break;

                    case 3:
                        Truck truck = new Truck();
                        System.out.print("Enter Make: ");
                        truck.setMake(sc.nextLine());
                        System.out.print("Enter Model: ");
                        truck.setModel(sc.nextLine());
                        System.out.print("Enter Year: ");
                        truck.setYear(Integer.parseInt(sc.nextLine()));
                        System.out.print("Enter Cargo Capacity (in tons): ");
                        truck.setCargoCapacity(Double.parseDouble(sc.nextLine()));
                        System.out.print("Enter Transmission Type (Manual/Automatic): ");
                        truck.setTransmissionType(sc.nextLine());
                        truck.displayTruckDetails();
                        break;

                    case 4:
                        continueProgram = false;
                        System.out.println("Exiting Vehicle Management System.");
                        break;

                    default:
                        System.out.println("Invalid choice! Please select 1-4.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter numeric values where required.");
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }

        sc.close();
    }
}



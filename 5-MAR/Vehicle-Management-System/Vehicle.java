/**
 * Vehicle Interface
 * 
 * This interface defines the contract for all vehicle types in the Car Rental Agency system.
 * It specifies the basic methods that every vehicle must implement to ensure consistency
 * across different vehicle types (Car, Motorcycle, Truck).
 * 
 * Methods:
 * - getMake(): Returns the manufacturer of the vehicle
 * - getModel(): Returns the model name of the vehicle
 * - getYear(): Returns the year of manufacture of the vehicle
 * 
 * @author Car Rental Agency System
 * @version 1.0
 */
public interface Vehicle {
    
    /**
     * Retrieves the make (manufacturer) of the vehicle.
     * 
     * @return String - The manufacturer of the vehicle (e.g., "Toyota", "Honda")
     */
    String getMake();
    
    /**
     * Retrieves the model name of the vehicle.
     * 
     * @return String - The model name of the vehicle (e.g., "Camry", "Civic")
     */
    String getModel();
    
    /**
     * Retrieves the year of manufacture of the vehicle.
     * 
     * @return int - The year when the vehicle was manufactured
     */
    int getYear();
}

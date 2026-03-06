/**
 * CarVehicle Interface
 * 
 * This interface extends the Vehicle interface and defines additional methods
 * specific to car vehicles. It provides a contract for managing car-specific
 * attributes such as the number of doors and fuel type.
 * 
 * Methods:
 * - setNumberOfDoors(int): Sets the number of doors on the car
 * - getNumberOfDoors(): Retrieves the number of doors on the car
 * - setFuelType(String): Sets the fuel type of the car
 * - getFuelType(): Retrieves the fuel type of the car
 * 
 * Supported Fuel Types: "petrol", "diesel", "electric"
 * 
 * @author Car Rental Agency System
 * @version 1.0
 */
public interface CarVehicle {
    
    /**
     * Sets the number of doors on the car.
     * Valid values are typically 2, 4, or more doors.
     * 
     * @param numberOfDoors - The number of doors on the car
     * @throws IllegalArgumentException if numberOfDoors is less than 2
     */
    void setNumberOfDoors(int numberOfDoors);
    
    /**
     * Retrieves the number of doors on the car.
     * 
     * @return int - The number of doors on the car
     */
    int getNumberOfDoors();
    
    /**
     * Sets the fuel type of the car.
     * Valid fuel types: "petrol", "diesel", "electric"
     * 
     * @param fuelType - The type of fuel the car uses
     * @throws IllegalArgumentException if fuelType is not valid
     */
    void setFuelType(String fuelType);
    
    /**
     * Retrieves the fuel type of the car.
     * 
     * @return String - The fuel type of the car
     */
    String getFuelType();
}

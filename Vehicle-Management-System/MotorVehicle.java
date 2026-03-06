/**
 * MotorVehicle Interface
 * 
 * This interface extends the Vehicle interface and defines additional methods
 * specific to motorcycle vehicles. It provides a contract for managing
 * motorcycle-specific attributes such as the number of wheels and motorcycle type.
 * 
 * Methods:
 * - setNumberOfWheels(int): Sets the number of wheels on the motorcycle
 * - getNumberOfWheels(): Retrieves the number of wheels on the motorcycle
 * - setMotorcycleType(String): Sets the type of motorcycle
 * - getMotorcycleType(): Retrieves the type of motorcycle
 * 
 * Supported Motorcycle Types: "sport", "cruiser", "off-road"
 * 
 * @author Car Rental Agency System
 * @version 1.0
 */
public interface MotorVehicle {
    
    /**
     * Sets the number of wheels on the motorcycle.
     * Most motorcycles have 2 wheels, but some trikes have 3 wheels.
     * 
     * @param numberOfWheels - The number of wheels on the motorcycle
     * @throws IllegalArgumentException if numberOfWheels is less than 2 or greater than 3
     */
    void setNumberOfWheels(int numberOfWheels);
    
    /**
     * Retrieves the number of wheels on the motorcycle.
     * 
     * @return int - The number of wheels on the motorcycle
     */
    int getNumberOfWheels();
    
    /**
     * Sets the type of motorcycle.
     * Valid types: "sport", "cruiser", "off-road"
     * 
     * @param motorcycleType - The type of motorcycle
     * @throws IllegalArgumentException if motorcycleType is not valid
     */
    void setMotorcycleType(String motorcycleType);
    
    /**
     * Retrieves the type of motorcycle.
     * 
     * @return String - The type of motorcycle
     */
    String getMotorcycleType();
}

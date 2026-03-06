/**
 * TruckVehicle Interface
 * 
 * This interface extends the Vehicle interface and defines additional methods
 * specific to truck vehicles. It provides a contract for managing truck-specific
 * attributes such as cargo capacity and transmission type.
 * 
 * Methods:
 * - setCargoCapacity(double): Sets the cargo capacity of the truck
 * - getCargoCapacity(): Retrieves the cargo capacity of the truck
 * - setTransmissionType(String): Sets the transmission type of the truck
 * - getTransmissionType(): Retrieves the transmission type of the truck
 * 
 * Supported Transmission Types: "manual", "automatic"
 * 
 * @author Car Rental Agency System
 * @version 1.0
 */
public interface TruckVehicle {
    
    /**
     * Sets the cargo capacity of the truck in tons.
     * Cargo capacity should be a positive number representing tons.
     * 
     * @param cargoCapacity - The maximum cargo capacity in tons
     * @throws IllegalArgumentException if cargoCapacity is less than or equal to 0
     */
    void setCargoCapacity(double cargoCapacity);
    
    /**
     * Retrieves the cargo capacity of the truck.
     * 
     * @return double - The cargo capacity in tons
     */
    double getCargoCapacity();
    
    /**
     * Sets the transmission type of the truck.
     * Valid transmission types: "manual", "automatic"
     * 
     * @param transmissionType - The type of transmission
     * @throws IllegalArgumentException if transmissionType is not valid
     */
    void setTransmissionType(String transmissionType);
    
    /**
     * Retrieves the transmission type of the truck.
     * 
     * @return String - The transmission type of the truck
     */
    String getTransmissionType();
}

/**
 * Truck Class
 * 
 * This class implements both the Vehicle and TruckVehicle interfaces,
 * representing a truck vehicle in the Car Rental Agency system.
 * 
 * It encapsulates truck-specific attributes including make, model, year,
 * cargo capacity, and transmission type. The class includes comprehensive
 * error handling and validation for all attributes.
 * 
 * @author Car Rental Agency System
 * @version 1.0
 */
public class Truck implements Vehicle, TruckVehicle {
    
    // Basic vehicle attributes
    private String make;
    private String model;
    private int year;
    
    // Truck-specific attributes
    private double cargoCapacity;
    private String transmissionType;
    
    // Valid transmission types
    private static final String[] VALID_TRANSMISSION_TYPES = {"manual", "automatic"};
    
    /**
     * Constructor for the Truck class.
     * Initializes a truck with basic vehicle information.
     * 
     * @param make - The manufacturer of the truck
     * @param model - The model name of the truck
     * @param year - The year of manufacture
     * @throws IllegalArgumentException if make or model is empty, or year is invalid
     */
    public Truck(String make, String model, int year) {
        if (make == null || make.trim().isEmpty()) {
            throw new IllegalArgumentException("Make cannot be empty");
        }
        if (model == null || model.trim().isEmpty()) {
            throw new IllegalArgumentException("Model cannot be empty");
        }
        if (year < 1896 || year > java.time.Year.now().getValue()) {
            throw new IllegalArgumentException("Year must be between 1896 and " + java.time.Year.now().getValue());
        }
        
        this.make = make.trim();
        this.model = model.trim();
        this.year = year;
    }
    
    /**
     * Retrieves the make of the truck.
     * 
     * @return String - The manufacturer of the truck
     */
    @Override
    public String getMake() {
        return this.make;
    }
    
    /**
     * Retrieves the model of the truck.
     * 
     * @return String - The model name of the truck
     */
    @Override
    public String getModel() {
        return this.model;
    }
    
    /**
     * Retrieves the year of manufacture of the truck.
     * 
     * @return int - The year of manufacture
     */
    @Override
    public int getYear() {
        return this.year;
    }
    
    /**
     * Sets the cargo capacity of the truck in tons.
     * Validates that cargo capacity is a positive number.
     * 
     * @param cargoCapacity - The cargo capacity in tons
     * @throws IllegalArgumentException if cargoCapacity is less than or equal to 0
     */
    @Override
    public void setCargoCapacity(double cargoCapacity) {
        if (cargoCapacity <= 0) {
            throw new IllegalArgumentException("Cargo capacity must be greater than 0 tons");
        }
        this.cargoCapacity = cargoCapacity;
    }
    
    /**
     * Retrieves the cargo capacity of the truck.
     * 
     * @return double - The cargo capacity in tons
     */
    @Override
    public double getCargoCapacity() {
        return this.cargoCapacity;
    }
    
    /**
     * Sets the transmission type of the truck.
     * Valid transmission types: "manual", "automatic"
     * 
     * @param transmissionType - The transmission type
     * @throws IllegalArgumentException if transmissionType is not valid
     */
    @Override
    public void setTransmissionType(String transmissionType) {
        if (transmissionType == null || transmissionType.trim().isEmpty()) {
            throw new IllegalArgumentException("Transmission type cannot be empty");
        }
        
        String normalizedType = transmissionType.trim().toLowerCase();
        boolean isValid = false;
        
        for (String validType : VALID_TRANSMISSION_TYPES) {
            if (validType.equals(normalizedType)) {
                isValid = true;
                break;
            }
        }
        
        if (!isValid) {
            throw new IllegalArgumentException("Invalid transmission type. Valid types are: manual, automatic");
        }
        
        this.transmissionType = normalizedType;
    }
    
    /**
     * Retrieves the transmission type of the truck.
     * 
     * @return String - The transmission type
     */
    @Override
    public String getTransmissionType() {
        return this.transmissionType;
    }
    
    /**
     * Provides a string representation of the truck.
     * 
     * @return String - Formatted truck information
     */
    @Override
    public String toString() {
        return String.format(
            "TRUCK DETAILS:\n" +
            "  Make: %s\n" +
            "  Model: %s\n" +
            "  Year: %d\n" +
            "  Cargo Capacity: %.2f tons\n" +
            "  Transmission Type: %s",
            this.make, this.model, this.year, this.cargoCapacity, this.transmissionType
        );
    }
}

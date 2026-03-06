/**
 * Motorcycle Class
 * 
 * This class implements both the Vehicle and MotorVehicle interfaces,
 * representing a motorcycle vehicle in the Car Rental Agency system.
 * 
 * It encapsulates motorcycle-specific attributes including make, model, year,
 * number of wheels, and motorcycle type. The class includes comprehensive
 * error handling and validation for all attributes.
 * 
 * @author Car Rental Agency System
 * @version 1.0
 */
public class Motorcycle implements Vehicle, MotorVehicle {
    
    // Basic vehicle attributes
    private String make;
    private String model;
    private int year;
    
    // Motorcycle-specific attributes
    private int numberOfWheels;
    private String motorcycleType;
    
    // Valid motorcycle types
    private static final String[] VALID_MOTORCYCLE_TYPES = {"sport", "cruiser", "off-road"};
    
    /**
     * Constructor for the Motorcycle class.
     * Initializes a motorcycle with basic vehicle information.
     * 
     * @param make - The manufacturer of the motorcycle
     * @param model - The model name of the motorcycle
     * @param year - The year of manufacture
     * @throws IllegalArgumentException if make or model is empty, or year is invalid
     */
    public Motorcycle(String make, String model, int year) {
        if (make == null || make.trim().isEmpty()) {
            throw new IllegalArgumentException("Make cannot be empty");
        }
        if (model == null || model.trim().isEmpty()) {
            throw new IllegalArgumentException("Model cannot be empty");
        }
        if (year < 1885 || year > java.time.Year.now().getValue()) {
            throw new IllegalArgumentException("Year must be between 1885 and " + java.time.Year.now().getValue());
        }
        
        this.make = make.trim();
        this.model = model.trim();
        this.year = year;
    }
    
    /**
     * Retrieves the make of the motorcycle.
     * 
     * @return String - The manufacturer of the motorcycle
     */
    @Override
    public String getMake() {
        return this.make;
    }
    
    /**
     * Retrieves the model of the motorcycle.
     * 
     * @return String - The model name of the motorcycle
     */
    @Override
    public String getModel() {
        return this.model;
    }
    
    /**
     * Retrieves the year of manufacture of the motorcycle.
     * 
     * @return int - The year of manufacture
     */
    @Override
    public int getYear() {
        return this.year;
    }
    
    /**
     * Sets the number of wheels on the motorcycle.
     * Most motorcycles have 2 wheels, but some trikes have 3 wheels.
     * 
     * @param numberOfWheels - The number of wheels
     * @throws IllegalArgumentException if numberOfWheels is not 2 or 3
     */
    @Override
    public void setNumberOfWheels(int numberOfWheels) {
        if (numberOfWheels < 2 || numberOfWheels > 3) {
            throw new IllegalArgumentException("Number of wheels must be 2 or 3");
        }
        this.numberOfWheels = numberOfWheels;
    }
    
    /**
     * Retrieves the number of wheels on the motorcycle.
     * 
     * @return int - The number of wheels
     */
    @Override
    public int getNumberOfWheels() {
        return this.numberOfWheels;
    }
    
    /**
     * Sets the type of motorcycle.
     * Valid motorcycle types: "sport", "cruiser", "off-road"
     * 
     * @param motorcycleType - The type of motorcycle
     * @throws IllegalArgumentException if motorcycleType is not valid
     */
    @Override
    public void setMotorcycleType(String motorcycleType) {
        if (motorcycleType == null || motorcycleType.trim().isEmpty()) {
            throw new IllegalArgumentException("Motorcycle type cannot be empty");
        }
        
        String normalizedType = motorcycleType.trim().toLowerCase();
        boolean isValid = false;
        
        for (String validType : VALID_MOTORCYCLE_TYPES) {
            if (validType.equals(normalizedType)) {
                isValid = true;
                break;
            }
        }
        
        if (!isValid) {
            throw new IllegalArgumentException("Invalid motorcycle type. Valid types are: sport, cruiser, off-road");
        }
        
        this.motorcycleType = normalizedType;
    }
    
    /**
     * Retrieves the type of motorcycle.
     * 
     * @return String - The type of motorcycle
     */
    @Override
    public String getMotorcycleType() {
        return this.motorcycleType;
    }
    
    /**
     * Provides a string representation of the motorcycle.
     * 
     * @return String - Formatted motorcycle information
     */
    @Override
    public String toString() {
        return String.format(
            "MOTORCYCLE DETAILS:\n" +
            "  Make: %s\n" +
            "  Model: %s\n" +
            "  Year: %d\n" +
            "  Number of Wheels: %d\n" +
            "  Motorcycle Type: %s",
            this.make, this.model, this.year, this.numberOfWheels, this.motorcycleType
        );
    }
}

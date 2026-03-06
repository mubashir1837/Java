/**
 * Car Class
 * 
 * This class implements both the Vehicle and CarVehicle interfaces,
 * representing a car vehicle in the Car Rental Agency system.
 * 
 * It encapsulates car-specific attributes including make, model, year,
 * number of doors, and fuel type. The class includes comprehensive
 * error handling and validation for all attributes.
 * 
 * @author Car Rental Agency System
 * @version 1.0
 */
public class Car implements Vehicle, CarVehicle {
    
    // Basic vehicle attributes
    private String make;
    private String model;
    private int year;
    
    // Car-specific attributes
    private int numberOfDoors;
    private String fuelType;
    
    // Valid fuel types
    private static final String[] VALID_FUEL_TYPES = {"petrol", "diesel", "electric"};
    
    /**
     * Constructor for the Car class.
     * Initializes a car with basic vehicle information.
     * 
     * @param make - The manufacturer of the car
     * @param model - The model name of the car
     * @param year - The year of manufacture
     * @throws IllegalArgumentException if make or model is empty, or year is invalid
     */
    public Car(String make, String model, int year) {
        if (make == null || make.trim().isEmpty()) {
            throw new IllegalArgumentException("Make cannot be empty");
        }
        if (model == null || model.trim().isEmpty()) {
            throw new IllegalArgumentException("Model cannot be empty");
        }
        if (year < 1886 || year > java.time.Year.now().getValue()) {
            throw new IllegalArgumentException("Year must be between 1886 and " + java.time.Year.now().getValue());
        }
        
        this.make = make.trim();
        this.model = model.trim();
        this.year = year;
    }
    
    /**
     * Retrieves the make of the car.
     * 
     * @return String - The manufacturer of the car
     */
    @Override
    public String getMake() {
        return this.make;
    }
    
    /**
     * Retrieves the model of the car.
     * 
     * @return String - The model name of the car
     */
    @Override
    public String getModel() {
        return this.model;
    }
    
    /**
     * Retrieves the year of manufacture of the car.
     * 
     * @return int - The year of manufacture
     */
    @Override
    public int getYear() {
        return this.year;
    }
    
    /**
     * Sets the number of doors on the car.
     * Validates that the number of doors is at least 2.
     * 
     * @param numberOfDoors - The number of doors
     * @throws IllegalArgumentException if numberOfDoors is less than 2
     */
    @Override
    public void setNumberOfDoors(int numberOfDoors) {
        if (numberOfDoors < 2) {
            throw new IllegalArgumentException("Number of doors must be at least 2");
        }
        this.numberOfDoors = numberOfDoors;
    }
    
    /**
     * Retrieves the number of doors on the car.
     * 
     * @return int - The number of doors
     */
    @Override
    public int getNumberOfDoors() {
        return this.numberOfDoors;
    }
    
    /**
     * Sets the fuel type of the car.
     * Valid fuel types: "petrol", "diesel", "electric"
     * 
     * @param fuelType - The fuel type
     * @throws IllegalArgumentException if fuelType is not valid
     */
    @Override
    public void setFuelType(String fuelType) {
        if (fuelType == null || fuelType.trim().isEmpty()) {
            throw new IllegalArgumentException("Fuel type cannot be empty");
        }
        
        String normalizedFuelType = fuelType.trim().toLowerCase();
        boolean isValid = false;
        
        for (String validType : VALID_FUEL_TYPES) {
            if (validType.equals(normalizedFuelType)) {
                isValid = true;
                break;
            }
        }
        
        if (!isValid) {
            throw new IllegalArgumentException("Invalid fuel type. Valid types are: petrol, diesel, electric");
        }
        
        this.fuelType = normalizedFuelType;
    }
    
    /**
     * Retrieves the fuel type of the car.
     * 
     * @return String - The fuel type
     */
    @Override
    public String getFuelType() {
        return this.fuelType;
    }
    
    /**
     * Provides a string representation of the car.
     * 
     * @return String - Formatted car information
     */
    @Override
    public String toString() {
        return String.format(
            "CAR DETAILS:\n" +
            "  Make: %s\n" +
            "  Model: %s\n" +
            "  Year: %d\n" +
            "  Number of Doors: %d\n" +
            "  Fuel Type: %s",
            this.make, this.model, this.year, this.numberOfDoors, this.fuelType
        );
    }
}

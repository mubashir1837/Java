package classes;

import interfaces.Vehicle;
import interfaces.MotorVehicle;

public class Motorcycle implements Vehicle, MotorVehicle {
    private String make, model, motorcycleType;
    private int year, numberOfWheels;

    public String getMake() { return make; }
    public String getModel() { return model; }
    public int getYear() { return year; }
    public void setMake(String make) { this.make = make; }
    public void setModel(String model) { this.model = model; }
    public void setYear(int year) { this.year = year; }

    public void setNumberOfWheels(int wheels) { this.numberOfWheels = wheels; }
    public int getNumberOfWheels() { return numberOfWheels; }
    public void setMotorcycleType(String type) { this.motorcycleType = type; }
    public String getMotorcycleType() { return motorcycleType; }

    public void displayMotorcycleDetails() {
        System.out.println("\n--- Motorcycle Details ---");
        System.out.println("Make: " + make);
        System.out.println("Model: " + model);
        System.out.println("Year: " + year);
        System.out.println("Number of Wheels: " + numberOfWheels);
        System.out.println("Motorcycle Type: " + motorcycleType);
    }
}


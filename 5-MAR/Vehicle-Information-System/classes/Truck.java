package classes;

import interfaces.Vehicle;
import interfaces.TruckVehicle;

public class Truck implements Vehicle, TruckVehicle {
    private String make, model, transmissionType;
    private int year;
    private double cargoCapacity;

    public String getMake() { return make; }
    public String getModel() { return model; }
    public int getYear() { return year; }
    public void setMake(String make) { this.make = make; }
    public void setModel(String model) { this.model = model; }
    public void setYear(int year) { this.year = year; }

    public void setCargoCapacity(double capacity) { this.cargoCapacity = capacity; }
    public double getCargoCapacity() { return cargoCapacity; }
    public void setTransmissionType(String type) { this.transmissionType = type; }
    public String getTransmissionType() { return transmissionType; }

    public void displayTruckDetails() {
        System.out.println("\n--- Truck Details ---");
        System.out.println("Make: " + make);
        System.out.println("Model: " + model);
        System.out.println("Year: " + year);
        System.out.println("Cargo Capacity (tons): " + cargoCapacity);
        System.out.println("Transmission Type: " + transmissionType);
    }
}


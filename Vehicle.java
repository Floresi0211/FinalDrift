import java.util.UUID;

class Vehicle {
    private String id;
    private String make;
    private String model;
    private int year;
    private String licensePlate;
    private int capacity;
    private double baseFeePerDay;
    private double ratePerMile;
    private double mileage;
    private VehicleStatus status = VehicleStatus.AVAILABLE;
    
    public enum VehicleStatus {
        AVAILABLE, RENTED, MAINTENANCE, OUT_OF_SERVICE
    }
    
    public Vehicle(String make, String model, int year, String licensePlate, 
                  int capacity, double baseFeePerDay, double ratePerMile) {
        this.id = UUID.randomUUID().toString();
        this.make = make;
        this.model = model;
        this.year = year;
        this.licensePlate = licensePlate;
        this.capacity = capacity;
        this.baseFeePerDay = baseFeePerDay;
        this.ratePerMile = ratePerMile;
    }
    
    public Vehicle(String model, String licensePlate, double baseFee, int capacity) {
        this("Generic", model, 2025, licensePlate, capacity, baseFee, 0.2);
    }
    
    public double calculateRental(int days, int miles, int age, boolean insurance, boolean isVeteran) {
        double baseRate = baseFeePerDay * days;
        double mileageRate = miles * ratePerMile;
        return baseRate + mileageRate;
    }
    
    // Getters and setters
    public String getId() { return id; }
    public String getMake() { return make; }
    public void setMake(String make) { this.make = make; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
    public String getLicensePlate() { return licensePlate; }
    public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }
    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public double getBaseFeePerDay() { return baseFeePerDay; }
    public void setBaseFeePerDay(double baseFeePerDay) { this.baseFeePerDay = baseFeePerDay; }
    public double getRatePerMile() { return ratePerMile; }
    public void setRatePerMile(double ratePerMile) { this.ratePerMile = ratePerMile; }
    public double getMileage() { return mileage; }
    public void setMileage(double mileage) { this.mileage = mileage; }
    public VehicleStatus getStatus() { return status; }
    public void setStatus(VehicleStatus status) { this.status = status; }
    
    @Override
    public String toString() {
        return year + " " + make + " " + model + " (License: " + licensePlate + ")";
    }
}
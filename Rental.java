import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class Rental {
    private String id;
    private Customer customer;
    private Vehicle vehicle;
    private LocalDateTime pickupTime;
    private LocalDateTime dropoffTime;
    private LocalDateTime actualDropoffTime;
    private String pickupLocation;
    private String dropoffLocation;
    private double initialMileage;
    private double finalMileage;
    private RentalStatus status;
    private double insuranceFee;
    
    public enum RentalStatus {
        RESERVED,
        ACTIVE,
        COMPLETED,
        CANCELLED
    }
    
    public Rental(Customer customer, Vehicle vehicle, 
                 LocalDateTime pickupTime, LocalDateTime dropoffTime,
                 String pickupLocation, String dropoffLocation,
                 double initialMileage, double insuranceFee) {
        this.id = UUID.randomUUID().toString();
        this.customer = customer;
        this.vehicle = vehicle;
        this.pickupTime = pickupTime;
        this.dropoffTime = dropoffTime;
        this.pickupLocation = pickupLocation;
        this.dropoffLocation = dropoffLocation;
        this.initialMileage = initialMileage;
        this.status = RentalStatus.RESERVED;
        this.insuranceFee = insuranceFee;
    }
    
    // Getters and setters
    public String getId() { return id; }
    
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    
    public Vehicle getVehicle() { return vehicle; }
    public void setVehicle(Vehicle vehicle) { this.vehicle = vehicle; }
    
    public LocalDateTime getPickupTime() { return pickupTime; }
    public void setPickupTime(LocalDateTime pickupTime) { this.pickupTime = pickupTime; }
    
    public LocalDateTime getDropoffTime() { return dropoffTime; }
    public void setDropoffTime(LocalDateTime dropoffTime) { this.dropoffTime = dropoffTime; }
    
    public LocalDateTime getActualDropoffTime() { return actualDropoffTime; }
    public void setActualDropoffTime(LocalDateTime actualDropoffTime) { this.actualDropoffTime = actualDropoffTime; }
    
    public String getPickupLocation() { return pickupLocation; }
    public void setPickupLocation(String pickupLocation) { this.pickupLocation = pickupLocation; }
    
    public String getDropoffLocation() { return dropoffLocation; }
    public void setDropoffLocation(String dropoffLocation) { this.dropoffLocation = dropoffLocation; }
    
    public double getInitialMileage() { return initialMileage; }
    public void setInitialMileage(double initialMileage) { this.initialMileage = initialMileage; }
    
    public double getFinalMileage() { return finalMileage; }
    public void setFinalMileage(double finalMileage) { this.finalMileage = finalMileage; }
    
    public RentalStatus getStatus() { return status; }
    public void setStatus(RentalStatus status) { this.status = status; }
    
    public double getInsuranceFee() { return insuranceFee; }
    public void setInsuranceFee(double insuranceFee) { this.insuranceFee = insuranceFee; }
    
    // Helper methods
    public long getRentalDurationInDays() {
        return ChronoUnit.DAYS.between(pickupTime, dropoffTime);
    }
    
    public double getMilesDriven() {
        return finalMileage - initialMileage;
    }
    
    public boolean isLate() {
        if (actualDropoffTime == null) {
            return false;
        }
        return actualDropoffTime.isAfter(dropoffTime);
    }
    
    public long getLateHours() {
        if (!isLate()) {
            return 0;
        }
        return ChronoUnit.HOURS.between(dropoffTime, actualDropoffTime);
    }
    
    @Override
    public String toString() {
        return "Rental #" + id + ": " + customer.getName() + " - " + vehicle.getMake() + " " + 
                vehicle.getModel() + " (" + pickupTime + " to " + dropoffTime + ")";
    }
}
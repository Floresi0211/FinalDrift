class SUV extends Vehicle {
    private boolean isOffRoadCapable;
    
    public SUV(String make, String model, int year, String licensePlate, int capacity, 
              double baseFeePerDay, double ratePerMile, boolean isOffRoadCapable) {
        super(make, model, year, licensePlate, capacity, baseFeePerDay, ratePerMile);
        this.isOffRoadCapable = isOffRoadCapable;
    }
    
    public SUV(String model, String licensePlate, double baseFee, int capacity) {
        super(model, licensePlate, baseFee, capacity);
        this.isOffRoadCapable = false;
    }
    
    @Override
    public double calculateRental(int days, int miles, int age, boolean insurance, boolean isVeteran) {
        double rate = getBaseFeePerDay() * days + miles * 0.5;  // Calculate base rate
        if (insurance) rate += 25;                   // Add insurance fee if selected
        if (age <= 25) rate += 35;                   // Add young driver fee
        if (isVeteran) rate -= 15;                   // Apply veteran discount
        return rate + rate * 0.09;                   // Add tax and return total
    }
    
    public boolean isOffRoadCapable() { return isOffRoadCapable; }
    public void setOffRoadCapable(boolean offRoadCapable) { isOffRoadCapable = offRoadCapable; }
}

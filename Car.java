class Car extends Vehicle {
    private boolean isFunCar;
    private String funCarTheme;
    
    public Car(String make, String model, int year, String licensePlate, int capacity, 
              double baseFeePerDay, double ratePerMile, boolean isFunCar, String funCarTheme) {
        super(make, model, year, licensePlate, capacity, baseFeePerDay, ratePerMile);
        this.isFunCar = isFunCar;
        this.funCarTheme = funCarTheme;
    }
    
    public Car(String model, String licensePlate, double baseFee, int capacity) {
        super(model, licensePlate, baseFee, capacity);
        this.isFunCar = false;
    }
    
    @Override
    public double calculateRental(int days, int miles, int age, boolean insurance, boolean isVeteran) {
        double rate = getBaseFeePerDay() * days + miles * 0.4;  // Calculate base rate
        if (insurance) rate += 20;                   // Add insurance fee if selected
        if (age <= 25) rate += 30;                   // Add young driver fee
        if (isVeteran) rate -= 10;                   // Apply veteran discount
        return rate + rate * 0.08;                   // Add tax and return total
    }
    
    public boolean isFunCar() { return isFunCar; }
    public void setFunCar(boolean funCar) { isFunCar = funCar; }
    public String getFunCarTheme() { return funCarTheme; }
    public void setFunCarTheme(String funCarTheme) { this.funCarTheme = funCarTheme; }
}
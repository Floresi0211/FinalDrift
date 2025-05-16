import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    public static void main(String[] args) {
        List<Vehicle> vehicles = new ArrayList<>();
        
        Car sedan = new Car("Toyota", "Camry", 2023, "CAR001", 5, 45.0, 0.20, false, null);
        Car luxury = new Car("Mercedes", "S-Class", 2024, "LUX789", 5, 95.0, 0.35, false, null);
        Car funCar = new Car("Volkswagen", "Beetle", 2022, "FUN456", 4, 55.0, 0.25, true, "Flower Power");
        
        SUV compact = new SUV("Honda", "CR-V", 2023, "SUV111", 5, 65.0, 0.30, false);
        SUV offroad = new SUV("Jeep", "Wrangler", 2024, "OFF222", 4, 85.0, 0.40, true);
        
        vehicles.add(sedan);
        vehicles.add(luxury);
        vehicles.add(funCar);
        vehicles.add(compact);
        vehicles.add(offroad);
        
        System.out.println("\nAvailable Vehicles");
        for (int i = 0; i < vehicles.size(); i++) {
            System.out.println((i + 1) + ". " + vehicles.get(i));
        }
        
        System.out.println("\nCustomer Registration");
        List<Customer> customers = new ArrayList<>();
        
        System.out.print("Create a new customer? (yes/no): ");
        String createCustomer = scanner.nextLine().trim().toLowerCase();
        
        while (createCustomer.equals("yes") || createCustomer.equals("y")) {
            Customer newCustomer = createCustomerFromInput();
            customers.add(newCustomer);
            
            System.out.println("\nCustomer created successfully!");
            System.out.println(newCustomer + " (Age: " + newCustomer.getAge() + 
                              ", License valid: " + newCustomer.isLicenseValid() + ")");
            
            System.out.print("\nWould you like to create another customer? (yes/no): ");
            createCustomer = scanner.nextLine().trim().toLowerCase();
        }
        
        if (customers.isEmpty()) {
            Customer john = new Customer(
                "John Doe", 
                "DL12345678", 
                LocalDate.of(2028, 5, 15), 
                LocalDate.of(1985, 3, 22), 
                "john@example.com",
                "555-123-4567",
                false,
                false
            );
            Customer alice = new Customer(
                "Alicia Martinez", 
                "DL87654321", 
                LocalDate.of(2026, 8, 10), 
                LocalDate.of(1955, 7, 8), 
                "alicia@example.com",
                "555-765-4321",
                true,
                true
            );
            customers.add(john);
            customers.add(alice);
        }
        
        System.out.println("\nRegistered Customers");
        for (int i = 0; i < customers.size(); i++) {
            Customer customer = customers.get(i);
            System.out.println((i + 1) + ". " + customer + " (Age: " + 
                              customer.getAge() + ", License valid: " + 
                              customer.isLicenseValid() + ")");
        }
        
        System.out.println("\n~~~ Creating Rental ~~~");
        
        System.out.println("\nSelect a customer by entering their number:");
        int customerIndex = getIntInput(1, customers.size()) - 1;
        Customer selectedCustomer = customers.get(customerIndex);
        
        System.out.println("\nSelect a vehicle by entering its number:");
        int vehicleIndex = getIntInput(1, vehicles.size()) - 1;
        Vehicle selectedVehicle = vehicles.get(vehicleIndex);
        
        System.out.println("\nEnter pickup location:");
        String pickupLocation = scanner.nextLine();
        
        System.out.println("Enter dropoff location:");
        String dropoffLocation = scanner.nextLine();
        
        System.out.println("Enter rental duration in days:");
        int rentalDays = getIntInput(1, 30);
        
        System.out.println("Enter initial mileage:");
        double initialMileage = getDoubleInput(0, 999999);
        
        System.out.println("Would you like to add insurance? (yes/no):");
        boolean wantsInsurance = scanner.nextLine().trim().toLowerCase().startsWith("y");
        double insuranceFee = wantsInsurance ? 20.0 : 0.0;
        
        LocalDateTime pickupTime = LocalDateTime.now();
        LocalDateTime dropoffTime = pickupTime.plusDays(rentalDays);
        
        Rental rental = new Rental(
            selectedCustomer,
            selectedVehicle,
            pickupTime,
            dropoffTime,
            pickupLocation,
            dropoffLocation,
            initialMileage,
            insuranceFee
        );
        
        System.out.println("\nRental created successfully!");
        System.out.println("Created rental: " + rental);
        
        System.out.println("\nProcessing Rental");
        
        selectedVehicle.setStatus(Vehicle.VehicleStatus.RENTED);
        System.out.println("Vehicle status updated: " + selectedVehicle);
        
        rental.setStatus(Rental.RentalStatus.ACTIVE);
        System.out.println("Rental status updated: " + rental.getStatus());
        
        System.out.println("\nSimulating Vehicle Return");
    
        System.out.println("Was the vehicle returned late? (yes/no):");
        boolean isLate = scanner.nextLine().trim().toLowerCase().startsWith("y");
        
        LocalDateTime actualDropoffTime = dropoffTime;
        int lateHours = 0;
        
        if (isLate) {
            System.out.println("Enter how many hours late:");
            lateHours = getIntInput(1, 72);
            actualDropoffTime = dropoffTime.plusHours(lateHours);
        }
        
        System.out.println("Enter final mileage:");
        double finalMileage = getDoubleInput(initialMileage, 999999);
        
        rental.setActualDropoffTime(actualDropoffTime);
        rental.setFinalMileage(finalMileage);
        rental.setStatus(Rental.RentalStatus.COMPLETED);
        selectedVehicle.setStatus(Vehicle.VehicleStatus.AVAILABLE);
        
        // Calculated rental cost
        int customerAge = selectedCustomer.getAge();
        boolean isVeteran = selectedCustomer.isVeteran();
        int milesDriven = (int)(finalMileage - initialMileage);
        
        double rentalCost = 0.2;
        if (selectedVehicle instanceof Car || selectedVehicle instanceof SUV) {
            rentalCost = selectedVehicle.calculateRental(
                rentalDays, 
                milesDriven, 
                customerAge, 
                wantsInsurance, 
                isVeteran
            );
        } else {
            double baseCost = selectedVehicle.getBaseFeePerDay() * rental.getRentalDurationInDays();
            double mileageCost = selectedVehicle.getRatePerMile() * rental.getMilesDriven();
            double insuranceCost = rental.getInsuranceFee() * rental.getRentalDurationInDays();
            
            double lateFeePerHour = selectedVehicle.getBaseFeePerDay() * 0.05;
            double lateFee = lateFeePerHour * lateHours;
            
            double subtotal = baseCost + mileageCost + insuranceCost + lateFee;
            double discount = 0.0;
            if (selectedCustomer.isSenior() || selectedCustomer.isVeteran()) {
                discount = subtotal * 0.10;
            }
            
            rentalCost = subtotal - discount;
        }
        
        System.out.println("\nVehicle Rental Summary");
        System.out.println("Customer: " + selectedCustomer.getName() + " (ID: " + selectedCustomer.getId() + ")");
        System.out.println("Vehicle: " + selectedVehicle.getYear() + " " + 
                          selectedVehicle.getMake() + " " + selectedVehicle.getModel());
        System.out.println("Rental period: " + rental.getRentalDurationInDays() + " days");
        System.out.println("Miles driven: " + rental.getMilesDriven());
        
        if (selectedVehicle instanceof Car) {
            Car car = (Car) selectedVehicle;
            if (car.isFunCar()) {
                System.out.println("Fun Car Theme: " + car.getFunCarTheme());
            }
        } else if (selectedVehicle instanceof SUV) {
            SUV suv = (SUV) selectedVehicle;
            if (suv.isOffRoadCapable()) {
                System.out.println("Off-Road Capable: Yes");
            }
        }
        
        if (isLate) {
            System.out.println("Late return: " + lateHours + " hours");
        }
        
        System.out.println("Insurance: " + (wantsInsurance ? "Yes" : "No"));
        
        if (selectedCustomer.isVeteran()) {
            System.out.println("Veteran discount applied");
        }
        
        if (selectedCustomer.isSenior()) {
            System.out.println("Senior discount applied");
        }
        
        System.out.println("\nTotal cost: $" + String.format("%.2f", rentalCost));
        
        scanner.close();
    }
    
    private static Customer createCustomerFromInput() {
        System.out.println("\nEnter customer details:");
        
        System.out.print("Name: ");
        String name = scanner.nextLine();
        
        System.out.print("License number: ");
        String licenseNumber = scanner.nextLine();
        
        LocalDate licenseExpirationDate = null;
        while (licenseExpirationDate == null) {
            System.out.print("License expiration date (yyyy-MM-dd): ");
            try {
                licenseExpirationDate = LocalDate.parse(scanner.nextLine(), dateFormatter);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd format.");
            }
        }
        
        LocalDate dateOfBirth = null;
        while (dateOfBirth == null) {
            System.out.print("Date of birth (yyyy-MM-dd): ");
            try {
                dateOfBirth = LocalDate.parse(scanner.nextLine(), dateFormatter);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd format.");
            }
        }
        
        System.out.print("Email: ");
        String email = scanner.nextLine();
        
        System.out.print("Phone: ");
        String phone = scanner.nextLine();
        
        System.out.print("Is the customer a veteran? (yes/no): ");
        boolean isVeteran = scanner.nextLine().trim().toLowerCase().startsWith("y");
        
        System.out.print("Is the customer a senior? (yes/no): ");
        boolean isSenior = scanner.nextLine().trim().toLowerCase().startsWith("y");
        
        return new Customer(name, licenseNumber, licenseExpirationDate, dateOfBirth, 
                           email, phone, isVeteran, isSenior);
    }
    
    private static int getIntInput(int min, int max) {
        int value = min - 1;
        while (value < min || value > max) {
            System.out.print("Enter a number between " + min + " and " + max + ": ");
            try {
                value = Integer.parseInt(scanner.nextLine().trim());
                if (value < min || value > max) {
                    System.out.println("Value must be between " + min + " and " + max);
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
        return value;
    }
    
    private static double getDoubleInput(double min, double max) {
        double value = min - 1;
        while (value < min || value > max) {
            System.out.print("Enter a number between " + min + " and " + max + ": ");
            try {
                value = Double.parseDouble(scanner.nextLine().trim());
                if (value < min || value > max) {
                    System.out.println("Value must be between " + min + " and " + max);
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
        return value;
    }
}
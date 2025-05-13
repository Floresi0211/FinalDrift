import java.util.*;

// Define the base Vehicle class
class Vehicle {
    String model;                    // Store the vehicle model
    String licensePlate;             // Store the vehicle license plate
    double baseFee;                  // Store the base rental fee
    int capacity;                    // Store the vehicle capacity
    String status = "AVAILABLE";     // Initialize the vehicle status as available

    // Constructor for Vehicle class
    Vehicle(String model, String licensePlate, double baseFee, int capacity) {
        this.model = model;              // Set the model
        this.licensePlate = licensePlate;// Set the license plate
        this.baseFee = baseFee;          // Set the base fee
        this.capacity = capacity;        // Set the capacity
    }

    // Method to calculate rental cost (to be overridden by subclasses)
    double calculateRental(int days, int miles, int age, boolean insurance, boolean isVeteran) {
        return 0;   // Default implementation returns 0
    }
}

// Define the Car class, which extends Vehicle
class Car extends Vehicle {
    // Constructor for Car class
    Car(String model, String licensePlate, double baseFee, int capacity) {
        super(model, licensePlate, baseFee, capacity);  // Call the superclass constructor
    }

    // Override the calculateRental method for Car
    @Override
    double calculateRental(int days, int miles, int age, boolean insurance, boolean isVeteran) {
        double rate = baseFee * days + miles * 0.4;  // Calculate base rate
        if (insurance) rate += 20;                   // Add insurance fee if selected
        if (age <= 25) rate += 30;                   // Add young driver fee
        if (isVeteran) rate -= 10;                   // Apply veteran discount
        return rate + rate * 0.08;                   // Add tax and return total
    }
}

// Define the Bike class, which extends Vehicle
class Bike extends Vehicle {
    // Constructor for Bike class
    Bike(String model, String licensePlate, double baseFee, int capacity) {
        super(model, licensePlate, baseFee, capacity);  // Call the superclass constructor
    }

    // Override the calculateRental method for Bike
    @Override
    double calculateRental(int days, int miles, int age, boolean insurance, boolean isVeteran) {
        double rate = baseFee * days + miles * 0.2;  // Calculate base rate
        if (insurance) rate += 10;                   // Add insurance fee if selected
        if (age <= 25) rate += 10;                   // Add young driver fee
        if (isVeteran) rate -= 5;                    // Apply veteran discount
        return rate + rate * 0.05;                   // Add tax and return total
    }
}

// Define the Truck class, which extends Vehicle
class Truck extends Vehicle {
    // Constructor for Truck class
    Truck(String model, String licensePlate, double baseFee, int capacity) {
        super(model, licensePlate, baseFee, capacity);  // Call the superclass constructor
    }

    // Override the calculateRental method for Truck
    @Override
    double calculateRental(int days, int miles, int age, boolean insurance, boolean isVeteran) {
        double rate = baseFee * days + miles * 0.6;  // Calculate base rate
        if (insurance) rate += 30;                   // Add insurance fee if selected
        if (age <= 25) rate += 40;                   // Add young driver fee
        if (isVeteran) rate -= 20;                   // Apply veteran discount
        return rate + rate * 0.1;                    // Add tax and return total
    }
}

// Define the Customer class
class Customer {
    String name;            // Store customer name
    int age;                // Store customer age
    boolean hasValidLicense;// Store license validity
    boolean isVeteran;      // Store veteran status

    // Constructor for Customer class
    Customer(String name, int age, boolean hasValidLicense, boolean isVeteran) {
        this.name = name;                       // Set the name
        this.age = age;                         // Set the age
        this.hasValidLicense = hasValidLicense; // Set license validity
        this.isVeteran = isVeteran;             // Set veteran status
    }
}

// Define the Rental class
class Rental {
    Vehicle vehicle;        // Store the rented vehicle
    Customer customer;      // Store the customer renting
    int rentalDays;         // Store the number of rental days
    int milesDriven;        // Store the miles driven
    boolean insurance;      // Store if insurance was taken
    LocalDateTime pickUpTime;   // Store pick-up time
    LocalDateTime dropOffTime;  // Store drop-off time

    // Constructor for Rental class
    Rental(Vehicle vehicle, Customer customer, int rentalDays, int milesDriven, boolean insurance) {
        this.vehicle = vehicle;                 // Set the vehicle
        this.customer = customer;               // Set the customer
        this.rentalDays = rentalDays;           // Set the rental days
        this.milesDriven = milesDriven;         // Set the miles driven
        this.insurance = insurance;             // Set the insurance status
        this.pickUpTime = LocalDateTime.now();  // Set pick-up time to now
        this.dropOffTime = this.pickUpTime.plusDays(rentalDays);  // Calculate drop-off time
        this.vehicle.status = "RENTED";         // Update vehicle status to rented
    }

    // Method to calculate total rental cost
    double getTotalCost() {
        return vehicle.calculateRental(rentalDays, milesDriven, customer.age, insurance, customer.isVeteran);
    }

    // Method to print rental receipt
    void printReceipt() {
        System.out.println("Booking Confirmed. Receipt: ");
        System.out.println("Customer: " + customer.name);
        System.out.println("Car: " + vehicle.model);
        System.out.println("License Plate: " + vehicle.licensePlate);
        System.out.println("Rental Duration: " + rentalDays + " day(s)");
        System.out.println("Miles Estimated: " + milesDriven);
        System.out.println("Pick-up: " + pickUpTime);
        System.out.println("Drop-off: " + dropOffTime);
        System.out.printf("Total Cost: $%.2f", getTotalCost());
    }
}

// Define the VehicleManager class
class VehicleManager {
    private List<Vehicle> vehicles = new ArrayList<>();  // List to store all vehicles

    // Method to add a vehicle to the manager
    void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    // Method to get a list of available vehicles
    List<Vehicle> getAvailableVehicles() {
        List<Vehicle> available = new ArrayList<>();
        for (Vehicle v : vehicles) {
            if (v.status.equals("AVAILABLE")) available.add(v);
        }
        return available;
    }
}

// Define the BookingManager class
class BookingManager {
    private List<Rental> rentals = new ArrayList<>();  // List to store all rentals

    // Method to check if a vehicle is available
    boolean isVehicleAvailable(Vehicle vehicle) {
        for (int i = 0; i < rentals.size(); i++) {
            if (rentals.get(i).vehicle == vehicle) {
                return false;
            }
        }
        return true;
    }

    // Method to schedule a new rental
    void scheduleRental(Rental rental) {
        rentals.add(rental);
    }
}

// Main class containing the program entry point
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);  // Create a Scanner for user input
        VehicleManager vehicleManager = new VehicleManager();  // Create a VehicleManager
        BookingManager bookingManager = new BookingManager();  // Create a BookingManager

        // Add different types of vehicles
        vehicleManager.addVehicle(new Car("Honda Accord", "CAR-002", 65, 5));
        vehicleManager.addVehicle(new Car("Mercedes-Benz C300", "CAR-003", 120, 5));
        vehicleManager.addVehicle(new Car("Tesla Model 3", "CAR-004", 150, 5));
        vehicleManager.addVehicle(new Car("BMW 3 Series", "CAR-006", 140, 5));

        vehicleManager.addVehicle(new Bike("Kawasaki Ninja", "BIKE-124", 35, 1));
        vehicleManager.addVehicle(new Bike("Ducati Monster", "BIKE-127", 55, 1));
       
        vehicleManager.addVehicle(new Truck("GMC Sierra", "TRUCK-459", 120, 3));
        vehicleManager.addVehicle(new Truck("Toyota Tundra", "TRUCK-460", 105, 3));
        vehicleManager.addVehicle(new Truck("Ford F-150", "TRUCK-456", 100, 3));

        System.out.println("Welcome to the Vehicle Rental System");
        System.out.println("Policy: Valid license required. Discounts for veterans. Fees vary by age, insurance, and vehicle type.");

        while (true) {
            System.out.print("\nEnter your name: ");
            String name = scanner.nextLine();

            System.out.print("Enter your age: ");
            int age = Integer.parseInt(scanner.nextLine());

            System.out.print("Do you have a valid license? (1 for Yes, 2 for No): ");
            int licenseOption = Integer.parseInt(scanner.nextLine());
            if (licenseOption != 1) {
                System.out.println("You must have a valid license to proceed.");
                continue;
            }

            System.out.print("Are you a veteran? (1 for Yes, 2 for No): ");
            int vetOption = Integer.parseInt(scanner.nextLine());

            Customer customer = new Customer(name, age, true, vetOption == 1);

            List<Vehicle> availableVehicles = vehicleManager.getAvailableVehicles();
            if (availableVehicles.isEmpty()) {
                System.out.println("No vehicles available.");
                break;
            }

            System.out.println("\nAvailable Vehicles: ");
            for (int i = 0; i < availableVehicles.size(); i++) {
                Vehicle v = availableVehicles.get(i);
                System.out.printf("%d. %s (%s) - $%.2f/day, Capacity: %d, Status: %s\n", 
                    i + 1, v.model, v.licensePlate, v.baseFee, v.capacity, v.status);
            }

            System.out.print("\nEnter the number of the vehicle to rent: ");
            int choice = Integer.parseInt(scanner.nextLine());
            if (choice < 1 || choice > availableVehicles.size()) {
                System.out.println("Invalid choice.");
                continue;
            }
            Vehicle selectedVehicle = availableVehicles.get(choice - 1);

            System.out.print("Enter number of rental days: ");
            int days = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter estimated miles to drive: ");
            int miles = Integer.parseInt(scanner.nextLine());

            System.out.print("Do you want insurance? (1 for Yes, 2 for No): ");
            int insuranceOption = Integer.parseInt(scanner.nextLine());
            boolean insurance = (insuranceOption == 1);

            if (!bookingManager.isVehicleAvailable(selectedVehicle)) {
                System.out.println("Sorry, that vehicle is already rented.");
                continue;
            }

            Rental rental = new Rental(selectedVehicle, customer, days, miles, insurance);
            bookingManager.scheduleRental(rental);
            rental.printReceipt();

            System.out.print("Would you like to rent another vehicle? Press 1 to continue or any other number to exit: ");
            int userChoice = scanner.nextInt();
            scanner.nextLine();
            if (userChoice != 1) {
                break;
            }
        }

        scanner.close();  // Close the scanner
        System.out.println("Thank you for using the Vehicle Rental System.");
    }
}

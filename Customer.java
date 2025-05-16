import java.time.LocalDate;
import java.util.UUID;

class Customer {
    private String id;
    private String name;
    private String licenseNumber;
    private LocalDate licenseExpirationDate;
    private LocalDate dateOfBirth;
    private String email;
    private String phone;
    private boolean isVeteran;
    private boolean isSenior;
    
    public Customer(String name, String licenseNumber, LocalDate licenseExpirationDate,
                   LocalDate dateOfBirth, String email, String phone, 
                   boolean isVeteran, boolean isSenior) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.licenseNumber = licenseNumber;
        this.licenseExpirationDate = licenseExpirationDate;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phone = phone;
        this.isVeteran = isVeteran;
        this.isSenior = isSenior;
    }
    
    public Customer(String name, int age, boolean hasValidLicense, boolean isVeteran) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.dateOfBirth = LocalDate.now().minusYears(age);
        this.licenseExpirationDate = hasValidLicense ? LocalDate.now().plusYears(5) : LocalDate.now().minusDays(1);
        this.licenseNumber = "DL" + Math.abs(name.hashCode());
        this.email = name.toLowerCase().replace(' ', '.') + "@example.com";
        this.phone = "555-" + (Math.abs(name.hashCode()) % 900 + 100) + "-" + (Math.abs(name.hashCode() / 1000) % 9000 + 1000);
        this.isVeteran = isVeteran;
        this.isSenior = age >= 65;
    }
    
    // Getters and setters
    public String getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getLicenseNumber() { return licenseNumber; }
    public void setLicenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; }
    public LocalDate getLicenseExpirationDate() { return licenseExpirationDate; }
    public void setLicenseExpirationDate(LocalDate licenseExpirationDate) { this.licenseExpirationDate = licenseExpirationDate; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public boolean isVeteran() { return isVeteran; }
    public void setVeteran(boolean veteran) { isVeteran = veteran; }
    public boolean isSenior() { return isSenior; }
    public void setSenior(boolean senior) { isSenior = senior; }
    
    public int getAge() {
        return LocalDate.now().getYear() - dateOfBirth.getYear();
    }
    
    public boolean isLicenseValid() {
        return licenseExpirationDate.isAfter(LocalDate.now());
    }
    
    @Override
    public String toString() {
        return name + " (License: " + licenseNumber + ")";
    }
}
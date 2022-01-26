import java.io.Serializable;
import java.time.LocalDate;

public class Employee implements Serializable {

    private final String name;
    private final int employeeNumber;
    private LocalDate dateOfHire;

    public Employee(String name, int employeeNumber, String date) {
        this.name = name;
        this.employeeNumber = employeeNumber;
        setDateOfHire(date);
    }

    public void setDateOfHire(String date) {
        dateOfHire = LocalDate.parse(date);
    }

    @Override
    public String toString(){
        return String.format("%s | %d | %s%n", name, employeeNumber, dateOfHire);
    }
}

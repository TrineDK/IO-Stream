import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class EmployeeDriver {

    static List<Employee> employeeList = new ArrayList<>();
    static String userSelection;
    static Scanner sc = new Scanner(System.in);
    private  static String binaryFile = "binary.bin";
    private  static String textFile = "text.txt";
    private  static String objectFile = "object.obj";
    private static final Employee testEmployee1 = new Employee("John Doe", 123, "1980-09-10");
    private static final Employee testEmployee2 = new Employee("Sally Smith", 456, "1990-11-12");


    public static void main (String[] args) throws IOException, ClassNotFoundException {

        employeeList.add(testEmployee1);
        employeeList.add(testEmployee2);

        System.out.println("Please add employee information and enter 9 when done.");
        addEmployee();

        System.out.println("Please enter one of the following options process your data or access the help menu:");
        System.out.println("--help\n--binary\n--text\n--object");
        processUserSelection(userSelection = sc.nextLine());

        sc.close();
    }


    public static void addEmployee() {
        int flag = 0;

        try {
            while (flag != 9) {
                System.out.println("Please enter the full name of the employee: ");
                String name = sc.nextLine();
                System.out.println("Please enter the employee number: ");
                int employeeNumber = sc.nextInt();
                System.out.println("Please enter the date of hire (YYYY-MM-DD): ");
                String date = sc.next();
                System.out.println("Please add another employee or press 9 to exit.");
                flag = sc.nextInt();
                sc.nextLine();
            employeeList.add(new Employee(name, employeeNumber, date));
            }
        } catch (InputMismatchException ex) {
            System.out.println("Entry is invalid. Please follow the input format.");
        }
}


    public static String displayEmployeeInfo(List<Employee> list) {

        StringBuilder sb = new StringBuilder();

        String str;

        for (Employee employee : list) {
            sb.append(employee);
        }

        str = sb.toString();
        return str;
    }


    public static void processUserSelection(String userSelection) throws IOException, ClassNotFoundException {

        while (!userSelection.equalsIgnoreCase("X")) {
            switch (userSelection) {
                case "--help":
                    helpMenu();
                    break;
                case "--binary":
                    writeToBinaryFile();
                    readFromBinaryFile();
                    break;
                case "--text":
                    writeToTextFile();
                    readFromTextFile();
                    break;
                case "--object":
                    writeObjectToFile();
                    readObjectFromFile();
                    break;
                default:
                    System.out.println("Invalid entry. Please enter --help, --binary, --text, or --object.");
                    processUserSelection(sc.nextLine());
                    break;
            }
            System.out.println("Please select a new option or press X to exit.");
            userSelection = sc.next();
        }
    }


    public static void helpMenu() throws IOException, ClassNotFoundException {
        System.out.println("Welcome to my fancy program, which will process your employee data in a multitude of ways. " +
                "Please enter --binary for your employee list to be processed as a binary file, enter --text to " +
                "process it as a text file, and --object to process it as a list of objects.");
        processUserSelection(userSelection = sc.nextLine());
    }

    public static void writeToBinaryFile() throws IOException {

        DataOutputStream dataOut = new DataOutputStream(
                new FileOutputStream(binaryFile));

        dataOut.writeChars(displayEmployeeInfo(employeeList));
        dataOut.close();
    }


    public static void readFromBinaryFile() throws IOException {

        DataInputStream dataIn = new DataInputStream(new FileInputStream(binaryFile));

        while(dataIn.available() >=1){
            char ch = dataIn.readChar();
            System.out.print(ch);
        }
    }


    public static void writeToTextFile() throws IOException {

       PrintWriter pw = new PrintWriter(textFile, StandardCharsets.UTF_8);
       pw.println(displayEmployeeInfo(employeeList));
       pw.close();
    }


    public static void readFromTextFile() throws IOException {

        String pathName = System.getProperty("user.dir") + File.separator + textFile;
        Path path = Paths.get(pathName);
        String employees = new String(Files.readAllBytes(path));
        System.out.println(employees);
    }


    public static void writeObjectToFile() throws IOException {

        ObjectOutputStream objectOut = new ObjectOutputStream(new FileOutputStream(objectFile));

        objectOut.writeObject(employeeList);
        objectOut.close();
    }


    public static void readObjectFromFile() throws IOException, ClassNotFoundException {

        List<Employee> newEmployeeList;
        ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream(objectFile));

        newEmployeeList = (List<Employee>) objectIn.readObject();
        System.out.print(displayEmployeeInfo(newEmployeeList));
    }
}

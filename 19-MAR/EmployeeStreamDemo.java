import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EmployeeStreamDemo {

    public static void main(String[] args) {

        // Dataset stored in collection
        List<Employee> employees = Arrays.asList(
                new Employee("Ali", 25, "IT", 50000),
                new Employee("Sara", 32, "HR", 60000),
                new Employee("Ahmed", 40, "Finance", 70000),
                new Employee("Zara", 28, "IT", 55000),
                new Employee("Usman", 35, "Marketing", 65000)
        );

        // Function Interface: name + department
        Function<Employee, String> nameDeptFunction =
                emp -> emp.getName() + " - " + emp.getDepartment();

        // Stream: transform collection
        List<String> nameDeptList = employees.stream()
                .map(nameDeptFunction)
                .collect(Collectors.toList());

        System.out.println("Employee Name & Department:");
        nameDeptList.forEach(System.out::println);

        // Average salary using streams
        double avgSalary = employees.stream()
                .mapToDouble(Employee::getSalary)
                .average()
                .orElse(0);

        System.out.println("\nAverage Salary: " + avgSalary);

        // Filter employees age > 30
        System.out.println("\nEmployees Age > 30:");
        employees.stream()
                .filter(emp -> emp.getAge() > 30)
                .forEach(emp ->
                        System.out.println(emp.getName() + " (" + emp.getAge() + ")")
                );

        // Bonus: Highest salary employee
        employees.stream()
                .max(Comparator.comparing(Employee::getSalary))
                .ifPresent(emp ->
                        System.out.println("\nHighest Paid Employee: " + emp.getName())
                );
    }
}


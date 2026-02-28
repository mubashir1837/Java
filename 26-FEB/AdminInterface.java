import java.util.Scanner;
import java.util.ArrayList;

public class AdminInterface {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Student> students = new ArrayList<>();

        while (true) {
            System.out.println("\n--- University Course Management ---");
            System.out.println("1. Add Course");
            System.out.println("2. Enroll Student");
            System.out.println("3. Assign Grade");
            System.out.println("4. Calculate Overall Grade");
            System.out.println("5. List Courses");
            System.out.println("6. Exit");
            System.out.print("Select option: ");

            int choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Course Code: "); 
                    String code = sc.nextLine();
                    System.out.print("Course Name: "); 
                    String name = sc.nextLine();
                    System.out.print("Max Capacity: "); 
                    int cap = sc.nextInt(); sc.nextLine();
                    CourseManagement.addCourse(code, name, cap);
                    System.out.println("Course added successfully!");
                    break;

                case 2:
                    System.out.print("Student Name: "); 
                    String sName = sc.nextLine();
                    System.out.print("Student ID: "); 
                    String sId = sc.nextLine();
                    Student student = new Student(sName, sId);
                    students.add(student);

                    CourseManagement.listCourses();
                    System.out.print("Enter course code to enroll: "); 
                    String cCode = sc.nextLine();
                    boolean enrolled = false;
                    for (Course c : CourseManagement.getCourses()) {
                        if (c.getCourseCode().equals(cCode)) {
                            CourseManagement.enrollStudent(student, c);
                            enrolled = true;
                            System.out.println("Student enrolled in " + c.getName());
                            break;
                        }
                    }
                    if (!enrolled) System.out.println("Course not found!");
                    break;

                case 3:
                    System.out.print("Student ID: "); 
                    String sid = sc.nextLine();
                    Student st = students.stream()
                                         .filter(s -> s.getId().equals(sid))
                                         .findFirst()
                                         .orElse(null);
                    if (st != null) {
                        System.out.print("Course Code: "); 
                        String cc = sc.nextLine();
                        System.out.print("Grade: "); 
                        double grade = sc.nextDouble(); 
                        sc.nextLine(); // Consume newline
                        boolean assigned = false;
                        for (Course c : CourseManagement.getCourses()) {
                            if (c.getCourseCode().equals(cc)) {
                                CourseManagement.assignGrade(st, c, grade);
                                assigned = true;
                                System.out.println("Grade assigned successfully!");
                                break;
                            }
                        }
                        if (!assigned) System.out.println("Course not found!");
                    } else {
                        System.out.println("Student not found!");
                    }
                    break;

                case 4:
                    System.out.print("Student ID: "); 
                    String stId = sc.nextLine();
                    Student s = students.stream()
                                        .filter(stu -> stu.getId().equals(stId))
                                        .findFirst()
                                        .orElse(null);
                    if (s != null) 
                        System.out.println("Overall Grade: " + CourseManagement.calculateOverallGrade(s));
                    else 
                        System.out.println("Student not found!");
                    break;

                case 5:
                    CourseManagement.listCourses();
                    break;

                case 6:
                    System.out.println("Exiting...");
                    sc.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
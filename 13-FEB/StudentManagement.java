import java.util.ArrayList;

public class StudentManagement {

    // Private static variables
    private static ArrayList<Student> studentList = new ArrayList<>();
    private static int totalStudents = 0;

    // Add student
    public static void addStudent(String name, String id, int age, double grade) {

        if (findStudentById(id) != null) {
            System.out.println("Error: Student ID already exists.");
            return;
        }

        Student newStudent = new Student(name, id, age, grade);
        studentList.add(newStudent);
        totalStudents++;

        System.out.println("Student added successfully!");
    }


    // Update student
    public static void updateStudent(String id, String newName, int newAge, double newGrade) {

        Student student = findStudentById(id);

        if (student == null) {
            System.out.println("Error: Student ID not found.");
            return;
        }

        student.setName(newName);
        student.setAge(newAge);
        student.setGrade(newGrade);

        System.out.println("Student updated successfully!");
    }



    // View student
    public static void viewStudent(String id) {

        Student student = findStudentById(id);

        if (student == null) {
            System.out.println("Error: Student ID not found.");
        } else {
            student.displayStudent();
        }
    }



    // Helper method
    private static Student findStudentById(String id) {
        for (Student s : studentList) {
            if (s.getId().equals(id)) {
                return s;
            }
        }
        return null;
    }

    public static int getTotalStudents() {
        return totalStudents;
    }

}




import java.util.ArrayList;

public class CourseManagement {
    private static ArrayList<Course> courses = new ArrayList<>();

    // Add a new course
    public static void addCourse(String code, String name, int capacity) {
        courses.add(new Course(code, name, capacity));
    }

    // Enroll student in a course
    public static void enrollStudent(Student student, Course course) {
        student.enrollCourse(course);
    }

    // Assign grade to a student
    public static void assignGrade(Student student, Course course, double grade) {
        student.assignGrade(course, grade);
    }

    public static double calculateOverallGrade(Student student) {
        return student.calculateOverallGrade();
    }

    public static void listCourses() {
        for (Course c : courses) {
            System.out.println(c.getCourseCode() + " - " + c.getName() + " (" + c.getEnrolledCount() + "/"
                    + c.getMaxCapacity() + ")");
        }
    }



    // Getter for courses list so other classes can access
    public static ArrayList<Course> getCourses() {
        return courses;
    }
}



import java.util.HashMap;
import java.util.ArrayList;

public class Student {
    // Private instance variables
    private String name;
    private String id;
    private ArrayList<Course> enrolledCourses;
    private HashMap<String, Double> courseGrades; // Course code -> Grade

    // Constructor
    public Student(String name, String id) {
        this.name = name;
        this.id = id;
        this.enrolledCourses = new ArrayList<>();
        this.courseGrades = new HashMap<>();
    }

    // Getter and Setter methods
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public ArrayList<Course> getEnrolledCourses() { return enrolledCourses; }

    // Enroll in a course
    public void enrollCourse(Course course) {
        if (!enrolledCourses.contains(course)) {
            enrolledCourses.add(course);
            course.incrementEnrollment(); // Update course enrollment count
        }
    }

    // Assign grade for a course
    public void assignGrade(Course course, double grade) {
        if (enrolledCourses.contains(course)) {
            courseGrades.put(course.getCourseCode(), grade);
        } else {
            System.out.println("Student not enrolled in " + course.getCourseCode());
        }
    }

    // Calculate overall grade (average)
    public double calculateOverallGrade() {
        double total = 0;
        for (double g : courseGrades.values()) total += g;
        return courseGrades.size() > 0 ? total / courseGrades.size() : 0;
    }
}
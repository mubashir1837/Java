public class Course {
    private String courseCode;
    private String name;
    private int maxCapacity;
    private int enrolledCount;

    // Static variable for total enrollment
    private static int totalEnrolled = 0;

    public Course(String courseCode, String name, int maxCapacity) {
        this.courseCode = courseCode;
        this.name = name;
        this.maxCapacity = maxCapacity;
        this.enrolledCount = 0;
    }

    // Getters
    public String getCourseCode() {
        return courseCode;
    }

    public String getName() {
        return name;
    }
    public int getMaxCapacity() {
        return maxCapacity;
    }

    public int getEnrolledCount() {
        return enrolledCount;
    }

    // Increment enrollment
    public void incrementEnrollment(){
        if (enrolledCount < maxCapacity){
            enrolledCount++;
            totalEnrolled++;
        } else {
            System.out.print("course" + name + "is full!");
        }
    }

    // Static method to get total enrolled students
    public static int getTotalEnrolled() {
        return totalEnrolled;
    }


}
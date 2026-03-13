package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Course class representing a course in the management system.
 * Contains course information and enrolled students.
 */
public class Course {
    private String courseId;
    private String courseName;
    private String courseCode;
    private String description;
    private String department;
    private int credits;
    private String instructor;
    private int maxCapacity;
    private List<String> enrolledStudents;
    
    /**
     * Default constructor
     */
    public Course() {
        this.enrolledStudents = new ArrayList<>();
    }
    
    /**
     * Parameterized constructor
     * 
     * @param courseId Unique identifier for the course
     * @param courseName Name of the course
     * @param courseCode Course code (e.g., CS101)
     * @param description Course description
     * @param department Department offering the course
     * @param credits Number of credits for the course
     * @param instructor Name of the instructor
     * @param maxCapacity Maximum number of students allowed
     */
    public Course(String courseId, String courseName, String courseCode,
                  String description, String department, int credits,
                  String instructor, int maxCapacity) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.description = description;
        this.department = department;
        this.credits = credits;
        this.instructor = instructor;
        this.maxCapacity = maxCapacity;
        this.enrolledStudents = new ArrayList<>();
    }
    
    // Getters and Setters
    
    public String getCourseId() {
        return courseId;
    }
    
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
    
    public String getCourseName() {
        return courseName;
    }
    
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    
    public String getCourseCode() {
        return courseCode;
    }
    
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDepartment() {
        return department;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }
    
    public int getCredits() {
        return credits;
    }
    
    public void setCredits(int credits) {
        this.credits = credits;
    }
    
    public String getInstructor() {
        return instructor;
    }
    
    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }
    
    public int getMaxCapacity() {
        return maxCapacity;
    }
    
    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }
    
    public List<String> getEnrolledStudents() {
        return enrolledStudents;
    }
    
    public void setEnrolledStudents(List<String> enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }
    
    /**
     * Get current enrollment count
     * @return Number of enrolled students
     */
    public int getEnrollmentCount() {
        return enrolledStudents.size();
    }
    
    /**
     * Check if course has available space
     * @return true if course is not full
     */
    public boolean hasAvailableSpace() {
        return enrolledStudents.size() < maxCapacity;
    }
    
    /**
     * Enroll a student in this course
     * @param studentId The student ID to enroll
     * @return true if enrollment successful, false if full or already enrolled
     */
    public boolean enrollStudent(String studentId) {
        if (hasAvailableSpace() && !enrolledStudents.contains(studentId)) {
            enrolledStudents.add(studentId);
            return true;
        }
        return false;
    }
    
    /**
     * Remove a student from this course
     * @param studentId The student ID to remove
     * @return true if removed successfully, false if not enrolled
     */
    public boolean removeStudent(String studentId) {
        return enrolledStudents.remove(studentId);
    }
    
    /**
     * Check if a student is enrolled in this course
     * @param studentId The student ID to check
     * @return true if student is enrolled
     */
    public boolean isStudentEnrolled(String studentId) {
        return enrolledStudents.contains(studentId);
    }
    
    @Override
    public String toString() {
        return courseCode + " - " + courseName + " (" + getEnrollmentCount() + "/" + maxCapacity + ")";
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Course course = (Course) obj;
        return courseId != null && courseId.equals(course.courseId);
    }
    
    @Override
    public int hashCode() {
        return courseId != null ? courseId.hashCode() : 0;
    }
}

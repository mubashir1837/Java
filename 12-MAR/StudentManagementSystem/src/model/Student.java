package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Student class representing a student in the management system.
 * Contains student personal information and enrolled courses.
 */
public class Student {
    private String studentId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String dateOfBirth;
    private String major;
    private List<String> enrolledCourses;
    
    /**
     * Default constructor
     */
    public Student() {
        this.enrolledCourses = new ArrayList<>();
    }
    
    /**
     * Parameterized constructor
     * 
     * @param studentId Unique identifier for the student
     * @param firstName Student's first name
     * @param lastName Student's last name
     * @param email Student's email address
     * @param phone Student's phone number
     * @param address Student's address
     * @param dateOfBirth Student's date of birth
     * @param major Student's major/field of study
     */
    public Student(String studentId, String firstName, String lastName, 
                   String email, String phone, String address, 
                   String dateOfBirth, String major) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.major = major;
        this.enrolledCourses = new ArrayList<>();
    }
    
    // Getters and Setters
    
    public String getStudentId() {
        return studentId;
    }
    
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getDateOfBirth() {
        return dateOfBirth;
    }
    
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    public String getMajor() {
        return major;
    }
    
    public void setMajor(String major) {
        this.major = major;
    }
    
    public List<String> getEnrolledCourses() {
        return enrolledCourses;
    }
    
    public void setEnrolledCourses(List<String> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }
    
    /**
     * Enroll student in a course
     * @param courseId The course ID to enroll in
     * @return true if enrollment successful, false if already enrolled
     */
    public boolean enrollInCourse(String courseId) {
        if (!enrolledCourses.contains(courseId)) {
            enrolledCourses.add(courseId);
            return true;
        }
        return false;
    }
    
    /**
     * Drop a course
     * @param courseId The course ID to drop
     * @return true if dropped successfully, false if not enrolled
     */
    public boolean dropCourse(String courseId) {
        return enrolledCourses.remove(courseId);
    }
    
    @Override
    public String toString() {
        return "Student{" +
                "studentId='" + studentId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", major='" + major + '\'' +
                '}';
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Student student = (Student) obj;
        return studentId != null && studentId.equals(student.studentId);
    }
    
    @Override
    public int hashCode() {
        return studentId != null ? studentId.hashCode() : 0;
    }
}

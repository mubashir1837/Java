package data;

import model.Student;
import model.Course;
import model.Grade;

import java.util.*;

public class DataManager {
    private static DataManager instance;
    
    // Data storage
    private Map<String, Student> students;
    private Map<String, Course> courses;
    private Map<String, Grade> grades;
    
    // ID counters for generating unique IDs
    private int studentIdCounter;
    private int courseIdCounter;
    private int gradeIdCounter;
    
    /**
     * Private constructor for singleton pattern
     */
    private DataManager() {
        students = new HashMap<>();
        courses = new HashMap<>();
        grades = new HashMap<>();
        studentIdCounter = 1000;
        courseIdCounter = 100;
        gradeIdCounter = 1;
        
        // Initialize with sample data
        initializeSampleData();
    }
    
    /**
     * Get the singleton instance of DataManager
     * @return DataManager instance
     */
    public static synchronized DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }
    
   
    public static synchronized void resetInstance() {
        instance = null;
    }
    
    
    /**
     * Generate a new unique student ID
     * @return New student ID
     */
    public String generateStudentId() {
        return "STU" + (studentIdCounter++);
    }
    
    /**
     * Add a new student to the system
     * @param student The student to add
     * @return true if added successfully, false if student ID already exists
     */
    public boolean addStudent(Student student) {
        if (students.containsKey(student.getStudentId())) {
            return false;
        }
        students.put(student.getStudentId(), student);
        return true;
    }
    
    /**
     * Update an existing student
     * @param student The student with updated information
     * @return true if updated successfully, false if student doesn't exist
     */
    public boolean updateStudent(Student student) {
        if (!students.containsKey(student.getStudentId())) {
            return false;
        }
        students.put(student.getStudentId(), student);
        return true;
    }
    
    /**
     * Delete a student from the system
     * @param studentId The ID of the student to delete
     * @return true if deleted successfully, false if student doesn't exist
     */
    public boolean deleteStudent(String studentId) {
        if (!students.containsKey(studentId)) {
            return false;
        }
        
        // Remove student from all enrolled courses
        Student student = students.get(studentId);
        for (String courseId : student.getEnrolledCourses()) {
            Course course = courses.get(courseId);
            if (course != null) {
                course.removeStudent(studentId);
            }
        }
        
        // Remove all grades for this student
        grades.values().removeIf(grade -> grade.getStudentId().equals(studentId));
        
        // Remove the student
        students.remove(studentId);
        return true;
    }
    
    /**
     * Get a student by ID
     * @param studentId The student ID
     * @return The Student object, or null if not found
     */
    public Student getStudent(String studentId) {
        return students.get(studentId);
    }
    
    /**
     * Get all students
     * @return List of all students
     */
    public List<Student> getAllStudents() {
        return new ArrayList<>(students.values());
    }
    
    /**
     * Search students by name
     * @param searchTerm The search term
     * @return List of matching students
     */
    public List<Student> searchStudentsByName(String searchTerm) {
        List<Student> results = new ArrayList<>();
        String lowerSearch = searchTerm.toLowerCase();
        
        for (Student student : students.values()) {
            if (student.getFirstName().toLowerCase().contains(lowerSearch) ||
                student.getLastName().toLowerCase().contains(lowerSearch) ||
                student.getFullName().toLowerCase().contains(lowerSearch)) {
                results.add(student);
            }
        }
        return results;
    }
    
    /**
     * Get students eligible for course enrollment
     * (students not already enrolled in the course)
     * @param courseId The course ID
     * @return List of eligible students
     */
    public List<Student> getEligibleStudentsForCourse(String courseId) {
        List<Student> eligible = new ArrayList<>();
        Course course = courses.get(courseId);
        
        if (course == null) return eligible;
        
        for (Student student : students.values()) {
            if (!course.isStudentEnrolled(student.getStudentId())) {
                eligible.add(student);
            }
        }
        return eligible;
    }
    
    
    /**
     * Generate a new unique course ID
     * @return New course ID
     */
    public String generateCourseId() {
        return "CRS" + (courseIdCounter++);
    }
    
    /**
     * Add a new course to the system
     * @param course The course to add
     * @return true if added successfully, false if course ID already exists
     */
    public boolean addCourse(Course course) {
        if (courses.containsKey(course.getCourseId())) {
            return false;
        }
        courses.put(course.getCourseId(), course);
        return true;
    }
    
    /**
     * Update an existing course
     * @param course The course with updated information
     * @return true if updated successfully, false if course doesn't exist
     */
    public boolean updateCourse(Course course) {
        if (!courses.containsKey(course.getCourseId())) {
            return false;
        }
        courses.put(course.getCourseId(), course);
        return true;
    }
    
    /**
     * Delete a course from the system
     * @param courseId The ID of the course to delete
     * @return true if deleted successfully, false if course doesn't exist
     */
    public boolean deleteCourse(String courseId) {
        if (!courses.containsKey(courseId)) {
            return false;
        }
        
        // Remove course from all enrolled students
        Course course = courses.get(courseId);
        for (String studentId : course.getEnrolledStudents()) {
            Student student = students.get(studentId);
            if (student != null) {
                student.dropCourse(courseId);
            }
        }
        
        // Remove all grades for this course
        grades.values().removeIf(grade -> grade.getCourseId().equals(courseId));
        
        // Remove the course
        courses.remove(courseId);
        return true;
    }
    
    /**
     * Get a course by ID
     * @param courseId The course ID
     * @return The Course object, or null if not found
     */
    public Course getCourse(String courseId) {
        return courses.get(courseId);
    }
    
    /**
     * Get all courses
     * @return List of all courses
     */
    public List<Course> getAllCourses() {
        return new ArrayList<>(courses.values());
    }
    
    /**
     * Get courses a student is enrolled in
     * @param studentId The student ID
     * @return List of courses the student is enrolled in
     */
    public List<Course> getStudentCourses(String studentId) {
        List<Course> studentCourses = new ArrayList<>();
        Student student = students.get(studentId);
        
        if (student == null) return studentCourses;
        
        for (String courseId : student.getEnrolledCourses()) {
            Course course = courses.get(courseId);
            if (course != null) {
                studentCourses.add(course);
            }
        }
        return studentCourses;
    }
    
    // ==================== Enrollment Operations ====================
    
    /**
     * Enroll a student in a course
     * @param studentId The student ID
     * @param courseId The course ID
     * @return true if enrollment successful, false otherwise
     */
    public boolean enrollStudentInCourse(String studentId, String courseId) {
        Student student = students.get(studentId);
        Course course = courses.get(courseId);
        
        if (student == null || course == null) {
            return false;
        }
        
        boolean enrolledInCourse = course.enrollStudent(studentId);
        boolean enrolledInStudent = student.enrollInCourse(courseId);
        
        // Create a grade record for this enrollment
        if (enrolledInCourse && enrolledInStudent) {
            String gradeId = generateGradeId();
            Grade grade = new Grade(gradeId, studentId, courseId, "Fall", "2023-2024");
            grades.put(gradeId, grade);
        }
        
        return enrolledInCourse && enrolledInStudent;
    }
    
    /**
     * Remove a student from a course
     * @param studentId The student ID
     * @param courseId The course ID
     * @return true if removal successful, false otherwise
     */
    public boolean removeStudentFromCourse(String studentId, String courseId) {
        Student student = students.get(studentId);
        Course course = courses.get(courseId);
        
        if (student == null || course == null) {
            return false;
        }
        
        boolean removedFromCourse = course.removeStudent(studentId);
        boolean removedFromStudent = student.dropCourse(courseId);
        
        // Remove associated grade record
        if (removedFromCourse && removedFromStudent) {
            grades.values().removeIf(grade -> 
                grade.getStudentId().equals(studentId) && 
                grade.getCourseId().equals(courseId));
        }
        
        return removedFromCourse && removedFromStudent;
    }
    
    // ==================== Grade Operations ====================
    
    /**
     * Generate a new unique grade ID
     * @return New grade ID
     */
    public String generateGradeId() {
        return "GRD" + (gradeIdCounter++);
    }
    
    /**
     * Get grade for a student in a specific course
     * @param studentId The student ID
     * @param courseId The course ID
     * @return The Grade object, or null if not found
     */
    public Grade getGrade(String studentId, String courseId) {
        for (Grade grade : grades.values()) {
            if (grade.getStudentId().equals(studentId) && 
                grade.getCourseId().equals(courseId)) {
                return grade;
            }
        }
        return null;
    }
    
    /**
     * Update a grade
     * @param grade The grade to update
     * @return true if updated successfully, false if grade doesn't exist
     */
    public boolean updateGrade(Grade grade) {
        if (!grades.containsKey(grade.getGradeId())) {
            return false;
        }
        grades.put(grade.getGradeId(), grade);
        return true;
    }
    
    /**
     * Get all grades for a student
     * @param studentId The student ID
     * @return List of grades for the student
     */
    public List<Grade> getStudentGrades(String studentId) {
        List<Grade> studentGrades = new ArrayList<>();
        for (Grade grade : grades.values()) {
            if (grade.getStudentId().equals(studentId)) {
                studentGrades.add(grade);
            }
        }
        return studentGrades;
    }
    
    /**
     * Get all grades for a course
     * @param courseId The course ID
     * @return List of grades for the course
     */
    public List<Grade> getCourseGrades(String courseId) {
        List<Grade> courseGrades = new ArrayList<>();
        for (Grade grade : grades.values()) {
            if (grade.getCourseId().equals(courseId)) {
                courseGrades.add(grade);
            }
        }
        return courseGrades;
    }
    
    /**
     * Get all grades
     * @return List of all grades
     */
    public List<Grade> getAllGrades() {
        return new ArrayList<>(grades.values());
    }
    
    // ==================== Statistics ====================
    
    /**
     * Get total number of students
     * @return Student count
     */
    public int getStudentCount() {
        return students.size();
    }
    
    /**
     * Get total number of courses
     * @return Course count
     */
    public int getCourseCount() {
        return courses.size();
    }
    
    /**
     * Get total number of enrollments
     * @return Enrollment count
     */
    public int getEnrollmentCount() {
        int count = 0;
        for (Course course : courses.values()) {
            count += course.getEnrollmentCount();
        }
        return count;
    }
    
    /**
     * Calculate average grade for a course
     * @param courseId The course ID
     * @return Average grade, or -1 if no grades
     */
    public double getCourseAverage(String courseId) {
        List<Grade> courseGrades = getCourseGrades(courseId);
        if (courseGrades.isEmpty()) {
            return -1;
        }
        
        double sum = 0;
        for (Grade grade : courseGrades) {
            sum += grade.getTotalScore();
        }
        return sum / courseGrades.size();
    }
    
    /**
     * Calculate student's GPA
     * @param studentId The student ID
     * @return GPA, or -1 if no grades
     */
    public double calculateGPA(String studentId) {
        List<Grade> studentGrades = getStudentGrades(studentId);
        if (studentGrades.isEmpty()) {
            return -1;
        }
        
        double totalPoints = 0;
        int count = 0;
        
        for (Grade grade : studentGrades) {
            if (grade.getTotalScore() > 0) {
                totalPoints += convertToGradePoints(grade.getLetterGrade());
                count++;
            }
        }
        
        return count > 0 ? totalPoints / count : -1;
    }
    
    /**
     * Convert letter grade to grade points
     * @param letterGrade The letter grade
     * @return Grade points (0-4.0 scale)
     */
    private double convertToGradePoints(String letterGrade) {
        switch (letterGrade) {
            case "A+": case "A": return 4.0;
            case "A-": return 3.7;
            case "B+": return 3.3;
            case "B": return 3.0;
            case "B-": return 2.7;
            case "C+": return 2.3;
            case "C": return 2.0;
            case "C-": return 1.7;
            case "D+": return 1.3;
            case "D": return 1.0;
            case "D-": return 0.7;
            default: return 0.0;
        }
    }
    
    // ==================== Sample Data ====================
    
    /**
     * Initialize the system with sample data for demonstration
     */
    private void initializeSampleData() {
        // Add sample courses
        Course cs101 = new Course(generateCourseId(), "Introduction to Computer Science", "CS101",
                "Fundamental concepts of computer science and programming", "Computer Science", 3,
                "Dr. Smith", 30);
        Course cs201 = new Course(generateCourseId(), "Data Structures", "CS201",
                "Advanced data structures and algorithms", "Computer Science", 3,
                "Dr. Johnson", 25);
        Course math101 = new Course(generateCourseId(), "Calculus I", "MATH101",
                "Introduction to differential and integral calculus", "Mathematics", 4,
                "Prof. Williams", 40);
        Course eng101 = new Course(generateCourseId(), "English Composition", "ENG101",
                "Fundamentals of academic writing", "English", 3,
                "Dr. Brown", 35);
        
        addCourse(cs101);
        addCourse(cs201);
        addCourse(math101);
        addCourse(eng101);
        
        // Add sample students
        Student student1 = new Student(generateStudentId(), "John", "Doe",
                "john.doe@email.com", "555-0101", "123 Main St", "2000-05-15", "Computer Science");
        Student student2 = new Student(generateStudentId(), "Jane", "Smith",
                "jane.smith@email.com", "555-0102", "456 Oak Ave", "2001-03-22", "Mathematics");
        Student student3 = new Student(generateStudentId(), "Michael", "Johnson",
                "michael.j@email.com", "555-0103", "789 Pine Rd", "2000-11-08", "Computer Science");
        Student student4 = new Student(generateStudentId(), "Emily", "Williams",
                "emily.w@email.com", "555-0104", "321 Elm St", "2001-07-30", "English");
        Student student5 = new Student(generateStudentId(), "David", "Brown",
                "david.brown@email.com", "555-0105", "654 Maple Dr", "2000-09-12", "Mathematics");
        
        addStudent(student1);
        addStudent(student2);
        addStudent(student3);
        addStudent(student4);
        addStudent(student5);
        
        // Enroll students in courses
        enrollStudentInCourse(student1.getStudentId(), cs101.getCourseId());
        enrollStudentInCourse(student1.getStudentId(), math101.getCourseId());
        enrollStudentInCourse(student2.getStudentId(), math101.getCourseId());
        enrollStudentInCourse(student2.getStudentId(), eng101.getCourseId());
        enrollStudentInCourse(student3.getStudentId(), cs101.getCourseId());
        enrollStudentInCourse(student3.getStudentId(), cs201.getCourseId());
        enrollStudentInCourse(student4.getStudentId(), eng101.getCourseId());
        enrollStudentInCourse(student5.getStudentId(), math101.getCourseId());
        
        // Assign some grades
        Grade grade1 = getGrade(student1.getStudentId(), cs101.getCourseId());
        if (grade1 != null) {
            grade1.setAllScores(85, 88, 92);
            updateGrade(grade1);
        }
        
        Grade grade2 = getGrade(student1.getStudentId(), math101.getCourseId());
        if (grade2 != null) {
            grade2.setAllScores(78, 82, 85);
            updateGrade(grade2);
        }
        
        Grade grade3 = getGrade(student2.getStudentId(), math101.getCourseId());
        if (grade3 != null) {
            grade3.setAllScores(92, 95, 98);
            updateGrade(grade3);
        }
        
        Grade grade4 = getGrade(student3.getStudentId(), cs101.getCourseId());
        if (grade4 != null) {
            grade4.setAllScores(88, 90, 87);
            updateGrade(grade4);
        }
    }
}

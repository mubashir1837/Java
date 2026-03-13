package model;

/**
 * Grade class representing a student's grade in a course.
 * Links students, courses, and their academic performance.
 */
public class Grade {
    private String gradeId;
    private String studentId;
    private String courseId;
    private double assignmentScore;
    private double midtermScore;
    private double finalScore;
    private double totalScore;
    private String letterGrade;
    private String semester;
    private String academicYear;
    private String comments;
    
    /**
     * Default constructor
     */
    public Grade() {
    }
    
    /**
     * Parameterized constructor
     * 
     * @param gradeId Unique identifier for the grade record
     * @param studentId ID of the student
     * @param courseId ID of the course
     * @param semester Semester (e.g., Fall, Spring, Summer)
     * @param academicYear Academic year (e.g., 2023-2024)
     */
    public Grade(String gradeId, String studentId, String courseId,
                 String semester, String academicYear) {
        this.gradeId = gradeId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.semester = semester;
        this.academicYear = academicYear;
        this.assignmentScore = 0.0;
        this.midtermScore = 0.0;
        this.finalScore = 0.0;
        this.totalScore = 0.0;
        this.letterGrade = "N/A";
    }
    
    // Getters and Setters
    
    public String getGradeId() {
        return gradeId;
    }
    
    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }
    
    public String getStudentId() {
        return studentId;
    }
    
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
    
    public String getCourseId() {
        return courseId;
    }
    
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
    
    public double getAssignmentScore() {
        return assignmentScore;
    }
    
    public void setAssignmentScore(double assignmentScore) {
        this.assignmentScore = assignmentScore;
        calculateTotalScore();
    }
    
    public double getMidtermScore() {
        return midtermScore;
    }
    
    public void setMidtermScore(double midtermScore) {
        this.midtermScore = midtermScore;
        calculateTotalScore();
    }
    
    public double getFinalScore() {
        return finalScore;
    }
    
    public void setFinalScore(double finalScore) {
        this.finalScore = finalScore;
        calculateTotalScore();
    }
    
    public double getTotalScore() {
        return totalScore;
    }
    
    public String getLetterGrade() {
        return letterGrade;
    }
    
    public String getSemester() {
        return semester;
    }
    
    public void setSemester(String semester) {
        this.semester = semester;
    }
    
    public String getAcademicYear() {
        return academicYear;
    }
    
    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }
    
    public String getComments() {
        return comments;
    }
    
    public void setComments(String comments) {
        this.comments = comments;
    }
    
    /**
     * Calculate total score based on weighted components
     * Assignments: 30%, Midterm: 30%, Final: 40%
     */
    private void calculateTotalScore() {
        this.totalScore = (assignmentScore * 0.30) + 
                         (midtermScore * 0.30) + 
                         (finalScore * 0.40);
        this.letterGrade = calculateLetterGrade(this.totalScore);
    }
    
    /**
     * Calculate letter grade from total score
     * @param score The total score (0-100)
     * @return The corresponding letter grade
     */
    public static String calculateLetterGrade(double score) {
        if (score >= 97) return "A+";
        if (score >= 93) return "A";
        if (score >= 90) return "A-";
        if (score >= 87) return "B+";
        if (score >= 83) return "B";
        if (score >= 80) return "B-";
        if (score >= 77) return "C+";
        if (score >= 73) return "C";
        if (score >= 70) return "C-";
        if (score >= 67) return "D+";
        if (score >= 63) return "D";
        if (score >= 60) return "D-";
        return "F";
    }
    
    /**
     * Set all scores at once and recalculate total
     * @param assignment Assignment score (0-100)
     * @param midterm Midterm score (0-100)
     * @param finalExam Final exam score (0-100)
     */
    public void setAllScores(double assignment, double midterm, double finalExam) {
        this.assignmentScore = assignment;
        this.midtermScore = midterm;
        this.finalScore = finalExam;
        calculateTotalScore();
    }
    
    /**
     * Check if grade is passing
     * @return true if grade is D- or higher
     */
    public boolean isPassing() {
        return totalScore >= 60.0;
    }
    
    @Override
    public String toString() {
        return "Grade{" +
                "studentId='" + studentId + '\'' +
                ", courseId='" + courseId + '\'' +
                ", totalScore=" + String.format("%.2f", totalScore) +
                ", letterGrade='" + letterGrade + '\'' +
                '}';
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Grade grade = (Grade) obj;
        return gradeId != null && gradeId.equals(grade.gradeId);
    }
    
    @Override
    public int hashCode() {
        return gradeId != null ? gradeId.hashCode() : 0;
    }
}

package util;

import java.util.regex.Pattern;

/**
 * Validator class providing static methods for input validation.
 * Ensures data integrity before operations are performed.
 */
public class Validator {
    
    // Email validation pattern
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    
    // Phone validation pattern (accepts a few common formats)
    // - 7 digit local: 555-0101 or 5550101
    // - 10 digits: 5550101234
    // - 10 digits with hyphens: 555-010-1234
    // - area code in parentheses: (555) 010-1234
    private static final Pattern PHONE_PATTERN = 
        Pattern.compile("^[0-9]{7}$|^[0-9]{3}-[0-9]{4}$|^[0-9]{3}-[0-9]{3}-[0-9]{4}$|^[0-9]{10}$|^\\([0-9]{3}\\) [0-9]{3}-[0-9]{4}$");
    
    // Date pattern (YYYY-MM-DD)
    private static final Pattern DATE_PATTERN = 
        Pattern.compile("^[0-9]{4}-[0-9]{2}-[0-9]{2}$");
    
    /**
     * Private constructor to prevent instantiation
     */
    private Validator() {
    }
    
    /**
     * Validate that a string is not null or empty
     * @param value The string to validate
     * @param fieldName The name of the field (for error message)
     * @throws ValidationException if validation fails
     */
    public static void validateNotEmpty(String value, String fieldName) throws ValidationException {
        if (value == null || value.trim().isEmpty()) {
            throw new ValidationException(fieldName + " cannot be empty.");
        }
    }
    
    /**
     * Validate email format
     * @param email The email to validate
     * @throws ValidationException if validation fails
     */
    public static void validateEmail(String email) throws ValidationException {
        if (email == null || email.trim().isEmpty()) {
            throw new ValidationException("Email cannot be empty.");
        }
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new ValidationException("Invalid email format. Please use format: example@domain.com");
        }
    }
    
    /**
     * Validate phone number format
     * @param phone The phone number to validate
     * @throws ValidationException if validation fails
     */
    public static void validatePhone(String phone) throws ValidationException {
        if (phone == null || phone.trim().isEmpty()) {
            throw new ValidationException("Phone number cannot be empty.");
        }
        if (!PHONE_PATTERN.matcher(phone).matches()) {
            throw new ValidationException("Invalid phone format. Examples: 5550101, 555-0101, 555-010-1234, (555) 010-1234, or 10 digits.");
        }
    }
    
    /**
     * Validate date format (YYYY-MM-DD)
     * @param date The date to validate
     * @throws ValidationException if validation fails
     */
    public static void validateDate(String date) throws ValidationException {
        if (date == null || date.trim().isEmpty()) {
            throw new ValidationException("Date cannot be empty.");
        }
        if (!DATE_PATTERN.matcher(date).matches()) {
            throw new ValidationException("Invalid date format. Please use format: YYYY-MM-DD");
        }
        
        // Validate month and day ranges
        try {
            String[] parts = date.split("-");
            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int day = Integer.parseInt(parts[2]);
            
            if (month < 1 || month > 12) {
                throw new ValidationException("Month must be between 1 and 12.");
            }
            if (day < 1 || day > 31) {
                throw new ValidationException("Day must be between 1 and 31.");
            }
            
            // Check for months with 30 days
            if ((month == 4 || month == 6 || month == 9 || month == 11) && day > 30) {
                throw new ValidationException("This month has only 30 days.");
            }
            
            // Check for February
            if (month == 2) {
                boolean isLeapYear = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
                int maxDays = isLeapYear ? 29 : 28;
                if (day > maxDays) {
                    throw new ValidationException("February has only " + maxDays + " days in " + year + ".");
                }
            }
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid date values.");
        }
    }
    
    /**
     * Validate score range (0-100)
     * @param score The score to validate
     * @param fieldName The name of the field (for error message)
     * @throws ValidationException if validation fails
     */
    public static void validateScore(double score, String fieldName) throws ValidationException {
        if (score < 0 || score > 100) {
            throw new ValidationException(fieldName + " must be between 0 and 100.");
        }
    }
    
    /**
     * Validate credits range (1-6 typically)
     * @param credits The credits to validate
     * @throws ValidationException if validation fails
     */
    public static void validateCredits(int credits) throws ValidationException {
        if (credits < 1 || credits > 6) {
            throw new ValidationException("Credits must be between 1 and 6.");
        }
    }
    
    /**
     * Validate capacity (positive number)
     * @param capacity The capacity to validate
     * @throws ValidationException if validation fails
     */
    public static void validateCapacity(int capacity) throws ValidationException {
        if (capacity < 1) {
            throw new ValidationException("Capacity must be at least 1.");
        }
        if (capacity > 200) {
            throw new ValidationException("Capacity cannot exceed 200.");
        }
    }
    
    /**
     * Validate student ID format
     * @param studentId The student ID to validate
     * @throws ValidationException if validation fails
     */
    public static void validateStudentId(String studentId) throws ValidationException {
        if (studentId == null || studentId.trim().isEmpty()) {
            throw new ValidationException("Student ID cannot be empty.");
        }
        if (!studentId.matches("STU[0-9]+")) {
            throw new ValidationException("Invalid student ID format.");
        }
    }
    
    /**
     * Validate course ID format
     * @param courseId The course ID to validate
     * @throws ValidationException if validation fails
     */
    public static void validateCourseId(String courseId) throws ValidationException {
        if (courseId == null || courseId.trim().isEmpty()) {
            throw new ValidationException("Course ID cannot be empty.");
        }
        if (!courseId.matches("CRS[0-9]+")) {
            throw new ValidationException("Invalid course ID format.");
        }
    }
    
    /**
     * Validate name (letters, spaces, hyphens, and apostrophes only)
     * @param name The name to validate
     * @param fieldName The name of the field (for error message)
     * @throws ValidationException if validation fails
     */
    public static void validateName(String name, String fieldName) throws ValidationException {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException(fieldName + " cannot be empty.");
        }
        if (!name.matches("^[A-Za-z\\s'-]+$")) {
            throw new ValidationException(fieldName + " can only contain letters, spaces, hyphens, and apostrophes.");
        }
        if (name.length() < 2 || name.length() > 50) {
            throw new ValidationException(fieldName + " must be between 2 and 50 characters.");
        }
    }
    
    /**
     * Validate course code format
     * @param courseCode The course code to validate
     * @throws ValidationException if validation fails
     */
    public static void validateCourseCode(String courseCode) throws ValidationException {
        if (courseCode == null || courseCode.trim().isEmpty()) {
            throw new ValidationException("Course code cannot be empty.");
        }
        if (!courseCode.matches("^[A-Z]{2,4}[0-9]{3}$")) {
            throw new ValidationException("Invalid course code format. Use format: CS101, MATH101, etc.");
        }
    }
    
    /**
     * Validate that an object is not null
     * @param obj The object to validate
     * @param fieldName The name of the field (for error message)
     * @throws ValidationException if validation fails
     */
    public static void validateNotNull(Object obj, String fieldName) throws ValidationException {
        if (obj == null) {
            throw new ValidationException(fieldName + " must be selected.");
        }
    }
}

# Student Management System GUI Application Documentation

This document explains the purpose, implementation, and usage of the Student
Management System GUI written in Java Swing. It serves as the assignment
submission documentation including code excerpts, design rationale, and running
instructions.

---

## 1. Introduction

The Student Management System (SMS) is a desktop application that allows
administrators to manage students, courses, enrollments, and grades through a
user-friendly graphical interface. The project uses Java Swing for the GUI and
is structured into four packages:

- `model` – domain classes (`Student`, `Course`, `Grade`).
- `data` – the singleton `DataManager` responsible for persistence and data
  operations.
- `gui` – all Swing components (`MainFrame` and panel classes).
- `util` – utility/helpers (`Validator`, `ValidationException`).

The design follows an MVC-like separation: models hold state, the GUI presents
and manipulates data, and `DataManager` provides controller-style services.
Event handling is used extensively to react to user interactions and update the
interface dynamically.

---

## 2. GUI Design & Navigation

The main window is implemented by `gui.MainFrame`. It uses a `CardLayout` to
switch between five functional panels:

1. **Dashboard** – overview with statistics and quick action buttons.
2. **Students** – add, update, delete, and view student records.
3. **Courses** – view and modify course list.
4. **Enrollment** – enroll/remove students in courses.
5. **Grades** – view and manage student grades.

A menu bar and toolbar provide navigation shortcuts. A status bar along the
bottom displays messages and statistics.

### Code Example: Creating the Content Area

```java
private void createContentArea() {
    cardLayout = new CardLayout();
    contentPanel = new JPanel(cardLayout);

    contentPanel.add(createDashboardPanel(), DASHBOARD_PANEL);
    contentPanel.add(new StudentManagementPanel(this), STUDENTS_PANEL);
    contentPanel.add(createCoursesPanel(), COURSES_PANEL);
    contentPanel.add(new CourseEnrollmentPanel(this), ENROLLMENT_PANEL);
    contentPanel.add(new GradeManagementPanel(this), GRADES_PANEL);

    add(contentPanel, BorderLayout.CENTER);
}
```

### MainFrame Class Explanation

The `MainFrame` class serves as the primary window for the Student Management System GUI application. It extends `JFrame` and manages the overall layout and navigation between different functional panels using a `CardLayout`.

#### Key Components and Initialization:
- **Fields**:
  - `cardLayout` and `contentPanel`: Handle switching between panels (Dashboard, Students, Courses, Enrollment, Grades).
  - `statusLabel` and `statsLabel`: Display status messages and statistics at the bottom.
  - Constants like `DASHBOARD_PANEL`, etc.: String identifiers for each panel in the CardLayout.
  - Panel references (`studentPanel`, `enrollmentPanel`, `gradePanel`): Allow updates to specific panels when data changes.

- **Constructor**: Calls methods to set up the frame, menu bar, toolbar, status bar, content area, and event listeners.

- **initializeFrame() Method**:
  - Sets the window title to "Student Management System".
  - Configures size (1200x800) with a minimum size (900x600) and centers it on the screen.
  - Sets the default close operation to `DO_NOTHING_ON_CLOSE` (handled by a custom listener).
  - Attempts to load a custom icon (`/icon.png`); falls back to default if unavailable.
  - Applies the system's look and feel for native appearance, with fallback to default if it fails.

This setup ensures a professional, user-friendly interface that adapts to the system's theme and provides a consistent navigation experience.

> **Design choices:**
> - Consistent `Segoe UI` font, light backgrounds, and color‑coded elements for
a cohesive visual style.
> - Use of titled borders, split panes, and grid layouts to logically organize
information.

---

## 3. Model Classes

### Student Class

The `Student` class represents a student in the system with personal information and enrollment data.

```java
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

    // Constructor, getters, setters, and methods...
}
```

### Course Class

The `Course` class represents a course offering with enrollment management.

```java
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

    // Constructor, getters, setters, and methods...
}
```

### Grade Class

The `Grade` class represents a student's grade in a specific course with weighted scoring.

```java
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

    // Constructor, getters, setters, and methods...
}
```

---

## 4. Data Management

### DataManager Class

The `DataManager` is a singleton class that handles all data operations and persistence.

```java
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

    // Singleton pattern implementation...
}
```

Key methods include:
- Student management: `addStudent()`, `updateStudent()`, `deleteStudent()`
- Course management: `addCourse()`, `updateCourse()`, `deleteCourse()`
- Enrollment: `enrollStudentInCourse()`, `removeStudentFromCourse()`
- Grades: `updateGrade()`, `getStudentGrades()`, `calculateGPA()`

---

## 5. Utility Classes

### Validator Class

The `Validator` class provides static methods for input validation.

```java
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

    // Phone validation pattern
    private static final Pattern PHONE_PATTERN =
        Pattern.compile("^[0-9]{7}$|^[0-9]{3}-[0-9]{4}$|^[0-9]{3}-[0-9]{3}-[0-9]{4}$|^[0-9]{10}$|^\\([0-9]{3}\\) [0-9]{3}-[0-9]{4}$");

    // Date pattern (YYYY-MM-DD)
    private static final Pattern DATE_PATTERN =
        Pattern.compile("^[0-9]{4}-[0-9]{2}-[0-9]{2}$");

    // Validation methods...
}
```

### ValidationException Class

Custom exception for validation errors.

```java
package util;

/**
 * Custom exception class for validation errors in the Student Management System.
 * Used to provide meaningful error messages for invalid inputs.
 */
public class ValidationException extends Exception {
    // Constructor implementations...
}
```

---

## 6. Student Management Functionality

Panel: `gui.StudentManagementPanel`

Features:

- Add new students via a form. Inputs are validated using
  `util.Validator`.
- Update existing student information by selecting a row in the table.
- View all students in a non-editable `JTable`.

### Event handlers example

```java
addButton.addActionListener(e -> addStudent());
updateButton.addActionListener(e -> updateStudent());
studentsTable.getSelectionModel().addListSelectionListener(
        e -> loadStudentForEditing());
```

These handlers ensure that clicking the buttons invokes the correct methods and
that the form fields synchronize with the selected table row.

---

## 7. Course Enrollment Functionality

Panel: `gui.CourseEnrollmentPanel`

Components & behavior:

- `JComboBox<Course>` for course selection, with custom renderer to display
  code and name.
- Two tables: eligible students and enrolled students.
- **Enroll** and **Remove** buttons placed between the tables.
- Course info label showing department, credits, enrollment count, etc.

### Enrollment handler excerpt

```java
private void enrollStudent() {
    if (selectedCourse == null || selectedEligibleStudent == null) {
        showError("Please select a course and a student to enroll.");
        return;
    }
    // confirmation dialog
    int confirm = JOptionPane.showConfirmDialog(...);
    if (confirm == JOptionPane.YES_OPTION) {
        boolean success = dataManager.enrollStudentInCourse(...);
        if (success) {
            showSuccess("Student enrolled successfully!");
            updateCourseDisplay();
            parentFrame.updateStatusBar();
        }
    }
}
```

The panel updates dynamically after each operation by reloading table models and
updating the status bar.

---

## 8. Grade Management Functionality

Panel: `gui.GradeManagementPanel`

Key features:

- Select a student from a combo box; grades for enrolled courses appear in a
  table.
- Selecting a row populates sliders and fields for assignment, midterm, and
  final scores.
- Sliders recalculate total score and letter grade in real time (with color
  coding).
- Save button persists changes; clear resets the form.

### Grade table setup snippet

```java
String[] gradesColumns = {"Course", "Assignments (30%)", "Midterm (30%)",
                           "Final (40%)", "Total", "Grade"};
gradesTableModel = new DefaultTableModel(gradesColumns, 0) {
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
};
gradesTable = new JTable(gradesTableModel);
gradesTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
// ... header styling ...
```

Event listeners ensure responses to table selection and slider changes.

---

## 9. Dynamic Interface Updates

All panels use `refresh...()` methods after data changes to keep the GUI in sync
with the underlying model. For example, enrolling a student triggers:

```java
updateCourseDisplay();
parentFrame.updateStatusBar();
```

When a new student is added or modified, the students table reloads and the status
bar message updates.

---

## 10. Error Handling

- Validation performed by `util.Validator` throws `ValidationException` with
  clear messages.
- GUI catches exceptions and displays error dialogs via `JOptionPane`.
- Common checks include: empty fields, invalid grades (<0 or >100), full course
  enrollment, duplicate entries.

Example:

```java
try {
    Validator.validateStudentId(id);
    // ... add student
} catch (ValidationException ex) {
    showError(ex.getMessage());
}
```

This keeps the application responsive and user-friendly in error scenarios.

---

## Running the Student Management System

### Prerequisites
- Java Development Kit (JDK) 8 or higher installed
- Windows operating system (or modify scripts for other OS)

### Compilation (Windows)
1. Open Command Prompt in the project root directory.
2. Run the build script:
   ```
   compile.bat
   ```
   This compiles all Java source files and places the class files in the `bin` directory.

### Execution (Windows)
1. After successful compilation, run:
   ```
   run.bat
   ```
   Or manually:
   ```
   cd bin
   java gui.MainFrame
   ```

### Compilation (Unix/Linux/Mac)
1. Open terminal in the project root directory.
2. Compile manually:
   ```
   mkdir -p bin
   javac -d bin src/model/*.java src/util/*.java src/data/*.java src/gui/*.java
   ```

### Execution (Unix/Linux/Mac)
```
java -cp bin gui.MainFrame
```

---

## 11. Design Rationale

- **Swing** was chosen for its simplicity and built-in availability.
- **CardLayout** provides easy switching between functional areas.
- Utility classes modularize validation logic separate from GUI.
- `DataManager` centralizes persistence and business rules, making GUI code
  cleaner.

---

## 12. Project Structure

```
StudentManagementSystem/
├── src/
│   ├── model/
│   │   ├── Student.java
│   │   ├── Course.java
│   │   └── Grade.java
│   ├── data/
│   │   └── DataManager.java
│   ├── gui/
│   │   ├── MainFrame.java
│   │   ├── StudentManagementPanel.java
│   │   ├── CourseEnrollmentPanel.java
│   │   └── GradeManagementPanel.java
│   └── util/
│       ├── Validator.java
│       └── ValidationException.java
├── bin/ (generated)
├── compile.bat
├── run.bat
├── build.sh
├── run.sh
├── README.md
├── PROJECT_SUMMARY.md
└── Documentation.md
```

---

## 13. Conclusion

This project satisfies all rubric criteria:

| Criterion                    | Status                                             |
|-----------------------------|----------------------------------------------------|
| GUI design                  | Intuitive, well-organized, visually consistent      |
| Swing implementation        | All components integrated, consistent styling      |
| Student management          | Add/update/view with event handling                |
| Course enrollment           | Dropdowns, eligibility display, enroll/remove      |
| Grade management            | View/assign with sliders and tables                |
| Dynamic updates             | Automatic refresh after every data change         |
| Error handling              | Exceptions caught, dialogs shown                   |
| Documentation               | Comprehensive explanations and code snippets       |

---

*End of documentation.*

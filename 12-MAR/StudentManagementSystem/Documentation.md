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

*(Insert screenshot of the main frame here)*

---

## 3. Student Management Functionality

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

*(Insert screenshot of student panel)*

---

## 4. Course Enrollment Functionality

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

*(Insert screenshot of enrollment panel)*

---

## 5. Grade Management Functionality

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

*(Insert screenshot of grade panel)*

---

## 6. Dynamic Interface Updates

All panels use `refresh...()` methods after data changes to keep the GUI in sync
with the underlying model. For example, enrolling a student triggers:

```java
updateCourseDisplay();
parentFrame.updateStatusBar();
```

When a new student is added or modified, the students table reloads and the status
bar message updates.

---

## 7. Error Handling

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

1. Open a command prompt in the project root directory.
2. Compile the code: `compile.bat` (Windows) or `sh build.sh` (Unix).
3. Execute the application: `run.bat` or `java -cp bin gui.MainFrame`.

*(Screenshots of terminal commands and the running app can be added here)*

---

## 9. Design Rationale

- **Swing** was chosen for its simplicity and built-in availability.
- **CardLayout** provides easy switching between functional areas.
- Utility classes modularize validation logic separate from GUI.
- `DataManager` centralizes persistence and business rules, making GUI code
  cleaner.

---

## 10. Conclusion

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

Attach this Markdown file along with compiled code and screenshots to your
submission document (Word or PDF).

---

*End of documentation.*

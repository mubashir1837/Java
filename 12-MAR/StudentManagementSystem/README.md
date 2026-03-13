# Student Management System

A comprehensive GUI application for managing student records, course enrollments, and grades. Built using Java Swing with a focus on user-friendly design and robust functionality.

## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [System Requirements](#system-requirements)
- [Installation and Setup](#installation-and-setup)
- [Running the Application](#running-the-application)
- [User Guide](#user-guide)
- [Architecture](#architecture)
- [Error Handling](#error-handling)
- [Future Enhancements](#future-enhancements)

## Overview

The Student Management System is a desktop application designed for educational administrators to efficiently manage student information, course enrollments, and academic grades. The application features an intuitive graphical user interface built with Java Swing, providing seamless navigation and real-time data updates.

### Key Design Principles
- **User-Friendly Interface**: Clean, intuitive design with logical organization
- **Data Integrity**: Comprehensive validation and error handling
- **Real-Time Updates**: Dynamic interface that reflects changes immediately
- **Modular Architecture**: Well-structured code for maintainability and extensibility

## Features

### 1. Student Management
- **Add New Students**: Create student records with comprehensive personal information
- **Update Student Information**: Modify existing student details
- **Delete Students**: Remove students with confirmation and cascade deletion of related records
- **Search Functionality**: Find students by name with partial matching
- **View All Students**: Display complete student list with enrollment counts

### 2. Course Management
- **View Courses**: Display all available courses with enrollment statistics
- **Course Information**: View course details including code, department, credits, instructor, and capacity

### 3. Course Enrollment
- **Enroll Students**: Add students to courses with capacity checking
- **Remove Enrollment**: Drop students from courses
- **Eligibility Display**: View students eligible for enrollment in each course
- **Enrollment Tracking**: Real-time display of enrollment counts and capacity

### 4. Grade Management
- **Assign Grades**: Set scores for assignments, midterms, and final exams
- **Automatic Calculation**: Weighted total score and letter grade calculation
- **Grade Visualization**: Color-coded grade display for quick assessment
- **GPA Tracking**: Automatic GPA calculation per student
- **Comments**: Add remarks to grade records

### 5. Dashboard
- **System Overview**: Quick statistics on students, courses, and enrollments
- **Quick Actions**: Direct access to common operations
- **Status Bar**: Real-time system status and statistics

## System Requirements

### Minimum Requirements
- **Java**: JDK 8 or higher
- **Operating System**: Windows 7+, macOS 10.10+, or Linux
- **RAM**: 512 MB minimum
- **Storage**: 50 MB free space

### Recommended Requirements
- **Java**: JDK 11 or higher
- **RAM**: 1 GB or more
- **Display**: 1280x720 resolution or higher

## Installation and Setup

### Step 1: Extract the Archive
Extract the `StudentManagementSystem.zip` file to your desired location.

```bash
unzip StudentManagementSystem.zip -d /path/to/installation
cd /path/to/installation/StudentManagementSystem
```

### Step 2: Verify Java Installation
Ensure Java is installed and configured:

```bash
java -version
javac -version
```

If Java is not installed, download and install it from [Oracle's website](https://www.oracle.com/java/technologies/downloads/) or use OpenJDK.

### Step 3: Compile the Application
Navigate to the source directory and compile:

```bash
cd src
javac -d ../bin model/*.java data/*.java util/*.java gui/*.java
```

Or use the provided build script:

```bash
chmod +x build.sh
./build.sh
```

## Running the Application

### Method 1: Using the Run Script
```bash
./run.sh
```

### Method 2: Manual Execution
```bash
cd bin
java gui.MainFrame
```

### Method 3: Using an IDE
1. Import the project into your preferred IDE (Eclipse, IntelliJ IDEA, NetBeans)
2. Set the `src` folder as the source directory
3. Run the `gui.MainFrame` class

## User Guide

### Main Window Layout

```
┌─────────────────────────────────────────────────────────────┐
│  Menu Bar: File | View | Help                               │
├─────────────────────────────────────────────────────────────┤
│  Toolbar: [Dashboard] [Students] [Courses] [Enroll] [Grades]│
├─────────────────────────────────────────────────────────────┤
│                                                             │
│                    Content Area                             │
│              (Changes based on selection)                   │
│                                                             │
├─────────────────────────────────────────────────────────────┤
│  Status Bar: Ready | Students: X | Courses: Y | Enroll: Z  │
└─────────────────────────────────────────────────────────────┘
```

### Navigation

#### Dashboard
- View system statistics at a glance
- Access quick action buttons for common tasks
- Monitor total students, courses, and enrollments

#### Student Management
1. **Adding a Student**:
   - Click "Add Student" button
   - Fill in required fields (marked with *)
   - Click "Add Student" to save

2. **Updating a Student**:
   - Select a student from the table
   - Modify the information in the form
   - Click "Update Student" to save changes

3. **Deleting a Student**:
   - Select a student from the table
   - Click "Delete Student"
   - Confirm the deletion

4. **Searching**:
   - Enter a name in the search field
   - Click "Search" to filter results
   - Click "Refresh" to show all students

#### Course Enrollment
1. **Enrolling a Student**:
   - Select a course from the dropdown
   - Choose an eligible student from the left table
   - Click "Enroll Student →"

2. **Removing Enrollment**:
   - Select a course from the dropdown
   - Choose an enrolled student from the right table
   - Click "← Remove Student"

#### Grade Management
1. **Assigning Grades**:
   - Select a student from the dropdown
   - Click on a course in the grades table
   - Adjust sliders for assignment, midterm, and final scores
   - Add optional comments
   - Click "Save Grade"

2. **Understanding Grade Calculation**:
   - Assignments: 30% of total
   - Midterm: 30% of total
   - Final Exam: 40% of total
   - Letter grades automatically calculated

### Data Validation

The system enforces the following validation rules:

| Field | Validation Rules |
|-------|-----------------|
| First/Last Name | 2-50 characters, letters only |
| Email | Valid email format (user@domain.com) |
| Phone | Formats: 555-0101, (555) 010-1234, or 5550101234 |
| Date of Birth | Format: YYYY-MM-DD, valid date |
| Major | Required, any text |
| Scores | 0-100 range |

### Error Messages

The application provides clear error messages for:
- Empty required fields
- Invalid email formats
- Invalid phone numbers
- Invalid dates
- Score values outside 0-100 range
- Attempting to enroll in full courses
- Duplicate enrollments

## Architecture

### Package Structure

```
src/
├── model/           # Data model classes
│   ├── Student.java
│   ├── Course.java
│   └── Grade.java
├── data/            # Data management
│   └── DataManager.java
├── util/            # Utility classes
│   ├── Validator.java
│   └── ValidationException.java
└── gui/             # GUI components
    ├── MainFrame.java
    ├── StudentManagementPanel.java
    ├── CourseEnrollmentPanel.java
    └── GradeManagementPanel.java
```

### Class Diagram

```
┌─────────────────┐
│   MainFrame     │
│  (Main Window)  │
└────────┬────────┘
         │
    ┌────┴────┬────────────┬─────────────────┐
    │         │            │                 │
    ▼         ▼            ▼                 ▼
┌────────┐ ┌─────────────┐ ┌─────────────────┐ ┌─────────────────┐
│Student │ │   Course    │ │    DataManager  │ │    Validator    │
│Management│ │Enrollment  │ │   (Singleton)   │ │                 │
│ Panel  │ │   Panel     │ │                 │ │                 │
└────────┘ └─────────────┘ └─────────────────┘ └─────────────────┘
                                                  │
    ┌─────────────────────────────────────────────┘
    │
    ▼
┌─────────────────────────────────────────────────────────────┐
│                        DataManager                          │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────────────┐  │
│  │  Students   │  │   Courses   │  │       Grades        │  │
│  │  (Map)      │  │   (Map)     │  │       (Map)         │  │
│  └─────────────┘  └─────────────┘  └─────────────────────┘  │
└─────────────────────────────────────────────────────────────┘
```

### Design Patterns Used

1. **Singleton Pattern**: `DataManager` ensures a single instance of data management
2. **Observer Pattern**: Event listeners for UI interactions
3. **MVC Pattern**: Separation of Model (data), View (GUI), and Controller (event handlers)
4. **Factory Pattern**: ID generation for students, courses, and grades

## Error Handling

### Validation Errors
- Input validation before data operations
- Descriptive error messages for users
- Form fields highlighted with error context

### Runtime Errors
- Graceful exception handling
- User-friendly error dialogs
- Application remains responsive

### Data Integrity
- Confirmation dialogs for destructive operations
- Cascade deletion handling
- Referential integrity maintenance

## Future Enhancements

Potential improvements for future versions:

1. **Database Integration**: Replace in-memory storage with persistent database
2. **User Authentication**: Add login system with role-based access
3. **Report Generation**: Export grades and reports to PDF/Excel
4. **Advanced Search**: Filter by multiple criteria
5. **Batch Operations**: Import/export data from CSV files
6. **Notification System**: Alerts for important events
7. **Audit Trail**: Log all changes for accountability

## Troubleshooting

### Common Issues

#### Application Won't Start
- Verify Java installation: `java -version`
- Check that all class files are compiled
- Ensure you're in the correct directory

#### Compilation Errors
- Verify JDK is installed (not just JRE)
- Check for syntax errors in source files
- Ensure all dependencies are available

#### Data Not Saving
- Check available disk space
- Verify write permissions
- Review error messages in console

### Support

For technical support or bug reports, please contact the development team.

## License

This project is developed for educational purposes.

## Acknowledgments

- Java Swing for the GUI framework
- Educational institutions for requirements and feedback

---

**Version**: 1.0  
**Last Updated**: 2024  
**Developer**: Student Management System Team

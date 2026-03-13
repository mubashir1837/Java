#!/bin/bash

# Student Management System - Build Script
# This script compiles all Java source files and prepares the application for execution

echo "========================================"
echo "Student Management System - Build Tool"
echo "========================================"
echo ""

# Check if Java is installed
if ! command -v javac &> /dev/null; then
    echo "Error: Java compiler (javac) not found!"
    echo "Please install JDK 8 or higher and try again."
    exit 1
fi

# Display Java version
echo "Java version:"
javac -version
echo ""

# Create bin directory if it doesn't exist
if [ ! -d "bin" ]; then
    echo "Creating bin directory..."
    mkdir bin
fi

# Clean previous builds
echo "Cleaning previous builds..."
rm -rf bin/*

# Compile source files
echo "Compiling source files..."
cd src

# Compile all Java files
javac -d ../bin \
    model/Student.java \
    model/Course.java \
    model/Grade.java \
    util/ValidationException.java \
    util/Validator.java \
    data/DataManager.java \
    gui/StudentManagementPanel.java \
    gui/CourseEnrollmentPanel.java \
    gui/GradeManagementPanel.java \
    gui/MainFrame.java

# Check if compilation was successful
if [ $? -eq 0 ]; then
    echo ""
    echo "========================================"
    echo "Build completed successfully!"
    echo "========================================"
    echo ""
    echo "Compiled files are in the 'bin' directory"
    echo ""
    echo "To run the application:"
    echo "  cd bin"
    echo "  java gui.MainFrame"
    echo ""
    echo "Or use the run.sh script:"
    echo "  ./run.sh"
    exit 0
else
    echo ""
    echo "========================================"
    echo "Build failed! Please check the errors above."
    echo "========================================"
    exit 1
fi

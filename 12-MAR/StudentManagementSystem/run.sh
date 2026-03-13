#!/bin/bash

# Student Management System - Run Script
# This script runs the Student Management System application

echo "========================================"
echo "Student Management System"
echo "========================================"
echo ""

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "Error: Java not found!"
    echo "Please install Java 8 or higher and try again."
    exit 1
fi

# Check if compiled classes exist
if [ ! -d "bin" ] || [ ! -f "bin/gui/MainFrame.class" ]; then
    echo "Compiled classes not found!"
    echo "Please build the application first using:"
    echo "  ./build.sh"
    exit 1
fi

# Run the application
echo "Starting Student Management System..."
echo ""
cd bin
java gui.MainFrame

# Check exit status
if [ $? -eq 0 ]; then
    echo ""
    echo "Application closed successfully."
else
    echo ""
    echo "Application exited with errors."
fi

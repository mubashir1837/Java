@echo off
REM Student Management System - Windows Build Script
REM This script compiles all Java source files for Windows users

echo ========================================
echo Student Management System - Build Tool
echo ========================================
echo.

REM Check if Java is installed
javac -version >nul 2>&1
if errorlevel 1 (
    echo Error: Java compiler not found!
    echo Please install JDK 8 or higher and try again.
    pause
    exit /b 1
)

echo Java version:
javac -version
echo.

REM Create bin directory if it doesn't exist
if not exist "bin" (
    echo Creating bin directory...
    mkdir bin
)

REM Clean previous builds
echo Cleaning previous builds...
del /Q bin\* >nul 2>&1

REM Compile source files
echo Compiling source files...
cd src

REM Compile all Java files
javac -d ..\bin ^
    model\Student.java ^
    model\Course.java ^
    model\Grade.java ^
    util\ValidationException.java ^
    util\Validator.java ^
    data\DataManager.java ^
    gui\StudentManagementPanel.java ^
    gui\CourseEnrollmentPanel.java ^
    gui\GradeManagementPanel.java ^
    gui\MainFrame.java

REM Check if compilation was successful
if errorlevel 1 (
    echo.
    echo ========================================
    echo Build failed! Please check the errors above.
    echo ========================================
    pause
    exit /b 1
) else (
    echo Copying resources...
    cd ..
    if exist "sms.jpg" (
        copy sms.jpg bin\ >nul
        echo Icon file copied to bin\
    )
    echo.
    echo ========================================
    echo Build completed successfully!
    echo ========================================
    echo.
    echo Compiled files are in the 'bin' directory
    echo.
    echo To run the application:
    echo   cd bin
    echo   java gui.MainFrame
    echo.
    pause
    exit /b 0
)

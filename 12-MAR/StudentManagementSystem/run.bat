@echo off
REM Student Management System - Windows Run Script
REM This script runs the Student Management System application on Windows

echo ========================================
echo Student Management System
echo ========================================
echo.

REM Check if Java is installed
java -version >nul 2>&1
if errorlevel 1 (
    echo Error: Java not found!
    echo Please install Java 8 or higher and try again.
    pause
    exit /b 1
)

REM Check if compiled classes exist
if not exist "bin\gui\MainFrame.class" (
    echo Compiled classes not found!
    echo Please build the application first using:
    echo   compile.bat
    pause
    exit /b 1
)

REM Run the application
echo Starting Student Management System...
echo.
cd bin
java gui.MainFrame

REM Check exit status
if errorlevel 1 (
    echo.
    echo Application exited with errors.
    pause
) else (
    echo.
    echo Application closed successfully.
)

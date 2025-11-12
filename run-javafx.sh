#!/bin/bash

# Employee Management System - JavaFX GUI Launcher
# This script runs the JavaFX GUI version of the application

echo "=========================================="
echo "Employee Management System - JavaFX Mode"
echo "=========================================="
echo ""

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "ERROR: Maven is not installed or not in PATH"
    echo "Please install Maven first"
    exit 1
fi

# Check if database.properties exists
if [ ! -f "src/main/resources/database.properties" ]; then
    echo "ERROR: database.properties not found"
    echo "Please configure your database connection first"
    exit 1
fi

# Compile and run with JavaFX
echo "Compiling and launching JavaFX GUI..."
echo ""
mvn clean compile javafx:run

exit_code=$?

if [ $exit_code -ne 0 ] && [ $exit_code -ne 130 ]; then
    echo ""
    echo "ERROR: Failed to launch JavaFX GUI"
    echo "Exit code: $exit_code"
    exit $exit_code
fi

echo ""
echo "Application closed."

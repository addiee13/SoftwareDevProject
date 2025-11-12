#!/bin/bash

# Employee Management System - Console Launcher
# This script runs the console version of the application

echo "=========================================="
echo "Employee Management System - Console Mode"
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

# Compile and run console version
echo "Compiling and launching console UI..."
echo ""
mvn clean compile exec:java

exit_code=$?

if [ $exit_code -ne 0 ]; then
    echo ""
    echo "ERROR: Failed to launch console UI"
    echo "Exit code: $exit_code"
    exit $exit_code
fi

echo ""
echo "Application closed."

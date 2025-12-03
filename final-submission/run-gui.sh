#!/bin/bash
# Run Employee Management System in GUI Mode

echo "Starting Employee Management System - JavaFX GUI Mode"
echo "====================================================="
echo ""

mvn exec:java -Dexec.mainClass="com.companyz.ems.Main" -Dexec.args="--gui"

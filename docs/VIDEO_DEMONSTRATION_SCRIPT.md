# Video Demonstration Script
# Employee Management System

**Duration**: 7-15 minutes  
**Format**: MPEG-4, MP4, or MOV  
**Resolution**: 1280x720 or higher recommended

---

## Pre-Recording Checklist

- [ ] MySQL database is running
- [ ] Database contains sample data (15-20 employees)
- [ ] Application is compiled and ready to run
- [ ] Screen recording software is set up
- [ ] Audio recording is tested (for narration)
- [ ] Desktop is clean and professional
- [ ] Close unnecessary applications

---

## Video Structure

### Introduction (30 seconds)

**Script**:
> "Hello, this is a demonstration of the Employee Management System developed for Company Z. This system provides a comprehensive solution for managing employee data, processing salary updates, and generating payroll reports. The system is built using Java, MySQL, and follows a layered architecture pattern. Let's begin by starting the application."

**Actions**:
- Show desktop
- Open terminal/command prompt
- Navigate to project directory

---

### Section 1: System Startup (1 minute)

**Script**:
> "First, let me show you how to start the application in console mode. I'll run the Main class which initializes the database connection and displays the main menu."

**Actions**:
1. Run command: `mvn exec:java -Dexec.mainClass="com.companyz.ems.Main"`
2. Show database connection success message
3. Display main menu with all options

**Narration Points**:
- Point out the clean menu structure
- Mention the 6 main features available
- Note the console-based interface

---

### Section 2: Search Employee (2 minutes)

**Script**:
> "Let's start with the search functionality. The system supports three types of searches: by name, by SSN, and by employee ID. I'll demonstrate searching by name first."

**Actions**:
1. Select option 1 (Search Employee)
2. Select option 1 (Search by Name)
3. Enter "Smith" as search term
4. Show search results in formatted table

**Narration Points**:
- Highlight the formatted output
- Point out all displayed fields (ID, name, SSN, job title, division, salary)
- Mention the LIKE query for partial matches

**Script (continued)**:
> "Now let me search by SSN to show exact matching."

**Actions**:
1. Return to main menu
2. Select option 1 (Search Employee)
3. Select option 2 (Search by SSN)
4. Enter a 9-digit SSN (e.g., "123456789")
5. Show single employee result

**Narration Points**:
- Note the SSN format (9 digits, no dashes)
- Mention unique constraint on SSN

---

### Section 3: Add New Employee (2 minutes)

**Script**:
> "Next, I'll demonstrate adding a new employee to the system. The system validates all required fields and ensures SSN uniqueness."

**Actions**:
1. Select option 2 (Add New Employee)
2. Enter employee details:
   - First Name: "Demo"
   - Last Name: "Employee"
   - SSN: "999888777"
   - Job Title: "QA Engineer"
   - Division: "Quality Assurance"
   - Salary: "65000"
   - Employment Type: "FULL_TIME"
3. Show success message with new employee ID

**Narration Points**:
- Mention input validation
- Point out the auto-generated employee ID
- Note the success confirmation

---

### Section 4: Update Employee Data (2 minutes)

**Script**:
> "Now I'll update an existing employee's information. The system displays current values and allows selective updates."

**Actions**:
1. Select option 3 (Update Employee)
2. Enter employee ID (use the one just created or an existing one)
3. Show current employee information
4. Update specific fields:
   - Keep first name (press Enter)
   - Keep last name (press Enter)
   - Keep SSN (press Enter)
   - Update job title to "Senior QA Engineer"
   - Keep division (press Enter)
   - Update salary to "72000"
   - Keep employment type (press Enter)
5. Show success message

**Narration Points**:
- Highlight the ability to keep current values
- Mention transaction management
- Show the update confirmation

---

### Section 5: Salary Update by Range (2-3 minutes)

**Script**:
> "One of the key features is the ability to apply percentage-based salary increases to employees within a specific salary range. This is useful for annual raises. I'll apply a 3.2% increase to employees earning between $58,000 and $105,000."

**Actions**:
1. Select option 5 (Update Salaries by Range)
2. Enter percentage: 3.2
3. Enter minimum salary: 58000
4. Enter maximum salary: 105000
5. Show confirmation prompt
6. Confirm with "yes"
7. Show count of affected employees

**Narration Points**:
- Explain the salary range criteria (>= min, < max)
- Mention transaction management for data integrity
- Point out the affected employee count
- Explain the calculation (salary * 1.032)

**Script (continued)**:
> "Let me verify the update by searching for one of the affected employees."

**Actions**:
1. Search for an employee that was in the range
2. Show the updated salary

---

### Section 6: Generate Reports (3-4 minutes)

**Script**:
> "The system provides three types of reports. Let me demonstrate each one."

#### Report 1: Full-Time Employee Report

**Actions**:
1. Select option 6 (Generate Reports)
2. Select option 1 (Full-Time Employee Report)
3. Scroll through the report showing:
   - Employee details
   - Pay statement history for each employee
   - Total employee count

**Narration Points**:
- Highlight the comprehensive employee information
- Point out the pay statement history
- Mention the total count at the bottom

#### Report 2: Pay by Job Title

**Script**:
> "Now I'll generate a report showing total pay by job title for a specific month."

**Actions**:
1. Return to reports menu
2. Select option 2 (Total Pay by Job Title)
3. Enter month: 11
4. Enter year: 2024
5. Show report with job titles and totals

**Narration Points**:
- Explain the aggregation (SUM of pay amounts)
- Point out the grand total
- Mention the JOIN query with employee table

#### Report 3: Pay by Division

**Script**:
> "Similarly, we can generate a report by division."

**Actions**:
1. Return to reports menu
2. Select option 3 (Total Pay by Division)
3. Enter month: 11
4. Enter year: 2024
5. Show report with divisions and totals

**Narration Points**:
- Compare with job title report
- Highlight the different grouping
- Show the grand total

---

### Section 7: Delete Employee (1 minute)

**Script**:
> "Finally, let me demonstrate the delete functionality. The system uses cascade delete to remove associated pay statements automatically."

**Actions**:
1. Select option 4 (Delete Employee)
2. Enter the employee ID created earlier
3. Show employee information for confirmation
4. Confirm deletion with "yes"
5. Show success message

**Narration Points**:
- Mention the confirmation step for safety
- Explain cascade delete (removes pay statements too)
- Note the transaction management

---

### Section 8: Database Verification (1-2 minutes)

**Script**:
> "Let me verify some of the changes we made by querying the database directly."

**Actions**:
1. Open MySQL client in another terminal
2. Run queries to verify:
   ```sql
   -- Show updated salaries
   SELECT empId, firstName, lastName, salary 
   FROM employee 
   WHERE salary >= 59856 AND salary < 108360;
   
   -- Verify deleted employee is gone
   SELECT * FROM employee WHERE empId = [deleted_id];
   
   -- Show pay statements still exist for other employees
   SELECT COUNT(*) FROM paystatement;
   ```

**Narration Points**:
- Confirm salary updates are persisted
- Verify deleted employee is removed
- Show data integrity maintained

---

### Section 9: Architecture Overview (1 minute)

**Script**:
> "Let me briefly show you the system architecture and code structure."

**Actions**:
1. Open project in IDE or file explorer
2. Show package structure:
   - model (Employee, PayStatement, SalaryRange)
   - repository (data access layer)
   - service (business logic layer)
   - ui (presentation layer)
   - util (utilities)
3. Briefly open one class to show JavaDoc

**Narration Points**:
- Explain layered architecture
- Mention separation of concerns
- Point out JavaDoc documentation

---

### Conclusion (30 seconds)

**Script**:
> "This concludes the demonstration of the Employee Management System. The system successfully implements all required features including employee search, data management, salary updates by range, and comprehensive reporting. The application follows best practices with layered architecture, input validation, transaction management, and comprehensive documentation. Thank you for watching."

**Actions**:
- Exit the application (option 0)
- Show final screen
- Fade out

---

## Post-Recording Checklist

- [ ] Video is 7-15 minutes long
- [ ] All features demonstrated
- [ ] Audio is clear and professional
- [ ] No sensitive information visible
- [ ] Video is in compatible format (MP4, MOV, MPEG-4)
- [ ] File size is reasonable for submission
- [ ] Video has been reviewed for quality

---

## Technical Tips

### Screen Recording Software Options

- **macOS**: QuickTime Player (built-in), OBS Studio (free)
- **Windows**: Xbox Game Bar (built-in), OBS Studio (free)
- **Linux**: SimpleScreenRecorder, OBS Studio

### Recording Settings

- **Resolution**: 1280x720 (720p) or 1920x1080 (1080p)
- **Frame Rate**: 30 fps
- **Audio**: 44.1 kHz, mono or stereo
- **Format**: MP4 (H.264 codec recommended)

### Narration Tips

- Speak clearly and at a moderate pace
- Avoid filler words ("um", "uh", "like")
- Practice the script before recording
- Use a quiet environment
- Consider using a microphone for better audio quality

### Editing Tips

- Trim any mistakes or long pauses
- Add title slide at beginning (optional)
- Add captions if required
- Ensure smooth transitions between sections
- Check audio levels are consistent

---

## Alternative: Slide-Based Presentation

If screen recording is not possible, create a slide presentation with:

1. Title slide
2. System overview
3. Screenshots of each feature
4. Database verification screenshots
5. Architecture diagram
6. Conclusion

Export as video using PowerPoint or similar tool.

---

**Note**: This script is a guide. Adjust timing and content based on your specific implementation and requirements.

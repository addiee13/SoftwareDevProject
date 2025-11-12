# Employee Management System - User Guide

## Welcome to the Employee Management System! 👋

This guide will help you understand and use all the features of the Employee Management System (EMS) for Company Z. Whether you're new to the system or need a refresher, this guide has you covered.

---

## Table of Contents

1. [What is the Employee Management System?](#what-is-the-employee-management-system)
2. [Getting Started](#getting-started)
3. [Main Menu Overview](#main-menu-overview)
4. [Feature 1: Search for Employees](#feature-1-search-for-employees)
5. [Feature 2: Add New Employee](#feature-2-add-new-employee)
6. [Feature 3: Update Employee Information](#feature-3-update-employee-information)
7. [Feature 4: Delete Employee](#feature-4-delete-employee)
8. [Feature 5: Update Salaries by Range](#feature-5-update-salaries-by-range)
9. [Feature 6: Generate Reports](#feature-6-generate-reports)
10. [Tips and Best Practices](#tips-and-best-practices)
11. [Troubleshooting](#troubleshooting)

---

## What is the Employee Management System?

The Employee Management System (EMS) is a simple, easy-to-use application that helps you manage employee information for your company. With this system, you can:

- 🔍 **Search** for employees quickly
- ➕ **Add** new employees to the system
- ✏️ **Update** employee information
- 🗑️ **Delete** employees who have left the company
- 💰 **Update salaries** for groups of employees at once
- 📊 **Generate reports** to analyze employee data and payroll

The system stores all information in a secure database, so your data is always safe and accessible.

---

## Getting Started

### Step 1: Launch the Application

Open your terminal or command prompt and run:

```bash
mvn exec:java
```

You'll see a welcome screen:

```
===========================================
  Employee Management System - Company Z
===========================================
```

### Step 2: Understanding the Main Menu

After the welcome screen, you'll see the main menu with numbered options. Simply type the number of the feature you want to use and press Enter.

---

## Main Menu Overview

When you start the application, you'll see this menu:

```
--- Main Menu ---
1. Search Employee
2. Add New Employee
3. Update Employee
4. Delete Employee
5. Update Salaries by Range
6. Generate Reports
0. Exit

Enter your choice:
```

**How to use the menu:**
- Type the number (1-6) for the feature you want
- Press **Enter**
- Type **0** to exit the application

Let's explore each feature in detail!

---

## Feature 1: Search for Employees

### What does it do?
Helps you find employees in the system using different search methods.

### When to use it?
- When you need to find an employee's information quickly
- Before updating or deleting an employee
- To verify if an employee exists in the system

### How to use it:

#### Step 1: Select Search Employee
From the main menu, type `1` and press Enter.

#### Step 2: Choose Search Method
You'll see three options:

```
--- Search Employee ---
Search by:
1. Name
2. SSN
3. Employee ID
Enter choice:
```

**Choose the method that works best for you:**

##### Option 1: Search by Name
- **Best for:** When you know the employee's first or last name
- **Example:** Type `1`, then enter `John` to find all employees named John

```
Enter choice: 1
Enter name: John
```

**What you'll see:**
```
--- Search Results ---
============================================================
ID    First Name    Last Name    SSN          Job Title              Division        Salary
------------------------------------------------------------
1     John          Smith        123456789    Software Engineer      Engineering     $75,000.00
============================================================
```

##### Option 2: Search by SSN
- **Best for:** When you have the employee's Social Security Number
- **Example:** Type `2`, then enter the 9-digit SSN

```
Enter choice: 2
Enter SSN (9 digits): 123456789
```

**Note:** You can enter the SSN with or without dashes (123-45-6789 or 123456789)

##### Option 3: Search by Employee ID
- **Best for:** When you know the employee's ID number
- **Example:** Type `3`, then enter the ID number

```
Enter choice: 3
Enter Employee ID: 1
```

#### What if no employees are found?
The system will display:
```
No employees found.
```

This means there are no employees matching your search. Double-check your spelling or try a different search method.

---

## Feature 2: Add New Employee

### What does it do?
Adds a brand new employee to the system.

### When to use it?
- When hiring a new employee
- When adding historical employee data

### How to use it:

#### Step 1: Select Add New Employee
From the main menu, type `2` and press Enter.

#### Step 2: Enter Employee Information
The system will ask for each piece of information one at a time:

```
--- Add New Employee ---
First Name: Jennifer
Last Name: Davis
SSN (9 digits): 999888777
Job Title: Marketing Manager
Division: Marketing
Salary: 92000
Employment Type (default: FULL_TIME): FULL_TIME
```

**Field Explanations:**

| Field | What to Enter | Example | Required? |
|-------|---------------|---------|-----------|
| **First Name** | Employee's first name | Jennifer | ✅ Yes |
| **Last Name** | Employee's last name | Davis | ✅ Yes |
| **SSN** | 9-digit Social Security Number (no dashes) | 999888777 | ✅ Yes |
| **Job Title** | Employee's position | Marketing Manager | ✅ Yes |
| **Division** | Department name | Marketing | ✅ Yes |
| **Salary** | Annual salary (numbers only) | 92000 | ✅ Yes |
| **Employment Type** | Usually FULL_TIME | FULL_TIME | ✅ Yes |

#### Step 3: Confirmation
If successful, you'll see:
```
Employee added successfully! Employee ID: 19
```

The system automatically assigns an Employee ID number.

### Important Notes:
- ⚠️ **SSN must be exactly 9 digits** (no letters, no dashes)
- ⚠️ **Salary must be a positive number** (no dollar signs or commas)
- ⚠️ **Each SSN must be unique** (you can't add two employees with the same SSN)

---

## Feature 3: Update Employee Information

### What does it do?
Changes information for an existing employee.

### When to use it?
- When an employee gets promoted (new job title)
- When an employee gets a raise (new salary)
- When an employee moves to a different division
- To correct any mistakes in employee data

### How to use it:

#### Step 1: Select Update Employee
From the main menu, type `3` and press Enter.

#### Step 2: Enter Employee ID
```
--- Update Employee ---
Enter Employee ID to update: 1
```

Type the ID of the employee you want to update. If you don't know the ID, use the Search feature first!

#### Step 3: Review Current Information
The system shows you the employee's current information:

```
Current Employee Information:
  Employee ID:      1
  Name:             John Smith
  SSN:              123456789
  Job Title:        Software Engineer
  Division:         Engineering
  Salary:           $75,000.00
  Employment Type:  FULL_TIME
```

#### Step 4: Enter New Values
The system will ask for each field. You have two options:

**Option A: Change the value**
- Type the new value and press Enter

**Option B: Keep the current value**
- Just press Enter without typing anything

```
Enter new values (press Enter to keep current value):
First Name [John]: 
Last Name [Smith]: 
SSN [123456789]: 
Job Title [Software Engineer]: Senior Software Engineer
Division [Engineering]: 
Salary [75000.00]: 85000
Employment Type [FULL_TIME]: 
```

In this example:
- First Name, Last Name, SSN, Division, and Employment Type stay the same (we just pressed Enter)
- Job Title changes to "Senior Software Engineer"
- Salary changes to $85,000

#### Step 5: Confirmation
```
Employee updated successfully!
```

### Pro Tips:
- 💡 Always search for the employee first to get their ID
- 💡 Only change the fields you need to update
- 💡 Press Enter to skip fields you don't want to change
- 💡 Double-check salary amounts before confirming

---

## Feature 4: Delete Employee

### What does it do?
Removes an employee from the system permanently.

### When to use it?
- When an employee leaves the company
- To remove duplicate or incorrect entries
- To clean up old employee records

### ⚠️ WARNING
**Deleting an employee is permanent!** All their information, including pay history, will be removed from the database. Make sure you really want to delete before confirming.

### How to use it:

#### Step 1: Select Delete Employee
From the main menu, type `4` and press Enter.

#### Step 2: Enter Employee ID
```
--- Delete Employee ---
Enter Employee ID to delete: 5
```

#### Step 3: Review Employee Information
The system shows you who you're about to delete:

```
Employee to delete:
  Employee ID:      5
  Name:             Test Employee5
  SSN:              555555555
  Job Title:        Engineer
  Division:         Engineering
  Salary:           $55,000.00
  Employment Type:  FULL_TIME
```

#### Step 4: Confirm Deletion
```
Are you sure you want to delete this employee? (yes/no): 
```

**Type your response:**
- Type `yes` or `y` to delete the employee
- Type `no` or `n` to cancel and keep the employee

#### Step 5: Confirmation
If you typed `yes`:
```
Employee deleted successfully!
```

If you typed `no`:
```
Deletion cancelled.
```

### Important Notes:
- ⚠️ **This action cannot be undone!**
- ⚠️ **All pay statements for this employee will also be deleted**
- 💡 Consider keeping inactive employees instead of deleting them
- 💡 Always double-check the employee information before confirming

---

## Feature 5: Update Salaries by Range

### What does it do?
Applies a percentage increase to all employees whose salaries fall within a specific range.

### When to use it?
- During annual raise periods
- To give cost-of-living adjustments
- To adjust salaries for specific salary bands
- To implement company-wide pay increases

### Real-World Example:
Your company wants to give a 3.2% raise to all employees earning between $58,000 and $105,000.

### How to use it:

#### Step 1: Select Update Salaries by Range
From the main menu, type `5` and press Enter.

#### Step 2: Enter Percentage Increase
```
--- Update Salaries by Range ---
Enter percentage increase (e.g., 3.2 for 3.2%): 3.2
```

**What to enter:**
- Just the number (e.g., `3.2` for 3.2%)
- Don't include the % symbol
- Can be a decimal (e.g., 3.2, 5.5, 10.0)

#### Step 3: Enter Minimum Salary
```
Enter minimum salary (inclusive): 58000
```

**What this means:**
- Employees earning **$58,000 or more** will be included
- The word "inclusive" means $58,000 is included in the range

#### Step 4: Enter Maximum Salary
```
Enter maximum salary (exclusive): 105000
```

**What this means:**
- Employees earning **less than $105,000** will be included
- The word "exclusive" means $105,000 is NOT included
- So the range is: $58,000 ≤ salary < $105,000

#### Step 5: Confirm the Update
The system shows you a summary:

```
Apply 3.2% increase to salaries >= $58,000 and < $105,000? (yes/no): 
```

**Review carefully:**
- Check the percentage is correct
- Check the salary range is correct
- Type `yes` to proceed or `no` to cancel

#### Step 6: See Results
```
Salary update completed!
Number of employees updated: 8
```

This tells you how many employees received the raise.

### Understanding the Calculation:

**Example:**
- Employee's current salary: $75,000
- Percentage increase: 3.2%
- Calculation: $75,000 × 1.032 = $77,400
- New salary: $77,400

**The formula:**
```
New Salary = Current Salary × (1 + Percentage / 100)
```

### Who Gets Updated?

**Employees IN the range (will get raise):**
- Current salary: $58,000 → Gets raise ✅
- Current salary: $75,000 → Gets raise ✅
- Current salary: $104,999 → Gets raise ✅

**Employees OUTSIDE the range (no raise):**
- Current salary: $57,999 → No raise ❌ (below minimum)
- Current salary: $105,000 → No raise ❌ (at or above maximum)
- Current salary: $120,000 → No raise ❌ (above maximum)

### Pro Tips:
- 💡 Use the Search feature to check salaries before and after the update
- 💡 Write down the number of employees updated for your records
- 💡 Common percentages: 3% (cost of living), 5% (merit increase), 10% (promotion)
- 💡 You can run this multiple times for different salary ranges

---

## Feature 6: Generate Reports

### What does it do?
Creates detailed reports about employees and payroll.

### When to use it?
- End of month payroll review
- Budget planning
- Analyzing compensation by department
- Reviewing employee pay history

### How to use it:

#### Step 1: Select Generate Reports
From the main menu, type `6` and press Enter.

#### Step 2: Choose Report Type
```
--- Generate Reports ---
1. Full-Time Employee Report with Pay History
2. Total Pay by Job Title (Monthly)
3. Total Pay by Division (Monthly)
Enter choice:
```

Let's explore each report:

---

### Report 1: Full-Time Employee Report with Pay History

**What it shows:**
- All full-time employees
- Complete employee information
- All pay statements for each employee

**How to use:**
1. Type `1` and press Enter
2. The report displays automatically

**Sample Output:**
```
--- Full-Time Employee Report ---
============================================================
  Employee ID:      1
  Name:             John Smith
  SSN:              123456789
  Job Title:        Software Engineer
  Division:         Engineering
  Salary:           $75,000.00
  Employment Type:  FULL_TIME

  Pay Statement History:
  --------------------------------------------------------
  Date            Period              Amount
  --------------------------------------------------------
  2024-03-31      March 2024          $6,250.00
  2024-02-29      February 2024       $6,250.00
  2024-01-31      January 2024        $6,250.00
============================================================
[... more employees ...]

Total Full-Time Employees: 18
```

**When to use this report:**
- To review all employee information at once
- To see payment history for employees
- For annual reviews or audits

---

### Report 2: Total Pay by Job Title (Monthly)

**What it shows:**
- Total amount paid to each job title for a specific month
- Helps you see which roles cost the most

**How to use:**
1. Type `2` and press Enter
2. Enter the month (1-12):
   ```
   Enter month (1-12): 1
   ```
3. Enter the year:
   ```
   Enter year: 2024
   ```

**Sample Output:**
```
--- Total Pay by Job Title ---
Month: 1/2024
============================================================
Job Title                                Total Pay
------------------------------------------------------------
Accountant                               $5,416.67
Business Analyst                         $6,000.00
DevOps Engineer                          $7,333.33
Financial Analyst                        $5,666.67
Marketing Director                       $8,750.00
Project Manager                          $9,166.67
QA Engineer                              $5,833.33
Sales Manager                            $7,666.67
Senior Developer                         $7,916.67
Software Engineer                        $13,750.00
============================================================
GRAND TOTAL                              $77,500.00
```

**How to read this report:**
- Each row shows a job title and the total paid to all employees with that title
- The GRAND TOTAL shows total payroll for the month
- Higher amounts might indicate more employees in that role or higher salaries

**When to use this report:**
- Monthly payroll analysis
- Budget planning by role
- Comparing compensation across job titles

---

### Report 3: Total Pay by Division (Monthly)

**What it shows:**
- Total amount paid to each division/department for a specific month
- Helps you see which departments cost the most

**How to use:**
1. Type `3` and press Enter
2. Enter the month (1-12):
   ```
   Enter month (1-12): 1
   ```
3. Enter the year:
   ```
   Enter year: 2024
   ```

**Sample Output:**
```
--- Total Pay by Division ---
Month: 1/2024
============================================================
Division                                 Total Pay
------------------------------------------------------------
Engineering                              $42,583.33
Finance                                  $11,083.34
Human Resources                          $11,916.66
Management                               $15,166.67
Marketing                                $13,750.00
Sales                                    $12,250.00
============================================================
GRAND TOTAL                              $106,750.00
```

**How to read this report:**
- Each row shows a division and the total paid to all employees in that division
- The GRAND TOTAL shows total payroll for the month
- You can see which departments have the highest payroll costs

**When to use this report:**
- Departmental budget reviews
- Comparing costs across divisions
- Planning department budgets

---

### Report Tips:
- 💡 **Month numbers:** January = 1, February = 2, ..., December = 12
- 💡 **Year format:** Use 4 digits (e.g., 2024, not 24)
- 💡 **No data?** If you see "No pay data found," there are no pay statements for that month
- 💡 **Save reports:** You can copy the output and paste it into a document for your records

---

## Tips and Best Practices

### General Tips
1. **Always search before updating or deleting** - Make sure you have the right employee
2. **Write down Employee IDs** - Keep a note of frequently accessed employee IDs
3. **Double-check SSNs** - SSN must be exactly 9 digits with no dashes
4. **Use descriptive job titles** - Makes reports easier to read
5. **Keep divisions consistent** - Use the same division names (e.g., always "Engineering" not sometimes "Engineering Dept")

### Data Entry Tips
- ✅ **DO:** Enter salaries as numbers only (75000)
- ❌ **DON'T:** Include dollar signs or commas ($75,000)
- ✅ **DO:** Enter SSN as 9 digits (123456789)
- ❌ **DON'T:** Include dashes (123-45-6789) - though the system will remove them automatically
- ✅ **DO:** Use consistent naming (Engineering, Finance, Marketing)
- ❌ **DON'T:** Mix formats (Eng, Engineering Dept, engineering)

### Safety Tips
- 🔒 **Never share SSNs** - Keep employee data confidential
- 💾 **Regular backups** - Ask your IT department to backup the database regularly
- ⚠️ **Be careful with delete** - Deletion is permanent!
- ✅ **Verify before salary updates** - Double-check percentage and range before confirming

---

## Troubleshooting

### Problem: "Database connection failed"
**Solution:**
1. Make sure MySQL is running
2. Check your database.properties file has correct username and password
3. Verify the database "employeeData" exists

### Problem: "No employees found" when searching
**Possible causes:**
- Employee doesn't exist in the system
- Typo in the search term
- Wrong search method (try searching by ID instead of name)

**Solution:**
- Try a different search method
- Check spelling
- Use the "Search by Name" with just part of the name

### Problem: "Invalid SSN format"
**Cause:** SSN is not exactly 9 digits

**Solution:**
- Remove any dashes or spaces
- Make sure it's exactly 9 digits
- Example: Use `123456789` not `123-45-6789`

### Problem: "Salary must be greater than 0"
**Cause:** You entered 0 or a negative number for salary

**Solution:**
- Enter a positive number
- Don't include dollar signs or commas
- Example: Use `75000` not `$75,000`

### Problem: "Employee not found" when updating
**Cause:** The Employee ID doesn't exist

**Solution:**
1. Use the Search feature to find the correct Employee ID
2. Make sure you're typing the ID number correctly

### Problem: Report shows "No pay data found"
**Cause:** There are no pay statements for that month/year

**Solution:**
- Try a different month or year
- Check if pay statements have been entered for employees
- Verify the date format (month: 1-12, year: 2024)

### Problem: "Number of employees updated: 0" after salary update
**Cause:** No employees have salaries in the specified range

**Solution:**
- Check your salary range is correct
- Use Search to verify employee salaries
- Adjust the minimum and maximum salary values

---

## Quick Reference Card

### Main Menu Options
| Number | Feature | What it does |
|--------|---------|--------------|
| 1 | Search Employee | Find employees by name, SSN, or ID |
| 2 | Add New Employee | Add a new employee to the system |
| 3 | Update Employee | Change employee information |
| 4 | Delete Employee | Remove an employee (permanent!) |
| 5 | Update Salaries by Range | Give raises to a group of employees |
| 6 | Generate Reports | View employee and payroll reports |
| 0 | Exit | Close the application |

### Search Methods
| Method | When to use | Example |
|--------|-------------|---------|
| Name | You know first or last name | "John" or "Smith" |
| SSN | You have the 9-digit SSN | "123456789" |
| Employee ID | You know the ID number | "1" or "25" |

### Report Types
| Report | Shows | Needs |
|--------|-------|-------|
| Employee Report | All employees with pay history | Nothing |
| Pay by Job Title | Total pay per job title | Month & Year |
| Pay by Division | Total pay per division | Month & Year |

---

## Need More Help?

If you're still having trouble:
1. Check the README.md file for installation instructions
2. Review the SYSTEM_TEST_CASES.md for detailed examples
3. Contact your system administrator
4. Check that your database is set up correctly

---

## Congratulations! 🎉

You now know how to use all the features of the Employee Management System! 

Remember:
- Start with Search to find employees
- Be careful with Delete (it's permanent!)
- Use Salary Updates for group raises
- Generate Reports for analysis

Happy managing! 😊

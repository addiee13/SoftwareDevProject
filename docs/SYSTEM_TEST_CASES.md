# System Test Cases

## Employee Management System - Company Z

**Version:** 1.0  
**Date:** November 2025  
**Author:** Company Z Development Team

---

## Test Environment Setup

### Prerequisites
1. MySQL 8.0+ installed and running
2. Database `employeeData` created with schema and sample data
3. Java 11+ installed
4. Maven 3.6+ installed
5. Application compiled successfully

### Test Data Setup
Run the following SQL scripts before testing:
```bash
mysql -u root -p < src/main/resources/schema.sql
mysql -u root -p < src/main/resources/sample_data.sql
```

### Starting the Application
```bash
mvn clean compile
mvn exec:java
```

---

## Test Case 1: Search Employee

### Test Case ID: TC-001

### Description
Verify that the system can search for employees using different search criteria (name, SSN, employee ID).

### Prerequisites
- Application is running
- Sample data is loaded in database
- At least one employee exists with known data

### Test Data
- **Employee Name:** "John Smith" (empId: 1, SSN: 123456789)
- **Employee SSN:** "234567890" (Sarah Johnson, empId: 2)
- **Employee ID:** 3 (Michael Williams)

### Test Steps

#### Test 1.1: Search by Name
1. From main menu, select option `1` (Search Employee)
2. Select search type `1` (Name)
3. Enter name: `John`
4. Press Enter

**Expected Result:**
- System displays all employees with "John" in first or last name
- Display includes: empId, firstName, lastName, SSN, jobTitle, division, salary
- At least one employee (John Smith) is shown
- SSN is displayed as 9 consecutive digits (123456789)

**Pass/Fail Criteria:**
- ✅ PASS: All matching employees displayed with correct information
- ❌ FAIL: No results shown, incorrect data, or system error

#### Test 1.2: Search by SSN
1. From main menu, select option `1` (Search Employee)
2. Select search type `2` (SSN)
3. Enter SSN: `234567890`
4. Press Enter

**Expected Result:**
- System displays exactly one employee (Sarah Johnson)
- All employee details are correct
- SSN matches the search term

**Pass/Fail Criteria:**
- ✅ PASS: Correct employee found and displayed
- ❌ FAIL: Wrong employee, no results, or multiple results

#### Test 1.3: Search by Employee ID
1. From main menu, select option `1` (Search Employee)
2. Select search type `3` (Employee ID)
3. Enter employee ID: `3`
4. Press Enter

**Expected Result:**
- System displays exactly one employee (Michael Williams)
- empId = 3
- All details are accurate

**Pass/Fail Criteria:**
- ✅ PASS: Correct employee displayed
- ❌ FAIL: Wrong employee or no results

#### Test 1.4: Search with No Results
1. From main menu, select option `1` (Search Employee)
2. Select search type `1` (Name)
3. Enter name: `NonExistentName`
4. Press Enter

**Expected Result:**
- System displays message: "No employees found."
- No error occurs
- Returns to main menu

**Pass/Fail Criteria:**
- ✅ PASS: Appropriate message displayed, no crash
- ❌ FAIL: System error or incorrect behavior

### Actual Results
_To be filled during testing_

### Status
- [ ] PASS
- [ ] FAIL

### Notes
_Any observations or issues_

---

## Test Case 2: Update Employee Data

### Test Case ID: TC-002

### Description
Verify that the system can update existing employee information and persist changes to the database.

### Prerequisites
- Application is running
- Employee with empId = 1 exists in database
- Current data for employee 1 is known

### Test Data
- **Employee ID:** 1
- **Original Job Title:** "Software Engineer"
- **New Job Title:** "Senior Software Engineer"
- **Original Salary:** $75,000.00
- **New Salary:** $85,000.00

### Test Steps

#### Test 2.1: Update Employee Information
1. From main menu, select option `3` (Update Employee)
2. Enter employee ID: `1`
3. System displays current employee information
4. For "Job Title", enter: `Senior Software Engineer`
5. For "Salary", enter: `85000`
6. Press Enter for other fields (keep current values)
7. Confirm the update

**Expected Result:**
- System displays "Employee updated successfully!"
- No errors occur

**Pass/Fail Criteria:**
- ✅ PASS: Success message displayed
- ❌ FAIL: Error message or system crash

#### Test 2.2: Verify Update in Database
1. From main menu, select option `1` (Search Employee)
2. Search by employee ID: `1`
3. View the employee details

**Expected Result:**
- Job Title shows: "Senior Software Engineer"
- Salary shows: $85,000.00
- Other fields remain unchanged
- SSN is still 123456789

**Pass/Fail Criteria:**
- ✅ PASS: All updates are reflected correctly
- ❌ FAIL: Data not updated or incorrect values

#### Test 2.3: Verify in Database Directly
1. Open DBeaver or MySQL command line
2. Execute query:
   ```sql
   SELECT * FROM employee WHERE empId = 1;
   ```

**Expected Result:**
- jobTitle column shows: "Senior Software Engineer"
- salary column shows: 85000.00
- Database record matches application display

**Pass/Fail Criteria:**
- ✅ PASS: Database contains updated values
- ❌ FAIL: Database not updated

#### Test 2.4: Restore Original Data
1. Update employee 1 back to original values
2. Job Title: "Software Engineer"
3. Salary: 75000

**Expected Result:**
- Data restored successfully

### Actual Results
_To be filled during testing_

### Status
- [ ] PASS
- [ ] FAIL

### Notes
_Any observations or issues_

---

## Test Case 3: Salary Update by Range

### Test Case ID: TC-003

### Description
Verify that the system can apply percentage-based salary increases to employees within a specific salary range, as specified in the requirements (3.2% for salary >= $58,000 and < $105,000).

### Prerequisites
- Application is running
- Multiple employees exist with various salaries
- At least 2 employees have salaries in the range $58,000 - $105,000

### Test Data
**Employees in Range ($58K - $105K):**
- Employee 1: $75,000 → Expected: $77,400 (3.2% increase)
- Employee 2: $95,000 → Expected: $98,040 (3.2% increase)
- Employee 4: $85,000 → Expected: $87,720 (3.2% increase)

**Employees Outside Range:**
- Employee 7: $55,000 → Expected: No change (below minimum)
- Employee 3: $110,000 → Expected: No change (above maximum)

### Test Steps

#### Test 3.1: Record Initial Salaries
1. Search for employees 1, 2, 4, 7, and 3
2. Record their current salaries:
   - Employee 1: $_______
   - Employee 2: $_______
   - Employee 4: $_______
   - Employee 7: $_______
   - Employee 3: $_______

#### Test 3.2: Apply Salary Increase
1. From main menu, select option `5` (Update Salaries by Range)
2. Enter percentage increase: `3.2`
3. Enter minimum salary: `58000`
4. Enter maximum salary: `105000`
5. Confirm the update (enter `yes`)

**Expected Result:**
- System displays: "Salary update completed!"
- System displays: "Number of employees updated: X" (where X >= 3)
- No errors occur

**Pass/Fail Criteria:**
- ✅ PASS: Success message with correct count
- ❌ FAIL: Error or incorrect count

#### Test 3.3: Verify Employees in Range Were Updated
1. Search for employee 1
2. Verify new salary is approximately $77,400
3. Search for employee 2
4. Verify new salary is approximately $98,040
5. Search for employee 4
6. Verify new salary is approximately $87,720

**Expected Result:**
- All three employees show increased salaries
- Increase is exactly 3.2% of original
- Calculation: New Salary = Old Salary × 1.032

**Pass/Fail Criteria:**
- ✅ PASS: All salaries increased by exactly 3.2%
- ❌ FAIL: Incorrect increase or no change

#### Test 3.4: Verify Employees Outside Range Were NOT Updated
1. Search for employee 7 (below range)
2. Verify salary is still $55,000 (unchanged)
3. Search for employee 3 (above range)
4. Verify salary is still $110,000 (unchanged)

**Expected Result:**
- Salaries remain unchanged
- No increase applied

**Pass/Fail Criteria:**
- ✅ PASS: Salaries unchanged for out-of-range employees
- ❌ FAIL: Salaries were incorrectly updated

#### Test 3.5: Verify in Database
1. Open DBeaver or MySQL command line
2. Execute query:
   ```sql
   SELECT empId, firstName, lastName, salary 
   FROM employee 
   WHERE empId IN (1, 2, 3, 4, 7)
   ORDER BY empId;
   ```

**Expected Result:**
- Database values match application display
- Only employees in range show updated salaries

**Pass/Fail Criteria:**
- ✅ PASS: Database reflects correct updates
- ❌ FAIL: Database inconsistent with application

### Calculation Verification

| Employee ID | Original Salary | Expected New Salary | Actual New Salary | Match? |
|-------------|----------------|---------------------|-------------------|--------|
| 1           | $75,000        | $77,400             | $_______          | [ ]    |
| 2           | $95,000        | $98,040             | $_______          | [ ]    |
| 4           | $85,000        | $87,720             | $_______          | [ ]    |
| 7           | $55,000        | $55,000 (no change) | $_______          | [ ]    |
| 3           | $110,000       | $110,000 (no change)| $_______          | [ ]    |

### Actual Results
_To be filled during testing_

### Status
- [ ] PASS
- [ ] FAIL

### Notes
_Any observations or issues_

---

## Additional Test Scenarios

### Test Case 4: Generate Employee Report
**Objective:** Verify full-time employee report with pay history

**Steps:**
1. Select option `6` (Generate Reports)
2. Select option `1` (Full-Time Employee Report)
3. Review output

**Expected:** All full-time employees displayed with pay statement history

### Test Case 5: Generate Pay by Job Title Report
**Objective:** Verify monthly pay aggregation by job title

**Steps:**
1. Select option `6` (Generate Reports)
2. Select option `2` (Total Pay by Job Title)
3. Enter month: `1`
4. Enter year: `2024`

**Expected:** Report shows total pay grouped by job title with correct totals

### Test Case 6: Generate Pay by Division Report
**Objective:** Verify monthly pay aggregation by division

**Steps:**
1. Select option `6` (Generate Reports)
2. Select option `3` (Total Pay by Division)
3. Enter month: `1`
4. Enter year: `2024`

**Expected:** Report shows total pay grouped by division with correct totals

---

## Test Summary

### Test Execution Checklist
- [ ] All test cases executed
- [ ] All pass/fail criteria documented
- [ ] Screenshots captured (if applicable)
- [ ] Database verification completed
- [ ] Issues logged (if any)

### Overall Test Results

| Test Case | Description | Status | Notes |
|-----------|-------------|--------|-------|
| TC-001    | Search Employee | [ ] PASS / [ ] FAIL | |
| TC-002    | Update Employee Data | [ ] PASS / [ ] FAIL | |
| TC-003    | Salary Update by Range | [ ] PASS / [ ] FAIL | |
| TC-004    | Employee Report | [ ] PASS / [ ] FAIL | |
| TC-005    | Pay by Job Title | [ ] PASS / [ ] FAIL | |
| TC-006    | Pay by Division | [ ] PASS / [ ] FAIL | |

### Sign-off

**Tester Name:** _________________  
**Date:** _________________  
**Signature:** _________________

---

## Appendix: Common Issues and Troubleshooting

### Issue 1: Database Connection Failed
**Solution:** Verify MySQL is running and credentials in `database.properties` are correct

### Issue 2: No Employees Found
**Solution:** Ensure sample data is loaded using `sample_data.sql`

### Issue 3: Salary Calculation Incorrect
**Solution:** Verify formula: New Salary = Old Salary × (1 + percentage / 100)

### Issue 4: SSN Format Issues
**Solution:** Ensure SSN is stored as 9 digits without dashes

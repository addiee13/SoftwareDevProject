# Employee Management System

A minimal employee management system for Company Z with less than 20 full-time employees.

## User Interface Options

This system provides two user interface options:
- **Console UI** (default) - Text-based command-line interface
- **JavaFX GUI** - Modern graphical user interface with tables and forms

## Prerequisites Installation

### Java Installation

#### macOS:
```bash
# Using Homebrew (Java 21 recommended)
brew install openjdk@21

# Add to PATH (add to ~/.zshrc or ~/.bash_profile)
echo 'export PATH="/usr/local/opt/openjdk@21/bin:$PATH"' >> ~/.zshrc
source ~/.zshrc

# Verify installation
java -version
```

**Note:** Java 11 or higher is required, but Java 21 is recommended for best JavaFX support.

#### Windows:
1. Download Java 21 JDK from [Oracle](https://www.oracle.com/java/technologies/downloads/) or [Adoptium](https://adoptium.net/)
2. Run the installer
3. Add Java to PATH:
   - Right-click "This PC" → Properties → Advanced System Settings
   - Click "Environment Variables"
   - Under System Variables, find "Path" and click Edit
   - Add: `C:\Program Files\Java\jdk-21\bin`
4. Verify: Open Command Prompt and run `java -version`

### Maven Installation

#### macOS:
```bash
# Using Homebrew (Recommended)
brew install maven

# Verify installation
mvn --version
```

**Alternative - Manual Installation (macOS):**
1. Download from: https://maven.apache.org/download.cgi
2. Extract: `tar -xvf apache-maven-3.9.x-bin.tar.gz`
3. Move: `sudo mv apache-maven-3.9.x /opt/`
4. Add to PATH in `~/.zshrc`:
   ```bash
   export PATH="/opt/apache-maven-3.9.x/bin:$PATH"
   ```
5. Reload: `source ~/.zshrc`

#### Windows:
1. Download Maven from: https://maven.apache.org/download.cgi
2. Extract the zip file to `C:\Program Files\Apache\maven`
3. Add Maven to PATH:
   - Right-click "This PC" → Properties → Advanced System Settings
   - Click "Environment Variables"
   - Under System Variables, click "New"
   - Variable name: `MAVEN_HOME`
   - Variable value: `C:\Program Files\Apache\maven`
   - Find "Path" variable and click Edit
   - Add: `%MAVEN_HOME%\bin`
4. Verify: Open Command Prompt and run `mvn --version`

### MySQL Installation

#### macOS:
```bash
# Using Homebrew
brew install mysql

# Start MySQL service
brew services start mysql

# Secure installation (set root password)
mysql_secure_installation

# Verify installation
mysql --version
```

#### Windows:
1. Download MySQL Installer from: https://dev.mysql.com/downloads/installer/
2. Run the installer and choose "Developer Default"
3. Set root password during installation
4. MySQL will be added to PATH automatically
5. Verify: Open Command Prompt and run `mysql --version`

### DBeaver Installation (Optional - for database management)

#### macOS:
```bash
brew install --cask dbeaver-community
```

#### Windows:
1. Download from: https://dbeaver.io/download/
2. Run the installer
3. Follow the installation wizard

## Database Setup

### Setup Instructions

1. **Create the database and schema:**
   ```bash
   mysql -u root -p < src/main/resources/schema.sql
   ```

2. **Load sample data:**
   ```bash
   mysql -u root -p < src/main/resources/sample_data.sql
   ```

3. **Configure database connection:**
   Edit `src/main/resources/database.properties` with your MySQL credentials:
   ```properties
   db.url=jdbc:mysql://localhost:3306/employeeData
   db.username=root
   db.password=your_password
   ```

## Build and Run

### Quick Start with Shell Scripts (macOS/Linux)

The easiest way to run the application:

#### Console Mode:
```bash
chmod +x run-console.sh
./run-console.sh
```

#### JavaFX GUI Mode:
```bash
chmod +x run-javafx.sh
./run-javafx.sh
```

### Using Maven (All Platforms)

#### Compile the project:
```bash
mvn clean compile
```

#### Run Console UI (default):
```bash
mvn exec:java
```

#### Run JavaFX GUI:
```bash
mvn javafx:run
```

#### Run tests:
```bash
mvn test
```

### Using IDE (Alternative)

#### IntelliJ IDEA:
**Console Mode:**
1. Open the project folder
2. IntelliJ will detect `pom.xml` and configure Maven automatically
3. Right-click on `Main.java` → Run 'Main.main()'

**JavaFX GUI Mode:**
1. Right-click on `JavaFxLauncher.java` → Run 'JavaFxLauncher.main()'

#### Eclipse:
**Console Mode:**
1. File → Import → Maven → Existing Maven Projects
2. Select your project folder
3. Right-click on `Main.java` → Run As → Java Application

**JavaFX GUI Mode:**
1. Right-click on `JavaFxLauncher.java` → Run As → Java Application

#### VS Code:
1. Install "Extension Pack for Java" extension
2. Open the project folder
3. Click the Run button above the `main` method in `Main.java` (Console) or `JavaFxLauncher.java` (GUI)

### Manual Compilation (Without Maven)

**Note:** Manual compilation is not recommended for JavaFX mode. Use Maven for best results.

If Maven is not available for Console mode only:

1. **Download MySQL Connector:**
   - Download from: https://dev.mysql.com/downloads/connector/j/
   - Place `mysql-connector-java-8.0.33.jar` in a `lib` folder

2. **Compile:**
   ```bash
   # macOS/Linux
   mkdir -p out
   javac -d out -cp "lib/mysql-connector-java-8.0.33.jar" \
     $(find src/main/java -name "*.java")
   
   # Windows
   mkdir out
   javac -d out -cp "lib\mysql-connector-java-8.0.33.jar" ^
     src\main\java\com\companyz\ems\**\*.java
   ```

3. **Run Console Mode:**
   ```bash
   # macOS/Linux
   java -cp "out:lib/mysql-connector-java-8.0.33.jar" com.companyz.ems.Main
   
   # Windows
   java -cp "out;lib\mysql-connector-java-8.0.33.jar" com.companyz.ems.Main
   ```

## Features

### Core Functionality
- Search employees by name, SSN, or employee ID
- Insert, update, and delete employee records
- Apply percentage-based salary increases to salary ranges
- Generate reports:
  - Full-time employee information with pay history
  - Total pay by job title for a specific month
  - Total pay by division for a specific month

### User Interfaces
- **Console UI**: Traditional text-based interface with menu navigation
- **JavaFX GUI**: Modern graphical interface with:
  - Menu bar for easy navigation
  - Table views for displaying employee data
  - Dialog forms for data entry
  - Confirmation dialogs for destructive operations

## Project Structure

```
src/
├── main/
│   ├── java/com/companyz/ems/
│   │   ├── model/              # Data models
│   │   ├── repository/         # Data access layer
│   │   ├── service/            # Business logic
│   │   ├── ui/                 # User interfaces
│   │   │   ├── ConsoleUI.java      # Console interface
│   │   │   ├── JavaFxUI.java       # JavaFX GUI
│   │   │   └── UserInterface.java  # Base interface
│   │   ├── util/               # Utilities
│   │   ├── Main.java           # Console entry point
│   │   └── JavaFxLauncher.java # JavaFX entry point
│   └── resources/
│       ├── database.properties
│       ├── schema.sql
│       └── sample_data.sql
├── test/
│   └── java/                   # Unit and integration tests
├── docs/
│   ├── USER_GUIDE.md           # Comprehensive user guide
│   └── SYSTEM_TEST_CASES.md    # Test documentation
├── run-console.sh              # Console launcher script
└── run-javafx.sh               # JavaFX launcher script
```

## Troubleshooting

### "mvn: command not found"
- **Solution:** Maven is not installed or not in PATH. Follow the Maven installation instructions above.

### "Database connection failed"
- **Solution:** 
  1. Verify MySQL is running: `mysql --version`
  2. Check credentials in `src/main/resources/database.properties`
  3. Ensure database `employeeData` exists
  4. Test connection: `mysql -u root -p employeeData`

### "Class not found" errors
- **Solution:** Run `mvn clean compile` to recompile all classes

### MySQL "Access denied" error
- **Solution:** 
  1. Reset MySQL root password
  2. Update password in `database.properties`

### Port 3306 already in use
- **Solution:** Another MySQL instance is running. Stop it or change the port in `database.properties`

### JavaFX GUI not launching or rendering issues (macOS)
- **Solution:** 
  1. Ensure Java 21 is installed (JavaFX works best with Java 21)
  2. Try using the shell script: `./run-javafx.sh`
  3. If issues persist, use Console mode: `./run-console.sh`

### "Error: JavaFX runtime components are missing"
- **Solution:** 
  1. Use Maven to run: `mvn javafx:run`
  2. Maven will automatically handle JavaFX dependencies
  3. Don't try to run JavaFX with `java -jar` without proper module configuration

## Documentation

- **[User Guide](docs/USER_GUIDE.md)** - Comprehensive guide for using all features
- **[System Test Cases](docs/SYSTEM_TEST_CASES.md)** - Test documentation and verification

## Technology Stack

- **Java 21** - Programming language
- **JavaFX 21** - GUI framework
- **MySQL 8.0** - Database
- **Maven 3.6+** - Build tool
- **JUnit 5** - Testing framework

## Authors

Company Z Development Team

## Version

1.0.0

# Calorie Counter — Assignment 6 (Spring Boot 3.3.3)

## Purpose
Calorie Counter is a Spring Boot 3.3.3 demo that tracks daily food intake. It provides a simple dashboard, food entry form, and history view backed by H2 and Spring Data JPA so you can explore CRUD patterns, Thymeleaf templating, and basic nutrition calculations in a single runnable project.

## Prerequisites
- **Java 17+** (Java 21 recommended). Verify with:
  ```bash
  java -version
  ```
- **Maven 3.9+** on PATH (no Maven Wrapper in repo). Verify with:
  ```bash
  mvn -version
  ```
- Optional: VS Code with **Extension Pack for Java** and **Spring Boot Extension Pack**. Configure JDK 17+ under **Java: Configure Java Runtime**.

## Download
Clone the repository and switch into it:
```bash
git clone <your-fork-or-clone-url>
cd calorie-counter
```

## Build / Configuration / Installation / Deployment
- **Quick start (run locally):**
  ```bash
  mvn spring-boot:run
  ```
  Then open http://localhost:8080.

- **Clean build (generates executable jar):**
  ```bash
  mvn clean package
  ```
  The jar appears under `target/` and can be run with `java -jar target/calorie-counter-*.jar`.

- **Port or property overrides:**
  ```bash
  mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8081"
  ```
  Additional properties can be set in `src/main/resources/application.properties` or via `-Dspring-boot.run.arguments`.

- **Database:** Uses in-memory H2; the console is available at http://localhost:8080/h2-console with JDBC URL `jdbc:h2:mem:caldb`.

- **VS Code launch config (optional):**
  ```jsonc
  // .vscode/launch.json
  {
    "version": "0.2.0",
    "configurations": [
      {
        "type": "java",
        "name": "Spring Boot - Calorie Counter",
        "request": "launch",
        "mainClass": "edu.csu.caloriecounter.Application",
        "projectName": "calorie-counter"
      }
    ]
  }
  ```

- **VS Code settings (force JDK 21):**
  ```jsonc
  // .vscode/settings.json
  {
    "java.configuration.runtimes": [
      { "name": "JavaSE-21", "path": "C:\\Program Files\\Java\\jdk-21", "default": true }
    ],
    "java.jdt.ls.java.home": "C:\\Program Files\\Java\\jdk-21",
    "maven.terminal.useJavaHome": true
  }
  ```

## Usage
- **Dashboard** (`/dashboard`): shows today’s totals and goal progress.
- **Add Food** (`/add`): submit new food entries with name, calories, protein, carbs, and fat.
- **History** (`/history`): review previous entries and totals.
- **H2 Console**: http://localhost:8080/h2-console (JDBC `jdbc:h2:mem:caldb`).

## Commands
```bash
# run
mvn spring-boot:run

# clean & rebuild
mvn clean package

# run on a different port
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8081"
```

## Tests
This project uses **JUnit 5** with the Spring Boot test starter. To run the full suite:
```bash
# run all tests (recommended)
mvn test

# run a specific test class
mvn -Dtest=LogServiceTest test
```

---

## Common issues
### 1) “mvnw: command not found” or “mvnw is not recognized”
This repo does not ship the Maven Wrapper scripts. Use `mvn` instead (Maven 3.9+ on PATH).
If you want to add the wrapper locally:
```bash
mvn -N wrapper:wrapper -Dmaven=3.9.9
```

### 2) “class file version 61.0” or similar
You’re using **Java 8**. Switch default Java to 17+ (21 recommended).
- `java -version` should show `17` or `21`.
- On Windows/VS Code, set JDK 21 under **Java: Configure Java Runtime**.

### 3) “Port 8080 already in use”
Run on a different port:
```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8081"
```
Or free the port (Windows):
```powershell
netstat -ano | findstr :8080
tasklist /FI "PID eq <PID>"
taskkill /PID <PID> /F
```

### 3) Whitelabel 500 on Dashboard
Already fixed: controller precomputes `percent`; views avoid method calls.

---

## Environment check scripts (optional)
We provide simple scripts to verify Java, Maven/Wrapper, and port status:
- `scripts/env-check.ps1` (Windows PowerShell)
- `scripts/env-check.sh` (macOS/Linux)

Run them from repo root; they will print helpful hints.

---

## Layout
```
src/main/java/edu/csu/caloriecounter/...
src/main/resources/templates/
src/main/resources/application.properties
docs/class-diagram.puml
docs/food-catalog.xlsx (food presets loaded by the app)
```

---

## Help
Include this in bug reports:
```
java -version
mvn -version
git rev-parse --short HEAD
```

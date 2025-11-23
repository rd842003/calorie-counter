# Calorie Counter — Assignment 6 (Spring Boot 3.3.3)

This repo contains a minimal, runnable prototype for the A6 demo:
- Spring Boot 3.3.3, Thymeleaf, Spring Data JPA, H2 (in‑memory)
- Pages: **Dashboard** (`/dashboard`), **Add Food** (`/add`), **History** (`/history`)
- Default port: **8080**

---

## ✅ Quick Start (one command)

This repo **does not include the Maven Wrapper**. Make sure Maven 3.9+ is installed and on your PATH.

From the repo root run **ONE** of the following:
```bash
# Unix/macOS
mvn spring-boot:run
# Windows PowerShell/CMD
mvn spring-boot:run
```

Then browse: http://localhost:8080

---

## 📦 Requirements

- **Java 17+** (Java 21 recommended).
  Check:
  ```bash
  java -version
  ```
  Expect something like `21.0.x`. If you see `1.8.x`, update your default Java.

- **Maven 3.9+** installed and on PATH: `mvn -version`

---

## 🧰 VS Code setup (optional but helpful)

1. Install extensions:
   - *Extension Pack for Java*
   - *Spring Boot Extension Pack*

2. Ensure VS Code uses JDK 17+:
   - Command Palette → **Java: Configure Java Runtime** → set your JDK 21 as default
   - Or add `.vscode/settings.json` (see below)

**Launch config** (green Run button):
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

**Settings** (forces JDK 21 and uses it for Maven too):
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

---

## 🚀 Commands

```bash
# run
mvn spring-boot:run

# clean & rebuild
mvn clean package

# run on a different port
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8081"
```

## 🧪 Tests

This project uses **JUnit 5** with the Spring Boot test starter. To run the full suite:

```bash
# run all tests (recommended)
mvn test

# run a specific test class
mvn -Dtest=LogServiceTest test
```

H2 Console: http://localhost:8080/h2-console
JDBC URL: `jdbc:h2:mem:caldb`

---

## 🧩 Common issues

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

## 🧪 Environment check scripts (optional)

We provide simple scripts to verify Java, Maven/Wrapper, and port status:

- `scripts/env-check.ps1` (Windows PowerShell)
- `scripts/env-check.sh` (macOS/Linux)

Run them from repo root; they will print helpful hints.

---

## 📁 Layout

```
src/main/java/edu/csu/caloriecounter/...
src/main/resources/templates/
src/main/resources/application.properties
docs/class-diagram.puml
docs/food-catalog.xlsx (food presets loaded by the app)
```

---

## 🙋 Help
Include this in bug reports:
```
java -version
mvn -version
git rev-parse --short HEAD
```

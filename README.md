# Campus Hiring Assignment - BFHL REST API

A production-ready Spring Boot REST API for processing mixed character arrays.

## Features
- **Categorization**: Parses arrays containing numeric strings (further categorized into odd and even), alphabetical characters (converted to uppercase), and special characters.
- **Sum Calculation**: Computes the sum of all numeric values.
- **Custom Alternating-Case Concat Logic**: Extracts alphabetical characters, reverses the string, and formats it to alternating casing (e.g., `["A", "ABCD", "DOE"]` -> `"EoDdCbAa"`).
- **Validation**: Rejects null, empty, or blank request inputs with explicit HTTP 400 response.
- **Global Exception Handling**: Returns clear JSON error payloads for any server errors or invalid formats.
- **Layered Architecture**: Follows best practices using Request/Response DTOs, interfaces, implementations, controllers, and exception advice.
- **Lombok & Logging**: Utilizes Lombok to reduce boilerplate and Slf4j for structured runtime logs.

---

## Directory Structure
```text
d:\FORme
├── pom.xml
├── README.md
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── campus
    │   │           └── bfhl
    │   │               ├── BfhlApplication.java
    │   │               ├── controller
    │   │               │   └── BfhlController.java
    │   │               ├── dto
    │   │               │   ├── BfhlRequest.java
    │   │               │   └── BfhlResponse.java
    │   │               ├── exception
    │   │               │   ├── GlobalExceptionHandler.java
    │   │               │   └── InvalidInputException.java
    │   │               └── service
    │   │                   ├── BfhlService.java
    │   │                   └── impl
    │   │                       └── BfhlServiceImpl.java
    │   └── resources
    │       └── application.properties
    └── test
        └── java
            └── com
                └── campus
                    └── bfhl
                        ├── BfhlApplicationTests.java
                        ├── controller
                        │   └── BfhlControllerTest.java
                        └── service
                            └── impl
                                └── BfhlServiceImplTest.java
```

---

## Exact Terminal Commands

### 1. Compile, Test and Build
To clean the target directory, compile all source files, and run the JUnit test cases, run:
```bash
mvn clean install
```
*(If Maven is not on your PATH, use the full path to your Maven executable, e.g. `& "C:\Program Files\JetBrains\IntelliJ IDEA 2026.1.2\plugins\maven\lib\maven3\bin\mvn.cmd" clean install`)*

### 2. Run the Application
To run the Spring Boot application locally:
```bash
mvn spring-boot:run
```
*(The server will start on port `8080` by default)*

---

## API Documentation

### POST `/bfhl`

#### 1. Example 1 (Standard Request)

**Request Header:**
`Content-Type: application/json`

**Request Body:**
```json
{
  "data": ["a", "1", "334", "4", "R", "$"]
}
```

**Response Body (HTTP 200 OK):**
```json
{
  "is_success": true,
  "user_id": "shivani_prajapati_26052026",
  "email": "shivani.prajapati@university.edu",
  "roll_number": "CU12345678",
  "odd_numbers": ["1"],
  "even_numbers": ["334", "4"],
  "alphabets": ["A", "R"],
  "special_characters": ["$"],
  "sum": 339,
  "concat_string": "Ra"
}
```

#### 2. Example 2 (Sample Output for Requirement 18)

**Request Header:**
`Content-Type: application/json`

**Request Body:**
```json
{
  "data": ["2", "a", "y", "4", "&", "-", "*", "5", "92", "b"]
}
```

**Response Body (HTTP 200 OK):**
```json
{
  "is_success": true,
  "user_id": "shivani_prajapati_26052026",
  "email": "shivani.prajapati@university.edu",
  "roll_number": "CU12345678",
  "odd_numbers": ["5"],
  "even_numbers": ["2", "4", "92"],
  "alphabets": ["A", "Y", "B"],
  "special_characters": ["&", "-", "*"],
  "sum": 103,
  "concat_string": "ByA"
}
```

#### 3. Example 3 (Validation Failure Request)

**Request Body:**
```json
{
  "data": []
}
```

**Response Body (HTTP 400 Bad Request):**
```json
{
  "is_success": false,
  "error_message": "Validation failed: Input data list cannot be empty"
}
```

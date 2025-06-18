# MedGuide-Hospital-Management-System

Project to resolve various problems faced by patients, doctors, and hospitals. This Application acts as an Interface between all the mentioned groups, facilitating smoother interactions and operations within a hospital environment.

## Features

*   **Appointment Management**: Patients can book appointments, and these can be managed by hospital staff.
*   **OTP Verification**: Secure appointment booking using OTP (One Time Password) sent to the patient's mobile phone.
*   **User Roles**: Distinct interfaces and functionalities for different users (Admin, Doctor, Receptionist, Patient).
*   **Patient Management**: Allows for registration and management of patient records.
*   **Doctor Management**: Enables administration of doctor profiles and schedules.
*   **Employee Management**: Provides tools for managing hospital employee data.

## Technologies Used

*   **Programming Language**: Java
*   **User Interface**: Java Swing
*   **Database**: Oracle Database
*   **Database Connectivity**: JDBC
*   **Design Pattern**: Data Access Object (DAO) architecture for database interactions.

## Recent Enhancements

To improve performance, stability, and maintainability, the database interaction layer of this project has been significantly refactored:

*   **Connection Pooling**: Implemented HikariCP, a high-performance JDBC connection pool, to efficiently manage database connections. This replaces the previous single-connection model, leading to better scalability under load.
*   **Optimized Database Operations**:
    *   Switched from `java.sql.Statement` to `java.sql.PreparedStatement` for all database queries, enhancing performance and providing protection against SQL injection vulnerabilities.
    *   Adopted try-with-resources statements to ensure proper and automatic closure of database resources (Connections, PreparedStatements, ResultSets), preventing resource leaks.
*   **Efficient ID Generation**: The method for generating new patient IDs (`getNewPatId` in `PatientDao`) has been optimized to query the maximum existing ID instead of counting all records, which is more efficient for large datasets.
*   **Query Refinements**: Ensured that SQL queries select only necessary columns by name, rather than using `SELECT *`, reducing data transfer and processing.

These changes contribute to a more robust and responsive application.

## Setup and Running the Project

This project was developed using Apache NetBeans.

1.  **Prerequisites**:
    *   Java Development Kit (JDK) 8 or higher.
    *   Oracle Database (ensure you have a user with appropriate permissions, e.g., the 'med' user with 'admin' password as referenced in the initial DB connection setup).
    *   Apache NetBeans IDE (optional, but helpful for project management).
2.  **Database Setup**:
    *   The application expects a specific database schema. Ensure the necessary tables (patient, doctors, employees, users, etc.) are created.
    *   The database connection details (URL, username, password) are managed in `src/hospital/dbutil/DBConnection.java`. This class now uses HikariCP for connection pooling. The original hardcoded connection targeted `jdbc:oracle:thin:@//LAPTOP-Q82125JL:1521/xe` with user `med` and password `admin` – adapt this configuration as needed for your environment.
3.  **Running the Application**:
    *   Open the project in NetBeans.
    *   The main entry point for the application is likely `src/hospital/gui/SplashScreenFrame.java` or a similar GUI starting point.
    *   Build and run the project through the IDE.

## Further Development (Placeholder)

*   Detailed instructions for schema setup.
*   Instructions for running outside an IDE.
*   More comprehensive error handling and user feedback.
*   Migration to a more modern UI framework.
*   Implementation of a build system like Maven or Gradle.
*   Addition of unit and integration tests.

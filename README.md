# Student Management System
## Introduction
The Student Management System is a Java-based application designed to help manage student information, courses, and enrollments. The system provides an intuitive graphical user interface (GUI) built with Java Swing, allowing users to perform various operations such as adding, removing, updating, and viewing student details.

## Features
- User-friendly GUI for easy interaction
- Add, update, and remove student records
- View student details
- Manage course enrollments
- Support for updating student information by ID or name
- Database-backed functionality for persistent data storage

## Technologies Used
- Java 8 or higher
- Java Swing for the GUI
- MySQL for the database
- JDBC for database connectivity

## Installation
1. **Clone the Repository**: 
   ```bash
   git clone <repository-url>
   ```

2. **Set Up MySQL Database**:
   - Start your MySQL server.
   - Log in to MySQL using the command line or a GUI tool:
     ```bash
     mysql -u <username> -p
     ```

   - Create a new database called `studentmanagementsystem`:
     ```sql
     CREATE DATABASE studentmanagementsystem;
     ```

   - Switch to the newly created database:
     ```sql
     USE studentmanagementsystem;
     ```

   - Execute the SQL script `student.sql` to set up the required tables and initial data:
     ```sql
     SOURCE path/to/student.sql;
     ```

3. **Configure Database Connection**:
   - Open the `DatabaseUtil` class in your project and ensure the database connection details are correct:
     ```java
     public static Connection getConnection() throws SQLException {
         String url = "jdbc:mysql://localhost:3306/studentmanagementsystem";
         String user = "your_username";
         String password = "your_password";
         return DriverManager.getConnection(url, user, password);
     }
     ```

4. **Compile the Project**:
   - Use an IDE (like IntelliJ IDEA or Eclipse) or command line to compile the project.

5. **Run the Application**:
   - Execute the main class to start the application.

## Usage
- **Login**: Enter your credentials to access the system.
- **Menu Options**: Choose from options to add, view, update, or remove students.
- **Update Student**: Select how to update (by ID or name) and provide the new details.
- **Exit**: Use the exit option to close the application.

## Database Structure
The database consists of three main tables:

1. **Students Table**:
   - `id` (VARCHAR): Unique identifier for each student.
   - `name` (VARCHAR): Name of the student.

2. **Courses Table**:
   - `title` (VARCHAR): Name of the course.

3. **Enrollments Table**:
   - `course_title` (VARCHAR): Title of the enrolled course (foreign key).
   - `student_id` (VARCHAR): ID of the student enrolled (foreign key).
   - Primary key: Combination of `course_title` and `student_id`.

   Here’s the SQL code to create the tables:

   ```sql
   CREATE TABLE students (
       id VARCHAR(50) PRIMARY KEY,
       name VARCHAR(100) NOT NULL
   );

   CREATE TABLE courses (
       title VARCHAR(100) PRIMARY KEY
   );

   CREATE TABLE enrollments (
       course_title VARCHAR(100),
       student_id VARCHAR(50),
       PRIMARY KEY (course_title, student_id),
       FOREIGN KEY (course_title) REFERENCES courses(title),
       FOREIGN KEY (student_id) REFERENCES students(id)
   );
   ```

## How to Run the Application
1. Ensure that the MySQL server is running and the database is set up.
2. Compile and run the `Main` class of the project in your IDE.
3. Interact with the GUI to manage student records.

## Database Connection
To connect to the database, ensure that the MySQL JDBC driver is included in your project. You can download the driver from the [MySQL Connector/J website](https://dev.mysql.com/downloads/connector/j/). 

In your Java project, include the driver in your build path. 

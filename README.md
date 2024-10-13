Introduction
The Student Management System is a Java-based application designed to help manage student information, courses, and enrollments. The system provides an intuitive graphical user interface (GUI) built with Java Swing, allowing users to perform various operations such as adding, removing, updating, and viewing student details.

Features
User-friendly GUI for easy interaction
Add, update, and remove student records
View student details
Manage course enrollments
Support for updating student information by ID or name
Database-backed functionality for persistent data storage
Technologies Used
Java 8 or higher
Java Swing for the GUI
MySQL for the database
JDBC for database connectivity
Installation
Clone the Repository:

bash
Copy code
git clone <repository-url>
Set Up MySQL Database:

Create a new database called studentmanagementsystem.
Execute the SQL script student.sql to set up the required tables and initial data.
Configure Database Connection:

Ensure the DatabaseUtil class is configured with the correct database URL, username, and password.
Compile the Project:

Use an IDE (like IntelliJ IDEA or Eclipse) or command line to compile the project.
Run the Application:

Execute the main class to start the application.
Usage
Login: Enter your credentials to access the system.
Menu Options: Choose from options to add, view, update, or remove students.
Update Student: Select how to update (by ID or name) and provide the new details.
Exit: Use the exit option to close the application.
Database Structure
The database consists of three main tables:

Students Table:

id (VARCHAR): Unique identifier for each student.
name (VARCHAR): Name of the student.
Courses Table:

title (VARCHAR): Name of the course.
Enrollments Table:

course_title (VARCHAR): Title of the enrolled course (foreign key).
student_id (VARCHAR): ID of the student enrolled (foreign key).
Primary key: Combination of course_title and student_id.
How to Run the Application
Ensure that the MySQL server is running and the database is set up.
Compile and run the Main class of the project in your IDE.
Interact with the GUI to manage student records.

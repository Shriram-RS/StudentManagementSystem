import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class GUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTextField nameField, idField, courseField;
	private JLabel nameLabel, idLabel, courseLabel;
	private JButton createStudentButton, createCourseButton;
	private JButton addInCourseButton, printCourseDetailsButton;
	private JButton deleteStudentButton, deleteCourseButton;
	private JButton updateStudentButton, updateCourseButton;
	private JButton removeStudentFromCourseButton, listAllStudentsButton, listAllCoursesButton;
	private JButton backToMainPanelButton;
	private JTextArea detailsArea;
	private CardLayout cardLayout = new CardLayout();
	private JPanel cardPanel = new JPanel(cardLayout);
	private JPanel mainPanel = new JPanel();
	private JPanel detailsPanel = new JPanel();
	private JTextField newCourseField; // Add this line

	public GUI() {
		// Set Look and Feel to Nimbus for a modern appearance
		try {
			for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			// If Nimbus is not available, fall back to default
		}

		// Initialize Panels
		initializeMainPanel();
		initializeDetailsPanel();

		// Add Panels to CardLayout
		cardPanel.add(mainPanel, "Main");
		cardPanel.add(detailsPanel, "Details");

		// Configure Frame
		this.add(cardPanel);
		this.setTitle("Student Management System");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800, 600);
		this.setLocationRelativeTo(null); // Center on screen
		this.setResizable(false);
		this.setVisible(true);
	}

	private void initializeMainPanel() {
		mainPanel.setLayout(new GridBagLayout());
		mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20)); // Add padding

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10); // Component spacing
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1;

		// Name Label and Field
		nameLabel = new JLabel("Name:");
		nameLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
		gbc.gridx = 0;
		gbc.gridy = 0;
		mainPanel.add(nameLabel, gbc);

		nameField = new JTextField(20);
		nameField.setFont(new Font("SansSerif", Font.PLAIN, 16));
		gbc.gridx = 1;
		mainPanel.add(nameField, gbc);

		// ID Label and Field
		idLabel = new JLabel("ID:");
		idLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
		gbc.gridx = 0;
		gbc.gridy = 1;
		mainPanel.add(idLabel, gbc);

		idField = new JTextField(20);
		idField.setFont(new Font("SansSerif", Font.PLAIN, 16));
		gbc.gridx = 1;
		mainPanel.add(idField, gbc);

		// Course Label and Field
		courseLabel = new JLabel("Course:");
		courseLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
		gbc.gridx = 0;
		gbc.gridy = 2;
		mainPanel.add(courseLabel, gbc);

		courseField = new JTextField(20);
		courseField.setFont(new Font("SansSerif", Font.PLAIN, 16));
		gbc.gridx = 1;
		mainPanel.add(courseField, gbc);

		// Buttons Panel
		JPanel buttonsPanel = new JPanel(new GridLayout(7, 2, 10, 10));
		buttonsPanel.setBorder(new EmptyBorder(20, 0, 0, 0));

		createStudentButton = new JButton("Create Student");
		styleButton(createStudentButton);
		buttonsPanel.add(createStudentButton);

		createCourseButton = new JButton("Create New Course");
		styleButton(createCourseButton);
		buttonsPanel.add(createCourseButton);

		addInCourseButton = new JButton("Add Student to Course");
		styleButton(addInCourseButton);
		buttonsPanel.add(addInCourseButton);

		removeStudentFromCourseButton = new JButton("Remove Student from Course");
		styleButton(removeStudentFromCourseButton);
		buttonsPanel.add(removeStudentFromCourseButton);

		deleteStudentButton = new JButton("Delete Student");
		styleButton(deleteStudentButton);
		buttonsPanel.add(deleteStudentButton);

		deleteCourseButton = new JButton("Delete Course");
		styleButton(deleteCourseButton);
		buttonsPanel.add(deleteCourseButton);

		updateStudentButton = new JButton("Update Student");
		styleButton(updateStudentButton);
		buttonsPanel.add(updateStudentButton);

		updateCourseButton = new JButton("Update Course");
		styleButton(updateCourseButton);
		buttonsPanel.add(updateCourseButton);

		listAllStudentsButton = new JButton("List All Students");
		styleButton(listAllStudentsButton);
		buttonsPanel.add(listAllStudentsButton);

		listAllCoursesButton = new JButton("List All Courses");
		styleButton(listAllCoursesButton);
		buttonsPanel.add(listAllCoursesButton);

		printCourseDetailsButton = new JButton("Print Course Details");
		styleButton(printCourseDetailsButton);
		buttonsPanel.add(printCourseDetailsButton);

		buttonsPanel.add(new JLabel("")); // Empty space for alignment

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		mainPanel.add(buttonsPanel, gbc);

		// Action Listeners
		createStudentButton.addActionListener(e -> createStudent());
		createCourseButton.addActionListener(e -> createCourse());
		addInCourseButton.addActionListener(e -> addStudentToCourse());
		removeStudentFromCourseButton.addActionListener(e -> removeStudentFromCourse());
		deleteStudentButton.addActionListener(e -> deleteStudent());
		deleteCourseButton.addActionListener(e -> deleteCourse());
		updateStudentButton.addActionListener(e -> onUpdateStudent());
		updateCourseButton.addActionListener(e -> onUpdateCourse());
		listAllStudentsButton.addActionListener(e -> listAllStudents());
		listAllCoursesButton.addActionListener(e -> listAllCourses());
		printCourseDetailsButton.addActionListener(e -> onViewCourseDetails());
	}

	private void styleButton(JButton button) {
		button.setFont(new Font("SansSerif", Font.BOLD, 16));
		button.setBackground(Color.LIGHT_GRAY);
		button.setForeground(Color.BLACK);
		button.setFocusPainted(false);
	}

	private void initializeDetailsPanel() {
		detailsPanel.setLayout(new BorderLayout(10, 10));
		detailsPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

		// Details Text Area
		detailsArea = new JTextArea();
		detailsArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
		detailsArea.setEditable(false);
		detailsArea.setLineWrap(true);
		detailsArea.setWrapStyleWord(true);
		JScrollPane scrollPane = new JScrollPane(detailsArea);
		detailsPanel.add(scrollPane, BorderLayout.CENTER);

		// Back Button
		backToMainPanelButton = new JButton("Back to Main Screen");
		styleButton(backToMainPanelButton);
		JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		backPanel.add(backToMainPanelButton);
		detailsPanel.add(backPanel, BorderLayout.SOUTH);

		// Action Listener
		backToMainPanelButton.addActionListener(e -> cardLayout.show(cardPanel, "Main"));
	}

	private void createStudent() {
		String name = nameField.getText().trim();
		String id = idField.getText().trim();

		if (name.isEmpty() || id.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Name and ID fields cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		// SQL Queries
		String checkQuery = "SELECT * FROM students WHERE id = ?";
		String insertQuery = "INSERT INTO students (id, name) VALUES (?, ?)";
		try (Connection conn = DatabaseUtil.getConnection();
			 PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
			 PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
			// Check if student already exists
			checkStmt.setString(1, id);
			ResultSet rs = checkStmt.executeQuery();

			if (rs.next()) {
				JOptionPane.showMessageDialog(this, "Student with this ID already exists.", "Duplicate Entry", JOptionPane.WARNING_MESSAGE);
				return;
			}
			// Insert new student
			insertStmt.setString(1, id);
			insertStmt.setString(2, name);
			insertStmt.executeUpdate();
			JOptionPane.showMessageDialog(this, "New student created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
			clearFields();
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error creating student: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	private void createCourse() {
		String courseTitle = courseField.getText().trim();
		if (courseTitle.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Course field cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		String checkQuery = "SELECT * FROM courses WHERE title = ?";
		String insertQuery = "INSERT INTO courses (title) VALUES (?)";
		try (Connection conn = DatabaseUtil.getConnection();
			 PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
			 PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
			// Check if course already exists
			checkStmt.setString(1, courseTitle);
			ResultSet rs = checkStmt.executeQuery();
			if (rs.next()) {
				JOptionPane.showMessageDialog(this, "Course already exists.", "Duplicate Entry", JOptionPane.WARNING_MESSAGE);
				return;
			}
			insertStmt.setString(1, courseTitle);
			insertStmt.executeUpdate();
			JOptionPane.showMessageDialog(this, "New course added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
			clearFields();
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error creating course: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	private void addStudentToCourse() {
		String courseTitle = courseField.getText().trim();
		String id = idField.getText().trim();
		if (courseTitle.isEmpty() || id.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Course and ID fields cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		String checkCourseQuery = "SELECT * FROM courses WHERE title = ?";
		String checkStudentQuery = "SELECT * FROM students WHERE id = ?";
		String checkEnrollmentQuery = "SELECT * FROM enrollments WHERE course_title = ? AND student_id = ?";
		String enrollQuery = "INSERT INTO enrollments (course_title, student_id) VALUES (?, ?)";
		try (Connection conn = DatabaseUtil.getConnection();
			 PreparedStatement checkCourseStmt = conn.prepareStatement(checkCourseQuery);
			 PreparedStatement checkStudentStmt = conn.prepareStatement(checkStudentQuery);
			 PreparedStatement checkEnrollmentStmt = conn.prepareStatement(checkEnrollmentQuery);
			 PreparedStatement enrollStmt = conn.prepareStatement(enrollQuery)) {
			checkCourseStmt.setString(1, courseTitle);
			ResultSet rsCourse = checkCourseStmt.executeQuery();
			if (!rsCourse.next()) {
				JOptionPane.showMessageDialog(this, "Course does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			checkStudentStmt.setString(1, id);
			ResultSet rsStudent = checkStudentStmt.executeQuery();
			if (!rsStudent.next()) {
				JOptionPane.showMessageDialog(this, "Student does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			checkEnrollmentStmt.setString(1, courseTitle);
			checkEnrollmentStmt.setString(2, id);
			ResultSet rsEnrollment = checkEnrollmentStmt.executeQuery();
			if (rsEnrollment.next()) {
				JOptionPane.showMessageDialog(this, "Student is already enrolled in this course.", "Duplicate Entry", JOptionPane.WARNING_MESSAGE);
				return;
			}
			enrollStmt.setString(1, courseTitle);
			enrollStmt.setString(2, id);
			enrollStmt.executeUpdate();
			JOptionPane.showMessageDialog(this, "Student added to course successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
			clearFields();
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error enrolling student: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	private void removeStudentFromCourse() {
		String courseTitle = courseField.getText().trim();
		String id = idField.getText().trim();
		if (courseTitle.isEmpty() || id.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Course and ID fields cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		String checkEnrollmentQuery = "SELECT * FROM enrollments WHERE course_title = ? AND student_id = ?";
		String deleteEnrollmentQuery = "DELETE FROM enrollments WHERE course_title = ? AND student_id = ?";
		try (Connection conn = DatabaseUtil.getConnection();
			 PreparedStatement checkEnrollmentStmt = conn.prepareStatement(checkEnrollmentQuery);
			 PreparedStatement deleteEnrollmentStmt = conn.prepareStatement(deleteEnrollmentQuery)) {
			checkEnrollmentStmt.setString(1, courseTitle);
			checkEnrollmentStmt.setString(2, id);
			ResultSet rsEnrollment = checkEnrollmentStmt.executeQuery();
			if (!rsEnrollment.next()) {
				JOptionPane.showMessageDialog(this, "Student is not enrolled in this course.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			deleteEnrollmentStmt.setString(1, courseTitle);
			deleteEnrollmentStmt.setString(2, id);
			deleteEnrollmentStmt.executeUpdate();
			JOptionPane.showMessageDialog(this, "Student removed from course successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
			clearFields();
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error removing student from course: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void deleteStudent() {
		String id = idField.getText().trim();
		if (id.isEmpty()) {
			JOptionPane.showMessageDialog(this, "ID field cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		String checkStudentQuery = "SELECT * FROM students WHERE id = ?";
		String deleteStudentQuery = "DELETE FROM students WHERE id = ?";
		try (Connection conn = DatabaseUtil.getConnection();
			 PreparedStatement checkStudentStmt = conn.prepareStatement(checkStudentQuery);
			 PreparedStatement deleteStudentStmt = conn.prepareStatement(deleteStudentQuery)) {
			checkStudentStmt.setString(1, id);
			ResultSet rsStudent = checkStudentStmt.executeQuery();
			if (!rsStudent.next()) {
				JOptionPane.showMessageDialog(this, "Student does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this student?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
			if (confirm != JOptionPane.YES_OPTION) {
				return;
			}
			deleteStudentStmt.setString(1, id);
			deleteStudentStmt.executeUpdate();
			JOptionPane.showMessageDialog(this, "Student deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
			clearFields();
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error deleting student: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	private void deleteCourse() {
		String courseTitle = courseField.getText().trim();
		if (courseTitle.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Course field cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		String checkCourseQuery = "SELECT * FROM courses WHERE title = ?";
		String deleteCourseQuery = "DELETE FROM courses WHERE title = ?";
		try (Connection conn = DatabaseUtil.getConnection();
			 PreparedStatement checkCourseStmt = conn.prepareStatement(checkCourseQuery);
			 PreparedStatement deleteCourseStmt = conn.prepareStatement(deleteCourseQuery)) {
			checkCourseStmt.setString(1, courseTitle);
			ResultSet rsCourse = checkCourseStmt.executeQuery();
			if (!rsCourse.next()) {
				JOptionPane.showMessageDialog(this, "Course does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this course?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
			if (confirm != JOptionPane.YES_OPTION) {
				return;
			}
			deleteCourseStmt.setString(1, courseTitle);
			deleteCourseStmt.executeUpdate();
			JOptionPane.showMessageDialog(this, "Course deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
			clearFields();
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error deleting course: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	private void listAllStudents() {
		String query = "SELECT * FROM students";
		StringBuilder sb = new StringBuilder();
		try (Connection conn = DatabaseUtil.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(query);
			 ResultSet rs = stmt.executeQuery()) {
			sb.append("List of All Students:\n");
			sb.append("----------------------------\n");
			boolean hasStudents = false;
			while (rs.next()) {
				String id = rs.getString("id");
				String name = rs.getString("name");
				sb.append("Name: ").append(name).append(", ID: ").append(id).append("\n");
				hasStudents = true;
			}
			if (!hasStudents) {
				sb.append("No students registered in the system.");
			} else {
				sb.append("----------------------------\nTotal Students: ").append(getCount("students")).append("\n");
			}
			detailsArea.setText(sb.toString());
			cardLayout.show(cardPanel, "Details");
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error listing students: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	private void listAllCourses() {
		String query = "SELECT * FROM courses";
		StringBuilder sb = new StringBuilder();
		try (Connection conn = DatabaseUtil.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(query);
			 ResultSet rs = stmt.executeQuery()) {
			sb.append("List of All Courses:\n");
			sb.append("----------------------------\n");
			boolean hasCourses = false;
			while (rs.next()) {
				String title = rs.getString("title");
				sb.append("Course Title: ").append(title).append("\n");
				hasCourses = true;
			}
			if (!hasCourses) {
				sb.append("No courses available in the system.");
			} else {
				sb.append("----------------------------\nTotal Courses: ").append(getCount("courses")).append("\n");
			}
			detailsArea.setText(sb.toString());
			cardLayout.show(cardPanel, "Details");
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error listing courses: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private int getCount(String tableName) {
		String countQuery = "SELECT COUNT(*) AS total FROM " + tableName;
		try (Connection conn = DatabaseUtil.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(countQuery);
			 ResultSet rs = stmt.executeQuery()) {

			if (rs.next()) {
				return rs.getInt("total");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}


	private void clearFields() {
		nameField.setText("");
		idField.setText("");
		courseField.setText("");
	}
	private void onUpdateCourse() {
		UpdateCourseDialog dialog = new UpdateCourseDialog(this);
		dialog.setVisible(true);
	}
	private void onUpdateStudent() {
		UpdateStudentDialog dialog = new UpdateStudentDialog(this);
		dialog.setVisible(true);
	}
	private void onViewCourseDetails() {
		ViewCourseDetailsDialog dialog = new ViewCourseDetailsDialog(this);
		dialog.setVisible(true);
	}
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new GUI());
	}
}
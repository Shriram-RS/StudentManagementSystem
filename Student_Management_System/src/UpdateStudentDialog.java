import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateStudentDialog extends JDialog {
    private JTextField idField;
    private JTextField nameField;
    private JTextField newCourseField;
    private JCheckBox updateById;
    private JCheckBox updateByName;

    public UpdateStudentDialog(Frame parent) {
        super(parent, "Update Student", true);
        setLayout(new GridLayout(5, 2));
        setSize(400, 200);
        setLocationRelativeTo(parent);

        idField = new JTextField();
        nameField = new JTextField();
        newCourseField = new JTextField();

        updateById = new JCheckBox("Update by ID");
        updateByName = new JCheckBox("Update by Name");

        add(updateById);
        add(new JLabel("ID:"));
        add(idField);

        add(updateByName);
        add(new JLabel("Name:"));
        add(nameField);

        add(new JLabel("New Course:"));
        add(newCourseField);

        JButton updateButton = new JButton("Update Student");
        updateButton.addActionListener(new UpdateStudentAction());
        add(updateButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        add(cancelButton);
    }

    private class UpdateStudentAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            String newCourse = newCourseField.getText().trim();

            if (!updateById.isSelected() && !updateByName.isSelected()) {
                JOptionPane.showMessageDialog(UpdateStudentDialog.this, "Please select at least one update option.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (updateById.isSelected() && id.isEmpty()) {
                JOptionPane.showMessageDialog(UpdateStudentDialog.this, "ID must be provided.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (updateByName.isSelected() && name.isEmpty()) {
                JOptionPane.showMessageDialog(UpdateStudentDialog.this, "Name must be provided.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Check if the student exists
            String checkStudentQuery = "SELECT id FROM students WHERE ";
            if (updateById.isSelected()) {
                checkStudentQuery += "id = ?";
            }
            if (updateByName.isSelected()) {
                if (updateById.isSelected()) checkStudentQuery += " OR ";
                checkStudentQuery += "name = ?";
            }

            try (Connection conn = DatabaseUtil.getConnection();
                 PreparedStatement checkStudentStmt = conn.prepareStatement(checkStudentQuery)) {

                int paramIndex = 1;
                if (updateById.isSelected()) {
                    checkStudentStmt.setString(paramIndex++, id);
                }
                if (updateByName.isSelected()) {
                    checkStudentStmt.setString(paramIndex++, name);
                }

                ResultSet rsStudent = checkStudentStmt.executeQuery();
                if (!rsStudent.next()) {
                    JOptionPane.showMessageDialog(UpdateStudentDialog.this, "Student not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String studentId = rsStudent.getString("id");

                // Check if the student is enrolled in a course
                String checkEnrollmentQuery = "SELECT course_title FROM enrollments WHERE student_id = ?";
                try (PreparedStatement checkEnrollmentStmt = conn.prepareStatement(checkEnrollmentQuery)) {
                    checkEnrollmentStmt.setString(1, studentId);
                    ResultSet rsEnrollment = checkEnrollmentStmt.executeQuery();
                    if (!rsEnrollment.next()) {
                        JOptionPane.showMessageDialog(UpdateStudentDialog.this, "No enrollment found for this student.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                // Update the course in the enrollments table
                if (!newCourse.isEmpty()) {
                    String updateCourseQuery = "UPDATE enrollments SET course_title = ? WHERE student_id = ?";
                    try (PreparedStatement updateCourseStmt = conn.prepareStatement(updateCourseQuery)) {
                        updateCourseStmt.setString(1, newCourse);
                        updateCourseStmt.setString(2, studentId);
                        int rowsAffected = updateCourseStmt.executeUpdate();
                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(UpdateStudentDialog.this, "Student course updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(UpdateStudentDialog.this, "No course updated, please check the details.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }

                // Update the student's name if the updateByName checkbox is selected
                if (updateByName.isSelected()) {
                    String updateNameQuery = "UPDATE students SET name = ? WHERE id = ?";
                    try (PreparedStatement updateNameStmt = conn.prepareStatement(updateNameQuery)) {
                        updateNameStmt.setString(1, name);
                        updateNameStmt.setString(2, studentId);
                        int rowsAffected = updateNameStmt.executeUpdate();
                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(UpdateStudentDialog.this, "Student name updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(UpdateStudentDialog.this, "No name updated, please check the details.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }

                dispose(); // Close the dialog after updates
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(UpdateStudentDialog.this, "Error updating student: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


}

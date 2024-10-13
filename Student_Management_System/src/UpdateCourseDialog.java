import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateCourseDialog extends JDialog {
    private JTextField courseTitleField;
    private JTextField newCourseField;

    public UpdateCourseDialog(Frame parent) {
        super(parent, "Update Course", true);
        setLayout(new GridLayout(3, 2));
        setSize(400, 200);
        setLocationRelativeTo(parent);

        courseTitleField = new JTextField();
        newCourseField = new JTextField();

        add(new JLabel("Existing Course Title:"));
        add(courseTitleField);
        add(new JLabel("New Course Title:"));
        add(newCourseField);

        JButton updateButton = new JButton("Update Course");
        updateButton.addActionListener(new UpdateCourseAction());
        add(updateButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        add(cancelButton);
    }

    private class UpdateCourseAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String existingCourseTitle = courseTitleField.getText().trim();
            String newCourseTitle = newCourseField.getText().trim();

            if (existingCourseTitle.isEmpty() || newCourseTitle.isEmpty()) {
                JOptionPane.showMessageDialog(UpdateCourseDialog.this, "All fields must be filled out.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String checkCourseQuery = "SELECT * FROM courses WHERE title = ?";
            String updateCourseQuery = "UPDATE courses SET title = ? WHERE title = ?";

            try (Connection conn = DatabaseUtil.getConnection();
                 PreparedStatement checkCourseStmt = conn.prepareStatement(checkCourseQuery);
                 PreparedStatement updateCourseStmt = conn.prepareStatement(updateCourseQuery)) {

                checkCourseStmt.setString(1, existingCourseTitle);
                ResultSet rsCourse = checkCourseStmt.executeQuery();

                if (!rsCourse.next()) {
                    JOptionPane.showMessageDialog(UpdateCourseDialog.this, "Course does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Execute update
                updateCourseStmt.setString(1, newCourseTitle);
                updateCourseStmt.setString(2, existingCourseTitle);
                updateCourseStmt.executeUpdate();

                JOptionPane.showMessageDialog(UpdateCourseDialog.this, "Course updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(UpdateCourseDialog.this, "Error updating course: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

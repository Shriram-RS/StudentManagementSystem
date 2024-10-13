import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ViewCourseDetailsDialog extends JDialog {
    private DefaultTableModel model;

    public ViewCourseDetailsDialog(JFrame parent) {
        super(parent, "View Course Details", true);
        initializeTable();
        loadCourseDetails();
        setSize(600, 400);
        setLocationRelativeTo(parent);
    }

    private void initializeTable() {
        // Initialize the table model
        model = new DefaultTableModel(new String[]{"Course Title", "Student ID", "Student Name"}, 0);
        JTable table = new JTable(model);

        // Enable column sorting
        table.setAutoCreateRowSorter(true);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadCourseDetails() {
        String query = "SELECT e.course_title, s.id, s.name " +
                "FROM enrollments e " +
                "JOIN courses c ON e.course_title = c.title " +
                "JOIN students s ON e.student_id = s.id " +
                "ORDER BY e.course_title";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            model.setRowCount(0);

            while (rs.next()) {
                String courseTitle = rs.getString("course_title");
                String studentId = rs.getString("id");
                String studentName = rs.getString("name");
                model.addRow(new Object[]{courseTitle, studentId, studentName});
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading course details: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

package gui;

import data.DataManager;
import model.Course;
import model.Student;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * CourseEnrollmentPanel - Provides functionality for enrolling students in courses.
 * Features course selection, eligible student display, and enrollment management.
 */
public class CourseEnrollmentPanel extends JPanel implements ActionListener {
    
    private MainFrame parentFrame;
    private DataManager dataManager;
    
    // Course selection
    private JComboBox<Course> courseComboBox;
    private JLabel courseInfoLabel;
    
    // Tables
    private JTable eligibleStudentsTable;
    private JTable enrolledStudentsTable;
    private DefaultTableModel eligibleTableModel;
    private DefaultTableModel enrolledTableModel;
    
    // Buttons
    private JButton enrollButton;
    private JButton removeButton;
    private JButton refreshButton;
    
    // Currently selected course and students
    private Course selectedCourse;
    private Student selectedEligibleStudent;
    private Student selectedEnrolledStudent;
    
    /**
     * Constructor
     * @param parentFrame Reference to the main application frame
     */
    public CourseEnrollmentPanel(MainFrame parentFrame) {
        this.parentFrame = parentFrame;
        this.dataManager = DataManager.getInstance();
        
        initializePanel();
        createComponents();
        layoutComponents();
        addEventListeners();
        refreshData();
    }
    
    /**
     * Initialize panel properties
     */
    private void initializePanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(15, 15, 15, 15));
        setBackground(new Color(248, 249, 250));
    }
    
    /**
     * Create all GUI components
     */
    private void createComponents() {
        // Course combo box
        courseComboBox = new JComboBox<>();
        courseComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        courseComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, 
                    int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Course) {
                    Course course = (Course) value;
                    setText(course.getCourseCode() + " - " + course.getCourseName());
                }
                return this;
            }
        });
        
        // Course info label
        courseInfoLabel = new JLabel("Select a course to view details");
        courseInfoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        courseInfoLabel.setForeground(Color.DARK_GRAY);
        
        // Eligible students table
        String[] eligibleColumns = {"Student ID", "Name", "Major"};
        eligibleTableModel = new DefaultTableModel(eligibleColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        eligibleStudentsTable = createStyledTable(eligibleTableModel);
        
        // Enrolled students table
        String[] enrolledColumns = {"Student ID", "Name", "Major"};
        enrolledTableModel = new DefaultTableModel(enrolledColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        enrolledStudentsTable = createStyledTable(enrolledTableModel);
        
        // Buttons
        enrollButton = createButton("Enroll Student →", new Color(60, 179, 113));
        removeButton = createButton("← Remove Student", new Color(220, 20, 60));
        refreshButton = createButton("Refresh", new Color(60, 180, 110));
        
        enrollButton.setEnabled(false);
        removeButton.setEnabled(false);
    }
    
    /**
     * Create a styled table
     */
    private JTable createStyledTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setRowHeight(28);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(70, 130, 180));
        // header text should be dark for readability
        table.getTableHeader().setForeground(Color.BLACK);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setGridColor(new Color(220, 220, 220));
        return table;
    }
    
    /**
     * Create a styled button
     */
    private JButton createButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBackground(bgColor);
        button.setForeground(isDark(bgColor) ? Color.WHITE : Color.BLACK);
        button.setOpaque(true);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addActionListener(this);
        button.addPropertyChangeListener("enabled", evt -> {
            boolean enabled = (Boolean) evt.getNewValue();
            button.setForeground(enabled ? (isDark(bgColor) ? Color.WHITE : Color.BLACK) : Color.DARK_GRAY);
        });
        return button;
    }

    /**
     * Utility to determine if a color is dark (for contrast decisions)
     */
    private boolean isDark(Color c) {
        double luminance = (0.299 * c.getRed() + 0.587 * c.getGreen() + 0.114 * c.getBlue()) / 255;
        return luminance < 0.5;
    }
    
    /**
     * Layout all components in the panel
     */
    private void layoutComponents() {
        // Header
        JLabel headerLabel = new JLabel("Course Enrollment");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerLabel.setForeground(new Color(50, 50, 50));
        
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(248, 249, 250));
        headerPanel.setBorder(new EmptyBorder(0, 0, 15, 0));
        headerPanel.add(headerLabel, BorderLayout.WEST);
        headerPanel.add(refreshButton, BorderLayout.EAST);
        
        // Course selection panel
        JPanel coursePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        coursePanel.setBackground(Color.WHITE);
        coursePanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            new EmptyBorder(10, 15, 10, 15)
        ));
        
        JLabel courseLabel = new JLabel("Select Course:");
        courseLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        
        courseComboBox.setPreferredSize(new Dimension(350, 30));
        
        coursePanel.add(courseLabel);
        coursePanel.add(courseComboBox);
        coursePanel.add(courseInfoLabel);
        
        // Tables panel
        JPanel tablesPanel = new JPanel(new GridLayout(1, 3, 15, 0));
        tablesPanel.setBackground(new Color(248, 249, 250));
        
        // Eligible students panel
        JPanel eligiblePanel = createTablePanel(
            "Eligible Students",
            eligibleStudentsTable,
            "Students who can be enrolled in this course"
        );
        
        // Action buttons panel
        JPanel actionPanel = new JPanel(new GridBagLayout());
        actionPanel.setBackground(new Color(248, 249, 250));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        actionPanel.add(enrollButton, gbc);
        
        gbc.gridy = 1;
        actionPanel.add(removeButton, gbc);
        
        // Enrolled students panel
        JPanel enrolledPanel = createTablePanel(
            "Enrolled Students",
            enrolledStudentsTable,
            "Students currently enrolled in this course"
        );
        
        tablesPanel.add(eligiblePanel);
        tablesPanel.add(actionPanel);
        tablesPanel.add(enrolledPanel);
        
        // Combine all panels
        JPanel centerPanel = new JPanel(new BorderLayout(0, 15));
        centerPanel.setBackground(new Color(248, 249, 250));
        centerPanel.add(coursePanel, BorderLayout.NORTH);
        centerPanel.add(tablesPanel, BorderLayout.CENTER);
        
        add(headerPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
    }
    
    /**
     * Create a table panel with title and scroll pane
     */
    private JPanel createTablePanel(String title, JTable table, String tooltip) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(70, 130, 180)),
                title,
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 14),
                new Color(60, 170, 110)
            ),
            new EmptyBorder(10, 10, 10, 10)
        ));
        panel.setToolTipText(tooltip);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Add event listeners to components
     */
    private void addEventListeners() {
        // Course selection listener
        courseComboBox.addActionListener(e -> {
            selectedCourse = (Course) courseComboBox.getSelectedItem();
            updateCourseDisplay();
        });
        
        // Eligible students table selection
        eligibleStudentsTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = eligibleStudentsTable.getSelectedRow();
                if (row >= 0 && selectedCourse != null) {
                    String studentId = (String) eligibleTableModel.getValueAt(row, 0);
                    selectedEligibleStudent = dataManager.getStudent(studentId);
                    enrollButton.setEnabled(selectedCourse.hasAvailableSpace());
                } else {
                    selectedEligibleStudent = null;
                    enrollButton.setEnabled(false);
                }
            }
        });
        
        // Enrolled students table selection
        enrolledStudentsTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = enrolledStudentsTable.getSelectedRow();
                if (row >= 0) {
                    String studentId = (String) enrolledTableModel.getValueAt(row, 0);
                    selectedEnrolledStudent = dataManager.getStudent(studentId);
                    removeButton.setEnabled(true);
                } else {
                    selectedEnrolledStudent = null;
                    removeButton.setEnabled(false);
                }
            }
        });
    }
    
    /**
     * Handle button actions
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        
        if (source == enrollButton) {
            enrollStudent();
        } else if (source == removeButton) {
            removeStudent();
        } else if (source == refreshButton) {
            refreshData();
        }
    }
    
    /**
     * Enroll a student in the selected course
     */
    private void enrollStudent() {
        if (selectedCourse == null || selectedEligibleStudent == null) {
            showError("Please select a course and a student to enroll.");
            return;
        }
        
        if (!selectedCourse.hasAvailableSpace()) {
            showError("This course is full. Cannot enroll more students.");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Enroll " + selectedEligibleStudent.getFullName() + " in " + 
            selectedCourse.getCourseCode() + " - " + selectedCourse.getCourseName() + "?",
            "Confirm Enrollment",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = dataManager.enrollStudentInCourse(
                selectedEligibleStudent.getStudentId(),
                selectedCourse.getCourseId()
            );
            
            if (success) {
                showSuccess("Student enrolled successfully!");
                updateCourseDisplay();
                parentFrame.updateStatusBar();
                selectedEligibleStudent = null;
                enrollButton.setEnabled(false);
            } else {
                showError("Failed to enroll student. Student may already be enrolled.");
            }
        }
    }
    
    /**
     * Remove a student from the selected course
     */
    private void removeStudent() {
        if (selectedCourse == null || selectedEnrolledStudent == null) {
            showError("Please select a course and a student to remove.");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Remove " + selectedEnrolledStudent.getFullName() + " from " + 
            selectedCourse.getCourseCode() + " - " + selectedCourse.getCourseName() + "?\n" +
            "This will also delete all grade records for this enrollment.",
            "Confirm Removal",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = dataManager.removeStudentFromCourse(
                selectedEnrolledStudent.getStudentId(),
                selectedCourse.getCourseId()
            );
            
            if (success) {
                showSuccess("Student removed from course successfully!");
                updateCourseDisplay();
                parentFrame.updateStatusBar();
                selectedEnrolledStudent = null;
                removeButton.setEnabled(false);
            } else {
                showError("Failed to remove student from course.");
            }
        }
    }
    
    /**
     * Update the display for the selected course
     */
    private void updateCourseDisplay() {
        if (selectedCourse == null) {
            courseInfoLabel.setText("Select a course to view details");
            eligibleTableModel.setRowCount(0);
            enrolledTableModel.setRowCount(0);
            enrollButton.setEnabled(false);
            removeButton.setEnabled(false);
            return;
        }
        
        // Update course info
        String info = String.format("| Department: %s | Credits: %d | Enrolled: %d/%d | Instructor: %s |",
            selectedCourse.getDepartment(),
            selectedCourse.getCredits(),
            selectedCourse.getEnrollmentCount(),
            selectedCourse.getMaxCapacity(),
            selectedCourse.getInstructor()
        );
        courseInfoLabel.setText(info);
        
        // Update eligible students table
        eligibleTableModel.setRowCount(0);
        java.util.List<Student> eligibleStudents = 
            dataManager.getEligibleStudentsForCourse(selectedCourse.getCourseId());
        
        for (Student student : eligibleStudents) {
            eligibleTableModel.addRow(new Object[]{
                student.getStudentId(),
                student.getFullName(),
                student.getMajor()
            });
        }
        
        // Update enrolled students table
        enrolledTableModel.setRowCount(0);
        // The enrolled students are retrieved directly from the selectedCourse object
        for (String studentId : selectedCourse.getEnrolledStudents()) {
            Student student = dataManager.getStudent(studentId);
            if (student != null) {
                enrolledTableModel.addRow(new Object[]{
                    student.getStudentId(),
                    student.getFullName(),
                    student.getMajor()
                });
            }
        }
        
        // Update status
        parentFrame.setStatusMessage(String.format(
            "Course: %s | Eligible: %d students | Enrolled: %d/%d",
            selectedCourse.getCourseCode(),
            eligibleStudents.size(),
            selectedCourse.getEnrollmentCount(),
            selectedCourse.getMaxCapacity()
        ));
        
        // Enable/disable enroll button based on capacity
        enrollButton.setEnabled(selectedCourse.hasAvailableSpace() && 
                               eligibleStudentsTable.getSelectedRow() >= 0);
    }
    
    /**
     * Refresh all data in the panel
     */
    public void refreshData() {
        // Save current selection
        Course currentSelection = (Course) courseComboBox.getSelectedItem();
        
        // Refresh course combo box
        courseComboBox.removeAllItems();
        java.util.List<Course> courses = dataManager.getAllCourses();
        
        for (Course course : courses) {
            courseComboBox.addItem(course);
        }
        
        // Restore selection if possible
        if (currentSelection != null) {
            for (int i = 0; i < courseComboBox.getItemCount(); i++) {
                if (courseComboBox.getItemAt(i).getCourseId().equals(currentSelection.getCourseId())) {
                    courseComboBox.setSelectedIndex(i);
                    break;
                }
            }
        }
        
        // Update display
        if (courseComboBox.getItemCount() > 0 && courseComboBox.getSelectedItem() != null) {
            selectedCourse = (Course) courseComboBox.getSelectedItem();
            updateCourseDisplay();
        }
        
        parentFrame.setStatusMessage("Enrollment data refreshed");
    }
    
    /**
     * Show error message dialog
     * @param message The error message
     */
    private void showError(String message) {
        JOptionPane.showMessageDialog(
            this,
            message,
            "Error",
            JOptionPane.ERROR_MESSAGE
        );
    }
    
    /**
     * Show success message dialog
     * @param message The success message
     */
    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(
            this,
            message,
            "Success",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
}

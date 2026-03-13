package gui;

import data.DataManager;
import model.Student;
import util.ValidationException;
import util.Validator;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * StudentManagementPanel - Provides comprehensive functionality for managing student records.
 * Includes features to add, update, delete, and view student information.
 */
public class StudentManagementPanel extends JPanel implements ActionListener {
    
    private MainFrame parentFrame;
    private DataManager dataManager;
    
    // Table components
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    
    // Form components
    private JTextField studentIdField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField addressField;
    private JTextField dobField;
    private JTextField majorField;
    
    // Buttons
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton clearButton;
    private JButton searchButton;
    private JButton refreshButton;
    
    // Currently selected student
    private Student selectedStudent;
    
    /**
     * Constructor
     * @param parentFrame Reference to the main application frame
     */
    public StudentManagementPanel(MainFrame parentFrame) {
        this.parentFrame = parentFrame;
        this.dataManager = DataManager.getInstance();
        
        initializePanel();
        createComponents();
        layoutComponents();
        addEventListeners();
        refreshTable();
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
        // Table setup
        String[] columns = {"Student ID", "First Name", "Last Name", "Email", 
                           "Phone", "Major", "Enrolled Courses"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        studentTable = new JTable(tableModel);
        studentTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        studentTable.setRowHeight(28);
        studentTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        studentTable.getTableHeader().setBackground(new Color(70, 130, 180));
        studentTable.getTableHeader().setForeground(Color.BLACK);
        studentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        studentTable.setGridColor(new Color(220, 220, 220));
        
        // Search field
        searchField = new JTextField(20);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        // Form fields
        studentIdField = createFormField();
        // allow administrators to enter a custom ID; if left blank an ID will
        // be generated when the student is added.  Previously this field was
        // read-only which prevented any manual input.
        studentIdField.setEditable(true);
        studentIdField.setBackground(Color.WHITE);
        
        firstNameField = createFormField();
        lastNameField = createFormField();
        emailField = createFormField();
        phoneField = createFormField();
        addressField = createFormField();
        dobField = createFormField();
        majorField = createFormField();
        
        // Buttons
        addButton = createButton("Add Student", new Color(60, 179, 113), this);
        updateButton = createButton("Update Student", new Color(70, 130, 180), this);
        deleteButton = createButton("Delete Student", new Color(220, 20, 60), this);
        clearButton = createButton("Clear Form", new Color(128, 128, 128), this);
        searchButton = createButton("Search", new Color(60, 170, 110), this);
        refreshButton = createButton("Refresh", new Color(60, 170, 110), this);
        
        updateButton.setEnabled(false);
        deleteButton.setEnabled(false);
    }
    
    /**
     * Create a styled form text field
     */
    private JTextField createFormField() {
        JTextField field = new JTextField(20);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        return field;
    }
    
    /**
     * Create a styled button
     */
    private JButton createButton(String text, Color bgColor, ActionListener listener) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBackground(bgColor);
        button.setForeground(isDark(bgColor) ? Color.WHITE : Color.BLACK);
        button.setOpaque(true);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addActionListener(listener);
        button.addPropertyChangeListener("enabled", evt -> {
            boolean enabled = (Boolean) evt.getNewValue();
            button.setForeground(enabled ? (isDark(bgColor) ? Color.WHITE : Color.BLACK) : Color.DARK_GRAY);
        });
        return button;
    }
    
    /**
     * Layout all components in the panel
     */
    private void layoutComponents() {
        // Header
        JLabel headerLabel = new JLabel("Student Management");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerLabel.setForeground(new Color(50, 50, 50));
        headerLabel.setBorder(new EmptyBorder(0, 0, 15, 0));
        
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(248, 249, 250));
        headerPanel.add(headerLabel, BorderLayout.WEST);
        
        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(new Color(248, 249, 250));
        searchPanel.add(new JLabel("Search: "));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(refreshButton);
        
        headerPanel.add(searchPanel, BorderLayout.EAST);
        
        // Table panel
        JScrollPane tableScroll = new JScrollPane(studentTable);
        tableScroll.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(new Color(248, 249, 250));
        tablePanel.setBorder(new TitledBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180)),
            "Student List",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 14),
            new Color(70, 130, 180)
        ));
        tablePanel.add(tableScroll, BorderLayout.CENTER);
        
        // Form panel
        JPanel formPanel = createFormPanel();
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(248, 249, 250));
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        
        // Right panel (form + buttons)
        JPanel rightPanel = new JPanel(new BorderLayout(0, 10));
        rightPanel.setBackground(new Color(248, 249, 250));
        rightPanel.add(formPanel, BorderLayout.CENTER);
        rightPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tablePanel, rightPanel);
        splitPane.setResizeWeight(0.6);
        splitPane.setDividerLocation(0.6);
        splitPane.setBackground(new Color(248, 249, 250));
        
        // Add to main panel
        add(headerPanel, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);
    }
    
    /**
     * Create the student form panel
     */
    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(70, 130, 180)),
                "Student Information",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 14),
                new Color(70, 130, 180)
            ),
            new EmptyBorder(15, 15, 15, 15)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Row 0 - Student ID (optional)
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(createLabel("Student ID (optional):"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        panel.add(studentIdField, gbc);
        
        // Row 1 - First Name
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        panel.add(createLabel("First Name:*"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(firstNameField, gbc);
        
        // Row 2 - Last Name
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE;
        panel.add(createLabel("Last Name:*"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(lastNameField, gbc);
        
        // Row 3 - Email
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE;
        panel.add(createLabel("Email:*"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(emailField, gbc);
        
        // Row 4 - Phone
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE;
        panel.add(createLabel("Phone:*"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(phoneField, gbc);
        
        // Row 5 - Address
        gbc.gridx = 0; gbc.gridy = 5; gbc.fill = GridBagConstraints.NONE;
        panel.add(createLabel("Address:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(addressField, gbc);
        
        // Row 6 - Date of Birth
        gbc.gridx = 0; gbc.gridy = 6; gbc.fill = GridBagConstraints.NONE;
        panel.add(createLabel("Date of Birth:*"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(dobField, gbc);
        
        // Row 7 - Major
        gbc.gridx = 0; gbc.gridy = 7; gbc.fill = GridBagConstraints.NONE;
        panel.add(createLabel("Major:*"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(majorField, gbc);
        
        // Required fields note
        gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 8, 0, 8);
        JLabel noteLabel = new JLabel("* Required fields");
        noteLabel.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        noteLabel.setForeground(Color.GRAY);
        panel.add(noteLabel, gbc);
        
        return panel;
    }
    
    /**
     * Utility to determine if a color is dark (for contrast decisions)
     */
    private boolean isDark(Color c) {
        // standard luminance formula
        double luminance = (0.299 * c.getRed() + 0.587 * c.getGreen() + 0.114 * c.getBlue()) / 255;
        return luminance < 0.5;
    }

    /**
     * Create a form label
     */
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        return label;
    }
    
    /**
     * Add event listeners to components
     */
    private void addEventListeners() {
        // Table selection listener
        studentTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = studentTable.getSelectedRow();
                    if (selectedRow >= 0) {
                        String studentId = (String) tableModel.getValueAt(selectedRow, 0);
                        loadStudentIntoForm(studentId);
                    }
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
        
        if (source == addButton) {
            addStudent();
        } else if (source == updateButton) {
            updateStudent();
        } else if (source == deleteButton) {
            deleteStudent();
        } else if (source == clearButton) {
            clearForm();
        } else if (source == searchButton) {
            searchStudents();
        } else if (source == refreshButton) {
            refreshTable();
            clearForm();
        }
    }
    
    /**
     * Add a new student
     */
    private void addStudent() {
        try {
            // Validate form inputs (ID optional)
            validateForm();
            
            // Create new student
            Student student = new Student();
            String idText = studentIdField.getText().trim();
            if (!idText.isEmpty()) {
                student.setStudentId(idText);
            } else {
                student.setStudentId(dataManager.generateStudentId());
            }
            student.setFirstName(firstNameField.getText().trim());
            student.setLastName(lastNameField.getText().trim());
            student.setEmail(emailField.getText().trim());
            student.setPhone(phoneField.getText().trim());
            student.setAddress(addressField.getText().trim());
            student.setDateOfBirth(dobField.getText().trim());
            student.setMajor(majorField.getText().trim());
            
            // Add to data manager
            if (dataManager.addStudent(student)) {
                showSuccess("Student added successfully!\nStudent ID: " + student.getStudentId());
                refreshTable();
                clearForm();
                parentFrame.updateStatusBar();
            } else {
                showError("Failed to add student. Student ID already exists.");
            }
            
        } catch (ValidationException ex) {
            showError(ex.getMessage());
        }
    }
    
    /**
     * Update existing student
     */
    private void updateStudent() {
        if (selectedStudent == null) {
            showError("Please select a student to update.");
            return;
        }
        
        try {
            // Validate form inputs
            validateForm();
            
            // Update student information
            selectedStudent.setFirstName(firstNameField.getText().trim());
            selectedStudent.setLastName(lastNameField.getText().trim());
            selectedStudent.setEmail(emailField.getText().trim());
            selectedStudent.setPhone(phoneField.getText().trim());
            selectedStudent.setAddress(addressField.getText().trim());
            selectedStudent.setDateOfBirth(dobField.getText().trim());
            selectedStudent.setMajor(majorField.getText().trim());
            
            // Update in data manager
            if (dataManager.updateStudent(selectedStudent)) {
                showSuccess("Student updated successfully!");
                refreshTable();
                clearForm();
            } else {
                showError("Failed to update student.");
            }
            
        } catch (ValidationException ex) {
            showError(ex.getMessage());
        }
    }
    
    /**
     * Delete selected student
     */
    private void deleteStudent() {
        if (selectedStudent == null) {
            showError("Please select a student to delete.");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to delete student " + selectedStudent.getFullName() + "?\n" +
            "This will also remove all enrollments and grades for this student.",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            if (dataManager.deleteStudent(selectedStudent.getStudentId())) {
                showSuccess("Student deleted successfully!");
                refreshTable();
                clearForm();
                parentFrame.updateStatusBar();
            } else {
                showError("Failed to delete student.");
            }
        }
    }
    
    /**
     * Search students by name
     */
    private void searchStudents() {
        String searchTerm = searchField.getText().trim();
        if (searchTerm.isEmpty()) {
            refreshTable();
            return;
        }
        
        tableModel.setRowCount(0);
        java.util.List<Student> results = dataManager.searchStudentsByName(searchTerm);
        
        for (Student student : results) {
            addStudentToTable(student);
        }
        
        parentFrame.setStatusMessage("Found " + results.size() + " student(s) matching '" + searchTerm + "'");
    }
    
    /**
     * Validate all form fields
     * @throws ValidationException if any validation fails
     */
    private void validateForm() throws ValidationException {
        String idText = studentIdField.getText().trim();
        if (!idText.isEmpty()) {
            Validator.validateNotEmpty(idText, "Student ID");
            // optionally additional format rules could be added here
        }
        Validator.validateName(firstNameField.getText().trim(), "First name");
        Validator.validateName(lastNameField.getText().trim(), "Last name");
        Validator.validateEmail(emailField.getText().trim());
        Validator.validatePhone(phoneField.getText().trim());
        Validator.validateDate(dobField.getText().trim());
        Validator.validateNotEmpty(majorField.getText().trim(), "Major");
    }
    
    /**
     * Load student data into form
     * @param studentId The student ID to load
     */
    private void loadStudentIntoForm(String studentId) {
        selectedStudent = dataManager.getStudent(studentId);
        if (selectedStudent != null) {
            studentIdField.setText(selectedStudent.getStudentId());
            firstNameField.setText(selectedStudent.getFirstName());
            lastNameField.setText(selectedStudent.getLastName());
            emailField.setText(selectedStudent.getEmail());
            phoneField.setText(selectedStudent.getPhone());
            addressField.setText(selectedStudent.getAddress());
            dobField.setText(selectedStudent.getDateOfBirth());
            majorField.setText(selectedStudent.getMajor());
            
            updateButton.setEnabled(true);
            deleteButton.setEnabled(true);
            addButton.setEnabled(false);
        }
    }
    
    /**
     * Clear the form fields
     */
    private void clearForm() {
        studentIdField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        addressField.setText("");
        dobField.setText("");
        majorField.setText("");
        searchField.setText("");
        
        selectedStudent = null;
        studentTable.clearSelection();
        
        updateButton.setEnabled(false);
        deleteButton.setEnabled(false);
        addButton.setEnabled(true);
        
        parentFrame.setStatusMessage("Ready");
    }
    
    /**
     * Refresh the student table with current data
     */
    public void refreshTable() {
        tableModel.setRowCount(0);
        java.util.List<Student> allStudents = dataManager.getAllStudents();
        
        for (Student student : allStudents) {
            addStudentToTable(student);
        }
        
        parentFrame.setStatusMessage("Loaded " + allStudents.size() + " students");
    }
    
    /**
     * Add a student row to the table
     * @param student The student to add
     */
    private void addStudentToTable(Student student) {
        tableModel.addRow(new Object[]{
            student.getStudentId(),
            student.getFirstName(),
            student.getLastName(),
            student.getEmail(),
            student.getPhone(),
            student.getMajor(),
            student.getEnrolledCourses().size()
        });
    }
    
    /**
     * Refresh data - called when panel is shown
     */
    public void refreshData() {
        refreshTable();
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

package gui;

import data.DataManager;
import model.Course;
import model.Grade;
import model.Student;
import util.ValidationException;
import util.Validator;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * GradeManagementPanel - Provides comprehensive functionality for managing student grades.
 * Features student selection, course grade viewing, and grade assignment with score breakdown.
 */
public class GradeManagementPanel extends JPanel implements ActionListener {
    
    private MainFrame parentFrame;
    private DataManager dataManager;
    
    // Student selection
    private JComboBox<Student> studentComboBox;
    private JLabel studentInfoLabel;
    
    // Tables
    private JTable gradesTable;
    private DefaultTableModel gradesTableModel;
    
    // Grade form components
    private JTextField courseField;
    private JSlider assignmentSlider;
    private JSlider midtermSlider;
    private JSlider finalSlider;
    private JLabel assignmentValueLabel;
    private JLabel midtermValueLabel;
    private JLabel finalValueLabel;
    private JLabel totalScoreLabel;
    private JLabel letterGradeLabel;
    private JTextArea commentsArea;
    
    // Buttons
    private JButton saveButton;
    private JButton clearButton;
    private JButton refreshButton;
    
    // Currently selected items
    private Student selectedStudent;
    private Grade selectedGrade;
    
    /**
     * Constructor
     * @param parentFrame Reference to the main application frame
     */
    public GradeManagementPanel(MainFrame parentFrame) {
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
        // Student combo box
        studentComboBox = new JComboBox<>();
        studentComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        studentComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, 
                    int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Student) {
                    Student student = (Student) value;
                    setText(student.getStudentId() + " - " + student.getFullName());
                }
                return this;
            }
        });
        
        // Student info label
        studentInfoLabel = new JLabel("Select a student to view grades");
        studentInfoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        studentInfoLabel.setForeground(Color.DARK_GRAY);
        
        // Grades table
        String[] gradesColumns = {"Course", "Assignments (30%)", "Midterm (30%)", 
                                   "Final (40%)", "Total", "Grade"};
        gradesTableModel = new DefaultTableModel(gradesColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        gradesTable = new JTable(gradesTableModel);
        gradesTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        gradesTable.setRowHeight(28);
        gradesTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        gradesTable.getTableHeader().setBackground(new Color(70, 130, 180));
        // make header text dark for readability
        gradesTable.getTableHeader().setForeground(Color.BLACK);
        gradesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        gradesTable.setGridColor(new Color(220, 220, 220));
        
        // Custom cell renderer for grades
        gradesTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if (column == 5 && value != null) { // Grade column
                    String grade = value.toString();
                    if (grade.startsWith("A")) {
                        c.setForeground(new Color(60, 179, 113));
                        setFont(new Font("Segoe UI", Font.BOLD, 12));
                    } else if (grade.startsWith("B")) {
                        c.setForeground(new Color(70, 130, 180));
                        setFont(new Font("Segoe UI", Font.BOLD, 12));
                    } else if (grade.startsWith("C")) {
                        c.setForeground(new Color(255, 165, 0));
                        setFont(new Font("Segoe UI", Font.PLAIN, 12));
                    } else if (grade.startsWith("D")) {
                        c.setForeground(new Color(255, 140, 0));
                        setFont(new Font("Segoe UI", Font.PLAIN, 12));
                    } else if (grade.equals("F")) {
                        c.setForeground(new Color(220, 20, 60));
                        setFont(new Font("Segoe UI", Font.BOLD, 12));
                    } else {
                        c.setForeground(Color.GRAY);
                        setFont(new Font("Segoe UI", Font.PLAIN, 12));
                    }
                } else {
                    c.setForeground(Color.BLACK);
                    setFont(new Font("Segoe UI", Font.PLAIN, 12));
                }
                
                setHorizontalAlignment(CENTER);
                return c;
            }
        });
        
        // Grade form fields
        courseField = new JTextField(20);
        courseField.setEditable(false);
        courseField.setBackground(new Color(240, 240, 240));
        courseField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        // Sliders (0-100)
        assignmentSlider = createScoreSlider();
        midtermSlider = createScoreSlider();
        finalSlider = createScoreSlider();
        
        assignmentValueLabel = createValueLabel();
        midtermValueLabel = createValueLabel();
        finalValueLabel = createValueLabel();
        
        totalScoreLabel = new JLabel("Total: --");
        totalScoreLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        totalScoreLabel.setForeground(new Color(70, 130, 180));
        
        letterGradeLabel = new JLabel("Grade: --");
        letterGradeLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        letterGradeLabel.setForeground(Color.GRAY);
        
        commentsArea = new JTextArea(3, 20);
        commentsArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        commentsArea.setLineWrap(true);
        commentsArea.setWrapStyleWord(true);
        commentsArea.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        
        // Buttons
        saveButton = createButton("Save Grade", new Color(60, 179, 113));
        clearButton = createButton("Clear", new Color(128, 128, 128));
        refreshButton = createButton("Refresh", new Color(60, 180, 110));
        
        saveButton.setEnabled(false);
        clearButton.setEnabled(false);
    }
    
    /**
     * Create a score slider
     */
    private JSlider createScoreSlider() {
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(5);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        return slider;
    }
    
    /**
     * Create a value label for sliders
     */
    private JLabel createValueLabel() {
        JLabel label = new JLabel("0");
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
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
        JLabel headerLabel = new JLabel("Grade Management");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerLabel.setForeground(new Color(50, 50, 50));
        
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(248, 249, 250));
        headerPanel.setBorder(new EmptyBorder(0, 0, 15, 0));
        headerPanel.add(headerLabel, BorderLayout.WEST);
        headerPanel.add(refreshButton, BorderLayout.EAST);
        
        // Student selection panel
        JPanel studentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        studentPanel.setBackground(Color.WHITE);
        studentPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            new EmptyBorder(10, 15, 10, 15)
        ));
        
        JLabel studentLabel = new JLabel("Select Student:");
        studentLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        
        studentComboBox.setPreferredSize(new Dimension(350, 30));
        
        studentPanel.add(studentLabel);
        studentPanel.add(studentComboBox);
        studentPanel.add(studentInfoLabel);
        
        // Grades table panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(70, 130, 180)),
                "Student's Grades",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 14),
                new Color(60, 170, 110)
            ),
            new EmptyBorder(10, 10, 10, 10)
        ));
        
        JScrollPane tableScroll = new JScrollPane(gradesTable);
        tableScroll.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        tablePanel.add(tableScroll, BorderLayout.CENTER);
        
        // Grade editing panel
        JPanel gradeEditPanel = createGradeEditPanel();
        
        // Split pane for table and edit panel
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tablePanel, gradeEditPanel);
        splitPane.setResizeWeight(0.5);
        splitPane.setDividerLocation(300);
        
        // Center panel
        JPanel centerPanel = new JPanel(new BorderLayout(0, 15));
        centerPanel.setBackground(new Color(248, 249, 250));
        centerPanel.add(studentPanel, BorderLayout.NORTH);
        centerPanel.add(splitPane, BorderLayout.CENTER);
        
        add(headerPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
    }
    
    /**
     * Create the grade editing panel
     */
    private JPanel createGradeEditPanel() {
        JPanel panel = new JPanel(new BorderLayout(15, 0));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(70, 130, 180)),
                "Assign/Edit Grade",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 14),
                new Color(70, 130, 180)
            ),
            new EmptyBorder(15, 15, 15, 15)
        ));
        
        // Left side - Course info and sliders
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setBackground(Color.WHITE);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Course field
        gbc.gridx = 0; gbc.gridy = 0;
        leftPanel.add(createLabel("Course:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        leftPanel.add(courseField, gbc);
        
        // Assignment slider
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        leftPanel.add(createLabel("Assignments (30%):"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        JPanel assignmentPanel = createSliderPanel(assignmentSlider, assignmentValueLabel);
        leftPanel.add(assignmentPanel, gbc);
        
        // Midterm slider
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE;
        leftPanel.add(createLabel("Midterm (30%):"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        JPanel midtermPanel = createSliderPanel(midtermSlider, midtermValueLabel);
        leftPanel.add(midtermPanel, gbc);
        
        // Final slider
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE;
        leftPanel.add(createLabel("Final Exam (40%):"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        JPanel finalPanel = createSliderPanel(finalSlider, finalValueLabel);
        leftPanel.add(finalPanel, gbc);
        
        // Comments
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE;
        leftPanel.add(createLabel("Comments:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        JScrollPane commentsScroll = new JScrollPane(commentsArea);
        commentsScroll.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        leftPanel.add(commentsScroll, gbc);
        
        // Right side - Grade display and buttons
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(new Color(248, 249, 250));
        rightPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            new EmptyBorder(20, 20, 20, 20)
        ));
        
        GridBagConstraints rgbc = new GridBagConstraints();
        rgbc.gridx = 0;
        rgbc.insets = new Insets(10, 0, 10, 0);
        rgbc.anchor = GridBagConstraints.CENTER;
        
        rgbc.gridy = 0;
        rightPanel.add(totalScoreLabel, rgbc);
        
        rgbc.gridy = 1;
        rgbc.insets = new Insets(5, 0, 20, 0);
        rightPanel.add(letterGradeLabel, rgbc);
        
        rgbc.gridy = 2;
        rgbc.insets = new Insets(10, 0, 10, 0);
        rgbc.fill = GridBagConstraints.HORIZONTAL;
        rightPanel.add(saveButton, rgbc);
        
        rgbc.gridy = 3;
        rightPanel.add(clearButton, rgbc);
        
        panel.add(leftPanel, BorderLayout.CENTER);
        panel.add(rightPanel, BorderLayout.EAST);
        
        return panel;
    }
    
    /**
     * Create a slider panel with label
     */
    private JPanel createSliderPanel(JSlider slider, JLabel valueLabel) {
        JPanel panel = new JPanel(new BorderLayout(10, 0));
        panel.setBackground(Color.WHITE);
        panel.add(slider, BorderLayout.CENTER);
        panel.add(valueLabel, BorderLayout.EAST);
        valueLabel.setPreferredSize(new Dimension(30, 20));
        return panel;
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
        // Student selection listener
        studentComboBox.addActionListener(e -> {
            selectedStudent = (Student) studentComboBox.getSelectedItem();
            updateStudentDisplay();
        });
        
        // Grades table selection
        gradesTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = gradesTable.getSelectedRow();
                if (row >= 0 && selectedStudent != null) {
                    loadGradeForEditing(row);
                }
            }
        });
        
        // Slider change listeners
        assignmentSlider.addChangeListener(e -> {
            assignmentValueLabel.setText(String.valueOf(assignmentSlider.getValue()));
            if (!assignmentSlider.getValueIsAdjusting()) {
                calculateTotalScore();
            }
        });
        
        midtermSlider.addChangeListener(e -> {
            midtermValueLabel.setText(String.valueOf(midtermSlider.getValue()));
            if (!midtermSlider.getValueIsAdjusting()) {
                calculateTotalScore();
            }
        });
        
        finalSlider.addChangeListener(e -> {
            finalValueLabel.setText(String.valueOf(finalSlider.getValue()));
            if (!finalSlider.getValueIsAdjusting()) {
                calculateTotalScore();
            }
        });
    }
    
    /**
     * Handle button actions
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        
        if (source == saveButton) {
            saveGrade();
        } else if (source == clearButton) {
            clearGradeForm();
        } else if (source == refreshButton) {
            refreshData();
        }
    }
    
    /**
     * Update the display for the selected student
     */
    private void updateStudentDisplay() {
        if (selectedStudent == null) {
            studentInfoLabel.setText("Select a student to view grades");
            gradesTableModel.setRowCount(0);
            clearGradeForm();
            return;
        }
        
        // Update student info
        double gpa = dataManager.calculateGPA(selectedStudent.getStudentId());
        String gpaText = gpa >= 0 ? String.format("%.2f", gpa) : "N/A";
        studentInfoLabel.setText(String.format("| Major: %s | GPA: %s | Enrolled Courses: %d |",
            selectedStudent.getMajor(),
            gpaText,
            selectedStudent.getEnrolledCourses().size()
        ));
        
        // Refresh grades table
        refreshGradesTable();
        
        // Clear grade form
        clearGradeForm();
        
        parentFrame.setStatusMessage("Viewing grades for " + selectedStudent.getFullName());
    }
    
    /**
     * Refresh the grades table
     */
    private void refreshGradesTable() {
        gradesTableModel.setRowCount(0);
        
        if (selectedStudent == null) return;
        
        java.util.List<Grade> grades = dataManager.getStudentGrades(selectedStudent.getStudentId());
        
        for (Grade grade : grades) {
            Course course = dataManager.getCourse(grade.getCourseId());
            String courseName = course != null ? course.getCourseCode() : grade.getCourseId();
            
            gradesTableModel.addRow(new Object[]{
                courseName,
                String.format("%.1f", grade.getAssignmentScore()),
                String.format("%.1f", grade.getMidtermScore()),
                String.format("%.1f", grade.getFinalScore()),
                String.format("%.2f", grade.getTotalScore()),
                grade.getLetterGrade()
            });
        }
    }
    
    /**
     * Load grade data for editing
     * @param row The selected row in the grades table
     */
    private void loadGradeForEditing(int row) {
        String courseCode = (String) gradesTableModel.getValueAt(row, 0);
        
        // Find the grade for this course
        java.util.List<Grade> grades = dataManager.getStudentGrades(selectedStudent.getStudentId());
        for (Grade grade : grades) {
            Course course = dataManager.getCourse(grade.getCourseId());
            if (course != null && course.getCourseCode().equals(courseCode)) {
                selectedGrade = grade;
                break;
            }
        }
        
        if (selectedGrade != null) {
            Course course = dataManager.getCourse(selectedGrade.getCourseId());
            courseField.setText(course != null ? course.getCourseName() : selectedGrade.getCourseId());
            
            assignmentSlider.setValue((int) selectedGrade.getAssignmentScore());
            midtermSlider.setValue((int) selectedGrade.getMidtermScore());
            finalSlider.setValue((int) selectedGrade.getFinalScore());
            
            assignmentValueLabel.setText(String.valueOf((int) selectedGrade.getAssignmentScore()));
            midtermValueLabel.setText(String.valueOf((int) selectedGrade.getMidtermScore()));
            finalValueLabel.setText(String.valueOf((int) selectedGrade.getFinalScore()));
            
            commentsArea.setText(selectedGrade.getComments());
            
            calculateTotalScore();
            
            saveButton.setEnabled(true);
            clearButton.setEnabled(true);
        }
    }
    
    /**
     * Calculate and display the total score
     */
    private void calculateTotalScore() {
        double assignment = assignmentSlider.getValue();
        double midterm = midtermSlider.getValue();
        double finalExam = finalSlider.getValue();
        
        double total = (assignment * 0.30) + (midterm * 0.30) + (finalExam * 0.40);
        String letterGrade = Grade.calculateLetterGrade(total);
        
        totalScoreLabel.setText(String.format("Total: %.2f%%", total));
        letterGradeLabel.setText("Grade: " + letterGrade);
        
        // Color code the grade
        if (letterGrade.startsWith("A")) {
            letterGradeLabel.setForeground(new Color(60, 179, 113));
        } else if (letterGrade.startsWith("B")) {
            letterGradeLabel.setForeground(new Color(70, 130, 180));
        } else if (letterGrade.startsWith("C")) {
            letterGradeLabel.setForeground(new Color(255, 165, 0));
        } else if (letterGrade.startsWith("D")) {
            letterGradeLabel.setForeground(new Color(255, 140, 0));
        } else {
            letterGradeLabel.setForeground(new Color(220, 20, 60));
        }
    }
    
    /**
     * Save the grade to the data manager
     */
    private void saveGrade() {
        if (selectedGrade == null) {
            showError("Please select a grade to edit from the table.");
            return;
        }
        
        try {
            double assignment = assignmentSlider.getValue();
            double midterm = midtermSlider.getValue();
            double finalExam = finalSlider.getValue();
            
            Validator.validateScore(assignment, "Assignment score");
            Validator.validateScore(midterm, "Midterm score");
            Validator.validateScore(finalExam, "Final exam score");
            
            selectedGrade.setAllScores(assignment, midterm, finalExam);
            selectedGrade.setComments(commentsArea.getText().trim());
            
            if (dataManager.updateGrade(selectedGrade)) {
                showSuccess("Grade saved successfully!");
                refreshGradesTable();
                updateStudentDisplay();
            } else {
                showError("Failed to save grade.");
            }
            
        } catch (ValidationException ex) {
            showError(ex.getMessage());
        }
    }
    
    /**
     * Clear the grade form
     */
    private void clearGradeForm() {
        selectedGrade = null;
        courseField.setText("");
        assignmentSlider.setValue(0);
        midtermSlider.setValue(0);
        finalSlider.setValue(0);
        assignmentValueLabel.setText("0");
        midtermValueLabel.setText("0");
        finalValueLabel.setText("0");
        commentsArea.setText("");
        totalScoreLabel.setText("Total: --");
        letterGradeLabel.setText("Grade: --");
        letterGradeLabel.setForeground(Color.GRAY);
        
        saveButton.setEnabled(false);
        clearButton.setEnabled(false);
        
        gradesTable.clearSelection();
    }
    
    /**
     * Refresh all data in the panel
     */
    public void refreshData() {
        // Save current selection
        Student currentSelection = (Student) studentComboBox.getSelectedItem();
        
        // Refresh student combo box
        studentComboBox.removeAllItems();
        java.util.List<Student> students = dataManager.getAllStudents();
        
        for (Student student : students) {
            studentComboBox.addItem(student);
        }
        
        // Restore selection if possible
        if (currentSelection != null) {
            for (int i = 0; i < studentComboBox.getItemCount(); i++) {
                if (studentComboBox.getItemAt(i).getStudentId().equals(currentSelection.getStudentId())) {
                    studentComboBox.setSelectedIndex(i);
                    break;
                }
            }
        }
        
        // Update display
        if (studentComboBox.getItemCount() > 0 && studentComboBox.getSelectedItem() != null) {
            selectedStudent = (Student) studentComboBox.getSelectedItem();
            updateStudentDisplay();
        }
        
        parentFrame.setStatusMessage("Grade data refreshed");
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

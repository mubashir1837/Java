package gui;

import data.DataManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * MainFrame class - The main application window for the Student Management System.
 * Provides navigation between different functionality panels and serves as the
 * primary container for all GUI components.
 */
public class MainFrame extends JFrame {
    
    private CardLayout cardLayout;
    private JPanel contentPanel;
    private JLabel statusLabel;
    private JLabel statsLabel;
    
    // Panel identifiers for CardLayout
    public static final String DASHBOARD_PANEL = "Dashboard";
    public static final String STUDENTS_PANEL = "Students";
    public static final String COURSES_PANEL = "Courses";
    public static final String ENROLLMENT_PANEL = "Enrollment";
    public static final String GRADES_PANEL = "Grades";
    
    // Panel references for updates
    private StudentManagementPanel studentPanel;
    private CourseEnrollmentPanel enrollmentPanel;
    private GradeManagementPanel gradePanel;
    
    /**
     * Constructor - Initialize the main application frame
     */
    public MainFrame() {
        initializeFrame();
        createMenuBar();
        createToolBar();
        createStatusBar();
        createContentArea();
        addEventListeners();
    }
    
    /**
     * Initialize the main frame properties
     */
    private void initializeFrame() {
        setTitle("Student Management System");
        setSize(1200, 800);
        setMinimumSize(new Dimension(900, 600));
        setLocationRelativeTo(null); // Center on screen
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        // Set application icon (using default if custom not available)
        try {
            setIconImage(new ImageIcon(getClass().getResource("/icon.png")).getImage());
        } catch (Exception e) {
            // Use default icon
        }
        
        // Set look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Use default look and feel
        }
    }
    
    /**
     * Create the menu bar with all menu items
     */
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        // File Menu
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic('F');
        
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.setMnemonic('X');
        exitItem.setAccelerator(KeyStroke.getKeyStroke("alt F4"));
        exitItem.addActionListener(e -> confirmExit());
        fileMenu.add(exitItem);
        
        // View Menu
        JMenu viewMenu = new JMenu("View");
        viewMenu.setMnemonic('V');
        
        JMenuItem dashboardItem = new JMenuItem("Dashboard");
        dashboardItem.addActionListener(e -> showPanel(DASHBOARD_PANEL));
        viewMenu.add(dashboardItem);
        
        JMenuItem studentsItem = new JMenuItem("Students");
        studentsItem.addActionListener(e -> showPanel(STUDENTS_PANEL));
        viewMenu.add(studentsItem);
        
        JMenuItem coursesItem = new JMenuItem("Courses");
        coursesItem.addActionListener(e -> showPanel(COURSES_PANEL));
        viewMenu.add(coursesItem);
        
        JMenuItem enrollmentItem = new JMenuItem("Enrollment");
        enrollmentItem.addActionListener(e -> showPanel(ENROLLMENT_PANEL));
        viewMenu.add(enrollmentItem);
        
        JMenuItem gradesItem = new JMenuItem("Grades");
        gradesItem.addActionListener(e -> showPanel(GRADES_PANEL));
        viewMenu.add(gradesItem);
        
        // Help Menu
        JMenu helpMenu = new JMenu("Help");
        helpMenu.setMnemonic('H');
        
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> showAboutDialog());
        helpMenu.add(aboutItem);
        
        menuBar.add(fileMenu);
        menuBar.add(viewMenu);
        menuBar.add(helpMenu);
        
        setJMenuBar(menuBar);
    }
    
    /**
     * Create the tool bar with quick action buttons
     */
    private void createToolBar() {
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        // Dashboard button
        JButton dashboardBtn = createToolBarButton("Dashboard", "View system dashboard");
        dashboardBtn.addActionListener(e -> showPanel(DASHBOARD_PANEL));
        toolBar.add(dashboardBtn);
        
        toolBar.addSeparator();
        
        // Students button
        JButton studentsBtn = createToolBarButton("Students", "Manage student records");
        studentsBtn.addActionListener(e -> showPanel(STUDENTS_PANEL));
        toolBar.add(studentsBtn);
        
        // Courses button
        JButton coursesBtn = createToolBarButton("Courses", "View and manage courses");
        coursesBtn.addActionListener(e -> showPanel(COURSES_PANEL));
        toolBar.add(coursesBtn);
        
        toolBar.addSeparator();
        
        // Enrollment button
        JButton enrollmentBtn = createToolBarButton("Enrollment", "Enroll students in courses");
        enrollmentBtn.addActionListener(e -> showPanel(ENROLLMENT_PANEL));
        toolBar.add(enrollmentBtn);
        
        // Grades button
        JButton gradesBtn = createToolBarButton("Grades", "Manage student grades");
        gradesBtn.addActionListener(e -> showPanel(GRADES_PANEL));
        toolBar.add(gradesBtn);
        
        add(toolBar, BorderLayout.NORTH);
    }
    
    /**
     * Create a styled toolbar button
     */
    private JButton createToolBarButton(String text, String tooltip) {
        JButton button = new JButton(text);
        button.setToolTipText(tooltip);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        button.setBackground(new Color(70, 130, 180));  // dark blue
        button.setForeground(Color.WHITE);              // ensure white text
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        button.setOpaque(true);
        return button;
    }
    
    /**
     * Create the main content area with CardLayout
     */
    private void createContentArea() {
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Create and add panels
        contentPanel.add(createDashboardPanel(), DASHBOARD_PANEL);
        
        studentPanel = new StudentManagementPanel(this);
        contentPanel.add(studentPanel, STUDENTS_PANEL);
        
        contentPanel.add(createCoursesPanel(), COURSES_PANEL);
        
        enrollmentPanel = new CourseEnrollmentPanel(this);
        contentPanel.add(enrollmentPanel, ENROLLMENT_PANEL);
        
        gradePanel = new GradeManagementPanel(this);
        contentPanel.add(gradePanel, GRADES_PANEL);
        
        add(contentPanel, BorderLayout.CENTER);
    }
    
    /**
     * Create the dashboard/welcome panel
     */
    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 248, 255));
        
        // Welcome header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(70, 130, 180));
        headerPanel.setPreferredSize(new Dimension(0, 150));
        headerPanel.setLayout(new GridBagLayout());
        
        JLabel titleLabel = new JLabel("Student Management System");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        
        // Content area
        JPanel contentArea = new JPanel(new GridLayout(2, 3, 20, 20));
        contentArea.setBackground(new Color(240, 248, 255));
        contentArea.setBorder(new EmptyBorder(30, 30, 30, 30));
        
        // Quick stat cards
        contentArea.add(createStatCard("Total Students", 
            String.valueOf(DataManager.getInstance().getStudentCount()), 
            new Color(70, 130, 180)));
        contentArea.add(createStatCard("Total Courses", 
            String.valueOf(DataManager.getInstance().getCourseCount()), 
            new Color(60, 179, 113)));
        contentArea.add(createStatCard("Total Enrollments", 
            String.valueOf(DataManager.getInstance().getEnrollmentCount()), 
            new Color(255, 165, 0)));
        
        // Quick action buttons
        JPanel actionsPanel = new JPanel(new GridLayout(1, 3, 15, 0));
        actionsPanel.setBackground(new Color(240, 248, 255));
        
        JButton addStudentBtn = createActionButton("Add New Student", 
            e -> showPanel(STUDENTS_PANEL));
        addStudentBtn.setForeground(Color.BLACK);

        JButton enrollBtn = createActionButton("Enroll Student", 
            e -> showPanel(ENROLLMENT_PANEL));
        enrollBtn.setForeground(Color.BLACK);

        JButton gradesBtn = createActionButton("Assign Grades", 
            e -> showPanel(GRADES_PANEL));
        gradesBtn.setForeground(Color.BLACK);
        
        actionsPanel.add(addStudentBtn);
        actionsPanel.add(enrollBtn);
        actionsPanel.add(gradesBtn);
        
        contentArea.add(actionsPanel);
        
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(contentArea, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Create a statistic card for the dashboard
     */
    private JPanel createStatCard(String title, String value, Color color) {
        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        card.setLayout(new GridLayout(2, 1));
        
        JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        valueLabel.setForeground(color);
        
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        titleLabel.setForeground(Color.DARK_GRAY);  // ensure dark text on white
        
        card.add(valueLabel);
        card.add(titleLabel);
        
        return card;
    }
    
    /**
     * Create an action button for the dashboard
     */
    private JButton createActionButton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
        button.addActionListener(listener);
        return button;
    }
    
    /**
     * Create the courses panel
     */
    private JPanel createCoursesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        JLabel header = new JLabel("Course Management", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 24));
        header.setBorder(new EmptyBorder(20, 0, 20, 0));
        panel.add(header, BorderLayout.NORTH);
        
        // Course table
        String[] columns = {"Course ID", "Code", "Name", "Department", "Credits", 
                           "Instructor", "Enrolled", "Capacity"};
        JTable courseTable = new JTable(new javax.swing.table.DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        courseTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        courseTable.setRowHeight(25);
        courseTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        refreshCourseTable(courseTable);
        
        JScrollPane scrollPane = new JScrollPane(courseTable);
        scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Refresh button
        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.addActionListener(e -> refreshCourseTable(courseTable));
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(refreshBtn);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    /**
     * Refresh the course table with current data
     */
    private void refreshCourseTable(JTable table) {
        javax.swing.table.DefaultTableModel model = 
            (javax.swing.table.DefaultTableModel) table.getModel();
        model.setRowCount(0);
        
        for (var course : DataManager.getInstance().getAllCourses()) {
            model.addRow(new Object[]{
                course.getCourseId(),
                course.getCourseCode(),
                course.getCourseName(),
                course.getDepartment(),
                course.getCredits(),
                course.getInstructor(),
                course.getEnrollmentCount(),
                course.getMaxCapacity()
            });
        }
    }
    
    /**
     * Create the status bar at the bottom
     */
    private void createStatusBar() {
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBorder(BorderFactory.createLoweredBevelBorder());
        statusPanel.setBackground(new Color(240, 240, 240));
        
        statusLabel = new JLabel(" Ready");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        
        statsLabel = new JLabel("Students: 0 | Courses: 0 | Enrollments: 0  ");
        statsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        
        statusPanel.add(statusLabel, BorderLayout.WEST);
        statusPanel.add(statsLabel, BorderLayout.EAST);
        
        add(statusPanel, BorderLayout.SOUTH);
        updateStatusBar();
    }
    
    /**
     * Update the status bar with current statistics
     */
    public void updateStatusBar() {
        DataManager dm = DataManager.getInstance();
        statsLabel.setText(String.format("Students: %d | Courses: %d | Enrollments: %d  ",
            dm.getStudentCount(), dm.getCourseCount(), dm.getEnrollmentCount()));
    }
    
    /**
     * Add event listeners to the frame
     */
    private void addEventListeners() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                confirmExit();
            }
        });
    }
    
    /**
     * Show a specific panel in the CardLayout
     * @param panelName The name of the panel to show
     */
    public void showPanel(String panelName) {
        cardLayout.show(contentPanel, panelName);
        setStatusMessage("Viewing " + panelName);
        
        // Refresh panels when shown
        switch (panelName) {
            case STUDENTS_PANEL:
                studentPanel.refreshData();
                break;
            case ENROLLMENT_PANEL:
                enrollmentPanel.refreshData();
                break;
            case GRADES_PANEL:
                gradePanel.refreshData();
                break;
        }
    }
    
    /**
     * Set the status bar message
     * @param message The message to display
     */
    public void setStatusMessage(String message) {
        statusLabel.setText(" " + message);
    }
    
    /**
     * Show confirmation dialog before exiting
     */
    private void confirmExit() {
        int result = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to exit the Student Management System?",
            "Confirm Exit",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (result == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
    
    /**
     * Show the about dialog
     */
    private void showAboutDialog() {
        JOptionPane.showMessageDialog(
            this,
            "Student Management System v1.0\n\n" +
            "A comprehensive GUI application for managing student records,\n" +
            "course enrollments, and grades.\n\n" +
            "Developed using Java Swing\n" +
            "© 2024 All Rights Reserved",
            "About",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    /**
     * Main entry point for the application
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        // Set system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Launch the application on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class StudentGradeTrackerGUI extends JFrame {
    private ArrayList<Student> students = new ArrayList<>();
    private JTextField nameField;
    private JTextField gradeField;
    private JTextArea studentListArea;
    private JLabel averageLabel;
    private JLabel highestLabel;
    private JLabel lowestLabel;
    private JButton updateButton;

    public StudentGradeTrackerGUI() {
        // Set up the frame
        setTitle("Student Grade Tracker");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create components
        nameField = new JTextField(15);
        gradeField = new JTextField(5);
        JButton addButton = new JButton("Add Student");
        JButton calculateButton = new JButton("Calculate");
        JButton showListButton = new JButton("Show Student List");
        updateButton = new JButton("Update Details");
        studentListArea = new JTextArea(10, 30);
        studentListArea.setEditable(false);
        averageLabel = new JLabel("Average grade: ");
        highestLabel = new JLabel("Highest grade: ");
        lowestLabel = new JLabel("Lowest grade: ");

        // Set up input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Name:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        inputPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Grade:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        inputPanel.add(gradeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(addButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        inputPanel.add(updateButton, gbc);

        // Set up control panel
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        controlPanel.add(calculateButton);
        controlPanel.add(showListButton);

        // Set up output panel
        JPanel outputPanel = new JPanel();
        outputPanel.setLayout(new GridLayout(3, 1));
        outputPanel.add(averageLabel);
        outputPanel.add(highestLabel);
        outputPanel.add(lowestLabel);

        // Add components to frame
        add(inputPanel, BorderLayout.NORTH);
        add(controlPanel, BorderLayout.SOUTH);
        add(outputPanel, BorderLayout.CENTER);
        add(new JScrollPane(studentListArea), BorderLayout.EAST);

        // Add action listeners
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addStudent();
            }
        });
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateStudent();
            }
        });
        calculateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calculateGrades();
            }
        });
        showListButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showStudentList();
            }
        });

        // Make frame visible
        setVisible(true);
    }

    private void addStudent() {
        try {
            String name = nameField.getText().trim();
            double grade = Double.parseDouble(gradeField.getText().trim());
            students.add(new Student(name, grade));
            nameField.setText("");
            gradeField.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid grade (e.g., 85.5).", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateStudent() {
        String oldName = JOptionPane.showInputDialog(this, "Enter the name of the student to update:");
        if (oldName != null && !oldName.trim().isEmpty()) {
            Student studentToUpdate = null;
            for (Student student : students) {
                if (student.getName().equalsIgnoreCase(oldName)) {
                    studentToUpdate = student;
                    break;
                }
            }

            if (studentToUpdate != null) {
                String newName = JOptionPane.showInputDialog(this, "Enter the new name:", studentToUpdate.getName());
                String newGradeStr = JOptionPane.showInputDialog(this, "Enter the new grade:", studentToUpdate.getGrade());

                try {
                    double newGrade = Double.parseDouble(newGradeStr);
                    studentToUpdate.setName(newName);
                    studentToUpdate.setGrade(newGrade);
                    JOptionPane.showMessageDialog(this, "Student details updated successfully.");
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid grade (e.g., 85.5).", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Student not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void calculateGrades() {
        double average = calculateAverage(students);
        double highest = findHighest(students);
        double lowest = findLowest(students);

        averageLabel.setText("Average grade: " + average);
        highestLabel.setText("Highest grade: " + highest);
        lowestLabel.setText("Lowest grade: " + lowest);
    }

    private void showStudentList() {
        studentListArea.setText("");
        for (Student student : students) {
            studentListArea.append(student.toString() + "\n");
        }
    }

    public static double calculateAverage(ArrayList<Student> students) {
        if (students.isEmpty()) {
            return 0;
        }
        double sum = 0;
        for (Student student : students) {
            sum += student.getGrade();
        }
        return sum / students.size();
    }

    public static double findHighest(ArrayList<Student> students) {
        if (students.isEmpty()) {
            return Double.MIN_VALUE;
        }
        double highest = students.get(0).getGrade();
        for (Student student : students) {
            if (student.getGrade() > highest) {
                highest = student.getGrade();
            }
        }
        return highest;
    }

    public static double findLowest(ArrayList<Student> students) {
        if (students.isEmpty()) {
            return Double.MAX_VALUE;
        }
        double lowest = students.get(0).getGrade();
        for (Student student : students) {
            if (student.getGrade() < lowest) {
                lowest = student.getGrade();
            }
        }
        return lowest;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentGradeTrackerGUI());
    }
}

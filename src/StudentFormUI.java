import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class StudentFormUI extends JFrame {
    JLabel label1, label2, label3;
    JTextField field1, field2, field3;
    JButton button1, button2, button3, button4;

    Connection connection;
    Statement statement;
    ResultSet rs;

    StudentFormUI() {
        // EXACT UI AS REQUESTED
        setLayout(null);
        setTitle("Student Salary Manager");
        setSize(450, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        label1 = new JLabel("Number");
        label2 = new JLabel("Name");
        label3 = new JLabel("Salary");

        field1 = new JTextField();
        field2 = new JTextField();
        field3 = new JTextField();

        button1 = new JButton("First");
        button2 = new JButton("Next");
        button3 = new JButton("Previous");
        button4 = new JButton("Last");

        label1.setBounds(100, 100, 100, 30);
        field1.setBounds(220, 100, 100, 30);

        label2.setBounds(100, 140, 100, 30);
        field2.setBounds(220, 140, 100, 30);

        label3.setBounds(100, 180, 100, 30);
        field3.setBounds(220, 180, 100, 30);

        button1.setBounds(100, 220, 100, 30);
        button2.setBounds(220, 220, 100, 30);
        button3.setBounds(100, 260, 100, 30);
        button4.setBounds(220, 260, 100, 30);

        add(label1);
        add(label2);
        add(label3);
        add(field1);
        add(field2);
        add(field3);
        add(button1);
        add(button2);
        add(button3);
        add(button4);

        // DATABASE LOGIC
        setupDatabaseAndLoadData();

        // BUTTON ACTIONS
        button1.addActionListener(e -> navigate("first"));
        button2.addActionListener(e -> navigate("next"));
        button3.addActionListener(e -> navigate("prev"));
        button4.addActionListener(e -> navigate("last"));

        setVisible(true);
    }

    private void setupDatabaseAndLoadData() {
        String url = "jdbc:mysql://localhost:3306/";
        String user = "root";
        String password = "root";
        String dbName = "StudentDB";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            // 1. Create and Use Database
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS " + dbName);
            statement.execute("USE " + dbName);

            // 2. Create Table
//            statement.executeUpdate("DROP TABLE IF EXISTS StudentSalary"); // Refresh table for clean start
//            String createTable = "CREATE TABLE StudentSalary (" +
//                    "id INT PRIMARY KEY AUTO_INCREMENT, " +
//                    "name VARCHAR(50), " +
//                    "student_no VARCHAR(20), " +
//                    "salary DOUBLE)";
//            statement.executeUpdate(createTable);
//
//            // 3. Insert 19 Entries
//            String insertData = "INSERT INTO StudentSalary (name, student_no, salary) VALUES " +
//                    "('Aryan', 'S101', 5000), ('Rahul', 'S102', 6000), ('Neha', 'S103', 5500), " +
//                    "('Anita', 'S104', 7000), ('Vikram', 'S105', 4800.00), ('Sanya', 'S106', 7200.50), " +
//                    "('Rohan', 'S107', 5300.00), ('Ishita', 'S108', 6100.00), ('Arjun', 'S109', 4500.00), " +
//                    "('Kavya', 'S110', 8000.00), ('Manish', 'S111', 5900.00), ('Priya', 'S112', 6400.00), " +
//                    "('Siddharth', 'S113', 5100.00), ('Aditi', 'S114', 6700.00), ('Yash', 'S115', 5550.00), " +
//                    "('Meera', 'S116', 7100.00), ('Varun', 'S117', 4900.00), ('Anjali', 'S118', 6250.00), " +
//                    "('Deepak', 'S119', 5800.00)";
//            statement.executeUpdate(insertData);

            // 4. Load ResultSet
            rs = statement.executeQuery("SELECT * FROM StudentSalary");

            // Show first record
            if (rs.next()) {
                showData();
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to Database: " + e.getMessage());
        }
    }

    private void navigate(String cmd) {
        try {
            if (cmd.equals("first")) rs.first();
            else if (cmd.equals("last")) rs.last();
            else if (cmd.equals("next")) {
                if (!rs.isLast()) rs.next();
            } else if (cmd.equals("prev")) {
                if (!rs.isFirst()) rs.previous();
            }
            showData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showData() throws SQLException {
        field1.setText(rs.getString("student_no"));
        field2.setText(rs.getString("name"));
        field3.setText(rs.getString("salary"));
    }

    public static void main(String[] args) {
        new StudentFormUI();
    }
}
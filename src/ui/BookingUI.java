package ui;

import dao.BookingDAO;
import model.Booking;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BookingUI extends JFrame {
    private final BookingDAO dao = new BookingDAO();

    private JTextField nameField;
    private JTextField seatsField;
    private JTextArea outputArea;

    public BookingUI() {
        dao.createTableIfNotExists();

        setTitle("Bus Booking System");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5,5));
        inputPanel.add(new JLabel("Passenger Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Seats to Book:"));
        seatsField = new JTextField();
        inputPanel.add(seatsField);

        JButton bookBtn = new JButton("Book");
        bookBtn.addActionListener(e -> bookSeats());
        inputPanel.add(bookBtn);

        JButton checkBtn = new JButton("Show All Bookings");
        checkBtn.addActionListener(e -> showAllBookings());
        inputPanel.add(checkBtn);

        add(inputPanel, BorderLayout.NORTH);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);
    }

    private void bookSeats() {
        String name = nameField.getText().trim();
        int seats;
        try {
            seats = Integer.parseInt(seatsField.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Enter valid number of seats!");
            return;
        }

        if (dao.addBooking(new Booking(name, seats))) {
            outputArea.append(name + " booked " + seats + " seats\n");
        } else {
            outputArea.append("Booking failed for " + name + "\n");
        }
    }

    private void showAllBookings() {
        outputArea.setText("");
        List<Booking> list = dao.getAllBookings();
        if (list.isEmpty()) outputArea.append("No bookings yet.\n");
        else list.forEach(b -> outputArea.append(b.getName() + " → " + b.getSeats() + " seats\n"));
    }
}

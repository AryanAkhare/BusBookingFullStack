package dao;

import db.DBConnection;
import model.Booking;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    public void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS bookings (" +
                "id INT PRIMARY KEY AUTO_INCREMENT," +
                "name VARCHAR(50)," +
                "seats INT" +
                ")";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public boolean addBooking(Booking booking) {
        String sql = "INSERT INTO bookings (name, seats) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, booking.getName());
            ps.setInt(2, booking.getSeats());
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public List<Booking> getAllBookings() {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM bookings";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Booking(rs.getInt("id"), rs.getString("name"), rs.getInt("seats")));
            }

        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public int getSeatsByPassenger(String name) {
        String sql = "SELECT SUM(seats) AS total FROM bookings WHERE name=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("total");
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }
}

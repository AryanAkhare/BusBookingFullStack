package model;

public class Booking {
    private int id;
    private String name;
    private int seats;

    public Booking(String name, int seats) {
        this.name = name;
        this.seats = seats;
    }

    public Booking(int id, String name, int seats) {
        this.id = id;
        this.name = name;
        this.seats = seats;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getSeats() { return seats; }
}

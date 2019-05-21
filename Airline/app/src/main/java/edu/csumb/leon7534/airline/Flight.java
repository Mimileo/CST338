package edu.csumb.leon7534.airline;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Flight {
    private UUID logId;
    private String flight_no;
    private String departure_location;
    private String arrival_location;
    private String depart_time;
    private int capacity;
    private double price;

    private SimpleDateFormat sf = new SimpleDateFormat("HH:mm");

    public Flight(){
        logId = UUID.randomUUID();

    }

    public Flight(UUID id){
        logId = id;
    }

    public void setFlight(String flight_no, String departure_location, String arrival_location, String depart_time, int capacity, double price){
        this.flight_no = flight_no;
        this.departure_location = departure_location;
        this.arrival_location = arrival_location;
        this.depart_time = depart_time;
        this.capacity = capacity;
        this.price = price;
    }

    public String getFlight_no() {
        return flight_no;
    }

    public void setFlight_no(String flight_no) {
        this.flight_no = flight_no;
    }

    public String getDeparture_location() {
        return departure_location;
    }

    public void setDeparture_location(String departure_location) {
        this.departure_location = departure_location;
    }

    public String getArrival_location() {
        return arrival_location;
    }

    public void setArrival_location(String arrival_location) {
        this.arrival_location = arrival_location;
    }

    public String getDate() {
        return depart_time;
    }

    public void setDate(String time) {
        this.depart_time = time;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public UUID getLogId() {
        return logId;
    }

    public String getDepart_time() {
        return depart_time;
    }

   // public String getDateString() {
     //   return sf.format(depart_time);
    //}

    public SimpleDateFormat getSf() {
        return sf;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("\n\n=======================\nFlight No. " + flight_no + "\nDeparture: " + departure_location +"\nTime "+ depart_time + "\nArrival: " + arrival_location);
        sb.append("\nCapacity: " + capacity);
        sb.append("\nPrice: $" + String.format("%.2f",price));
        return sb.toString();
    }

    public boolean equals(Flight otherFlight){
        return this.departure_location.equals(otherFlight.getDeparture_location()) &&
                this.arrival_location.equals(otherFlight.getArrival_location()) &&
                this.depart_time.equals(otherFlight.getDepart_time());
    }
}

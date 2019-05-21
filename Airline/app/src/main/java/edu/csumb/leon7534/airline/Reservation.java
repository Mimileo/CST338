package edu.csumb.leon7534.airline;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Reservation {
    // get auto increment ID
    private UUID reservationLogId;
    private int reservationSqlLogId;

    private String flightNumber;
    private String arrival;
    private String depart;
    private Date logDate;
    private String username;
    private String depart_time;
    //private String transaction_type;
    private int tickets;
    private double amountDue;
    private boolean canceled;

    SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy @ HH:mm:ss");


    public Reservation() {
        this(UUID.randomUUID());
    }

    public Reservation(UUID resID){
        this.reservationLogId = resID;
        this.logDate = new Date();
        canceled = false;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
/*
    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }*/

    public String getDepart_time() {
        return depart_time;
    }

    public void setDepart_time(String depart_time) {
        this.depart_time = depart_time;
    }

    public int getTickets() {
        return tickets;
    }

    public void setTickets(int tickets) {
        this.tickets = tickets;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    public UUID getReservationLogId() {
        return reservationLogId;
    }

    public String getStringReservationLogId() {
        return reservationLogId.toString();
    }

    public Date getLogDate() {
        return logDate;
    }

    public int getSqlLogId() {
        return reservationSqlLogId;
    }

    public void setSqlLogId(int reservationSqlLogId) {
        this.reservationSqlLogId = reservationSqlLogId;
    }

    public double getAmountDue() {
        return amountDue;
    }

    public void setTotalAmount(double amountDue) {
        this.amountDue = amountDue;
    }

    public boolean equals(Reservation other){
        return this.username.equals(other.getUsername())
                && this.flightNumber.equals(other.getFlightNumber());
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();

        sb.append("\nUsername : " + getUsername() +
                "\nReservation ID: " + getSqlLogId() +
                 "\nFlight No.: " + getFlightNumber() +
                "\nDeparture: " + getDepart() +
                "\nDeparture Time: " + getDepart_time() +
                "\nArrival: " + getArrival() +
                "\nNumber of Tickets: " + getTickets() +
                "\nTotal Amount: $" + String.format("%.2f", this.getAmountDue())
        );
        return  sb.toString();
    }


}

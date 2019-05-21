package edu.csumb.leon7534.airline;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class LogTransaction {
    private UUID transactionLogId;
    private int sqlLogId;



    private String ID;
    private String name;

    private String transaction_type;

    // flight info

    private String flightNumber;
    private String departure;
    private String arrival;
    private String departureTime;
    private int numberOfReservedTickets;
    private String reservationID;
    private double totalAmount;
    Date transcationLogDate;

    SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy @ HH:mm:ss");
    // SimpleDateFormat timeFormat = new SimpleDateFormat("");

    public LogTransaction() {
        transcationLogDate = new Date();
        transactionLogId = UUID.randomUUID();
    }

    public LogTransaction(UUID userLogId) {
        this.transactionLogId = userLogId;
    }

    public int getSqlLogId() {
        return sqlLogId;
    }

    public void setSqlLogId(int sqlLogId) {
        this.sqlLogId = sqlLogId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public int getNumberOfReservedTickets() {
        return numberOfReservedTickets;
    }

    public void setNumberOfReservedTickets(int numberOfReservedTickets) {
        this.numberOfReservedTickets = numberOfReservedTickets;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getReservationID() {
        return reservationID;
    }

    public void setReservationID(String reservationID) {
        this.reservationID = reservationID;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }

    public SimpleDateFormat getSf() {
        return sf;
    }

    public void setSf(SimpleDateFormat sf) {
        this.sf = sf;
    }


    public UUID getTransactionLogId() {
        return transactionLogId;
    }

    public Date getTranscationLogDate() {
        return transcationLogDate;
    }

    public void setTranscationLogDate(Date transcationLogDate) {
        this.transcationLogDate = transcationLogDate;
    }

    public String getDateTotalString() {
        return sf.format(transcationLogDate);
    }

    public String getDateString() {
        return sf.format(transcationLogDate).substring(0, 12);
    }

    public String getTimeString() {
        return sf.format(transcationLogDate).substring(12);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(reservationID != null){
            sb.append(
                    "\n\nTransaction Type: " + getTransaction_type()+
                    "\nUsername: " + getName() +
                    "\nFlight No.: " + getFlightNumber() +
                    "\nDeparture: " + getDeparture() +
                    "\nArrival: " + getArrival() +
                    "\nDate: " + getDateString() +
                    "\nTime: " + getTimeString() +
                    "\nNumber of Tickets: " + getNumberOfReservedTickets() +
                    "\nReservation Number: " + getReservationID() +
                    "\nTotal Amount: " + String.format("%.2f", getTotalAmount())
                   );
        }else {
            sb.append(
                    "\n\nTransaction Type: " + getTransaction_type()+
                    "\nUsername: " + getName() +
                    "\nDate: " + getDateString() +
                    "\nTime: " + getTimeString()
                   );
        }
        return sb.toString();
    }
}

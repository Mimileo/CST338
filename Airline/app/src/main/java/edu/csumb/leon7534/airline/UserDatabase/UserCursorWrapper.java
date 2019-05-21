package edu.csumb.leon7534.airline.UserDatabase;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Date;
import java.util.UUID;

import edu.csumb.leon7534.airline.Flight;
import edu.csumb.leon7534.airline.LogTransaction;
import edu.csumb.leon7534.airline.Reservation;
import edu.csumb.leon7534.airline.UserDatabase.UserDbSchema.UserTable;
import edu.csumb.leon7534.airline.UserDatabase.UserDbSchema.FlightTable;
import edu.csumb.leon7534.airline.UserDatabase.UserDbSchema.ReservationTable;
import edu.csumb.leon7534.airline.UserDatabase.UserDbSchema.LogTransactionTable;




import edu.csumb.leon7534.airline.UserItem;

public class UserCursorWrapper extends CursorWrapper {

    public UserCursorWrapper(Cursor cursor){
        super(cursor);
    }


    public UserItem getUserItem() {
        String uuidString = getString(getColumnIndex(UserTable.Cols.UUID));
        String username = getString(getColumnIndex(UserTable.Cols.USERNAME));
        String password = getString(getColumnIndex(UserTable.Cols.PASSWORD));
        String tranaction_type = getString(getColumnIndex(UserTable.Cols.TRANSACTION_TYPE));
        Date date = new Date(getLong(getColumnIndex(UserTable.Cols.DATE)));

        int sqlLogId = getInt(getColumnIndex("_id"));

        UserItem user = new UserItem(UUID.fromString(uuidString));

        user.setUsername(username);
        user.setPassword(password);
        user.setTransaction_type(tranaction_type);
        user.setLogDate(date);
        user.setSqlLogId(sqlLogId);

        return user;

    }

    public Flight getFlightItem() {
        String uuidString = getString(getColumnIndex(FlightTable.Cols.UUID));
        String flight_no = getString(getColumnIndex(FlightTable.Cols.FLIGHT_NO));
        String depart_location = getString(getColumnIndex(FlightTable.Cols.DEPART_LOC));
        String arrival = getString(getColumnIndex(FlightTable.Cols.ARRIVAL));
        String departtime = getString(getColumnIndex(FlightTable.Cols.DEPART_TIME));
        int capacity = getInt(getColumnIndex(FlightTable.Cols.CAPACITY));
        double price = getDouble(getColumnIndex(FlightTable.Cols.PRICE));


        int sqlLogId = getInt(getColumnIndex("_id"));

        Flight flight = new Flight(UUID.fromString(uuidString));

        flight.setFlight_no(flight_no);
        flight.setDeparture_location(depart_location);
        flight.setArrival_location(arrival);
        flight.setDate(departtime);
        flight.setCapacity(capacity);
        flight.setPrice(price);

        return flight;

    }

    public Reservation getReservation() {
        String uuidString = getString(getColumnIndex(ReservationTable.Cols.UUID));
        String username = getString(getColumnIndex(ReservationTable.Cols.USERNAME));
        String flight_no = getString(getColumnIndex(ReservationTable.Cols.FLIGHT_NO));
        String depart_location = getString(getColumnIndex(ReservationTable.Cols.DEPART_LOC));
        String depart_time = getString(getColumnIndex(ReservationTable.Cols.DEPART_TIME));
        String arrival = getString(getColumnIndex(ReservationTable.Cols.ARRIVAL));
        Date logDate = new Date(getLong(getColumnIndex(ReservationTable.Cols.LOG_DATE)));
        int tickets = getInt(getColumnIndex(ReservationTable.Cols.TICKETS));
        double total = getInt(getColumnIndex(ReservationTable.Cols.TOTAL_AMOUNT));
        int isCanceled = getInt(getColumnIndex(ReservationTable.Cols.STATUS));

        int sqlLogid = getInt(getColumnIndex("_id"));

        Reservation reservation = new Reservation(UUID.fromString(uuidString));

        reservation.setUsername(username);
        reservation.setFlightNumber(flight_no);
        reservation.setDepart(depart_location);
        reservation.setDepart_time(depart_time);
        reservation.setArrival(arrival);
        reservation.setLogDate(logDate);
        reservation.setTickets(tickets);
        reservation.setTotalAmount(total);
        reservation.setCanceled(isCanceled != 0);
        reservation.setSqlLogId(sqlLogid + 1000);

        return reservation;

    }

    public LogTransaction getLogTransaction() {
        String uuidString = getString(getColumnIndex(LogTransactionTable.Cols.UUID));
        String name = getString(getColumnIndex(LogTransactionTable.Cols.NAME));
        String flightNumber = getString(getColumnIndex(LogTransactionTable.Cols.FLIGHT_NO));
        String depart_location = getString(getColumnIndex(LogTransactionTable.Cols.DEPART_LOC));
        String arrival = getString(getColumnIndex(LogTransactionTable.Cols.ARRIVAL));
        String departtime = getString(getColumnIndex(LogTransactionTable.Cols.DEPART_TIME));
        Date logDate = new Date(getLong(getColumnIndex(LogTransactionTable.Cols.LOG_DATE)));
        int tickets = getInt(getColumnIndex(LogTransactionTable.Cols.TICKETS));
        double price = getDouble(getColumnIndex(LogTransactionTable.Cols.TOTAL_AMOUNT));
        String resID = getString(getColumnIndex(LogTransactionTable.Cols.RESERVATION_ID));

        String transaction_type = getString(getColumnIndex(LogTransactionTable.Cols.TRANSACTION_TYPE));



        int sqlLogid = getInt(getColumnIndex("_id"));

        LogTransaction log = new LogTransaction(UUID.fromString(uuidString));

        log.setName(name);
        log.setFlightNumber(flightNumber);
        log.setDeparture(depart_location);
        log.setArrival(arrival);
        log.setDepartureTime(departtime);
        log.setNumberOfReservedTickets(tickets);
        log.setTotalAmount(price);
        log.setReservationID(resID);
        log.setTranscationLogDate(logDate);
        log.setTransaction_type(transaction_type);
        log.setSqlLogId(sqlLogid);

        return log;

    }




}

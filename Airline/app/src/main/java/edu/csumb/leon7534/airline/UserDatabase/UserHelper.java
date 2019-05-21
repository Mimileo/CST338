package edu.csumb.leon7534.airline.UserDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import edu.csumb.leon7534.airline.Flight;
import edu.csumb.leon7534.airline.LogTransaction;
import edu.csumb.leon7534.airline.Reservation;
import edu.csumb.leon7534.airline.UserDatabase.UserDbSchema.UserTable;
import edu.csumb.leon7534.airline.UserDatabase.UserDbSchema.FlightTable;
import edu.csumb.leon7534.airline.UserDatabase.UserDbSchema.ReservationTable;
import edu.csumb.leon7534.airline.UserDatabase.UserDbSchema.LogTransactionTable;


import edu.csumb.leon7534.airline.UserItem;

public class UserHelper extends SQLiteOpenHelper {
    public static final String TAG = "Airline";

    public static final String DATABASE_NAME = "userDatabase.db";
    public static final int VERSION = 1;

    private SQLiteDatabase db;



    public UserHelper(Context context) {
        super(context, DATABASE_NAME,null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL("create table " + UserTable.NAME +"(" +
                "_id integer primary key autoincrement, " +
                UserTable.Cols.UUID + ", " +
                UserTable.Cols.USERNAME + ", " +
                UserTable.Cols.PASSWORD + ", " +
                UserTable.Cols.TRANSACTION_TYPE + ", " +
                UserTable.Cols.DATE +
                ")");
        db.execSQL("create table " + FlightTable.NAME +"(" +
                "_id integer primary key autoincrement, " +
                FlightTable.Cols.UUID + ", " +
                FlightTable.Cols.FLIGHT_NO + ", " +
                FlightTable.Cols.DEPART_LOC + ", " +
                FlightTable.Cols.ARRIVAL + ", " +
                FlightTable.Cols.DEPART_TIME + ", " +
                FlightTable.Cols.CAPACITY + ", " +
                FlightTable.Cols.PRICE +
                ")");
        db.execSQL("create table " + ReservationTable.NAME +"(" +
                "_id integer primary key autoincrement, " +
                ReservationTable.Cols.UUID + ", " +
                ReservationTable.Cols.FLIGHT_NO + ", " +
                ReservationTable.Cols.DEPART_LOC + ", " +
                ReservationTable.Cols.ARRIVAL + ", " +
                ReservationTable.Cols.DEPART_TIME + ", " +
                ReservationTable.Cols.LOG_DATE + ", " +
                ReservationTable.Cols.USERNAME + ", " +
                ReservationTable.Cols.TICKETS + ", " +
                LogTransactionTable.Cols.TOTAL_AMOUNT + ", " +
                ReservationTable.Cols.STATUS +
                ")");
        db.execSQL("create table " + LogTransactionTable.NAME +"(" +
                "_id integer primary key autoincrement, " +
                LogTransactionTable.Cols.UUID + ", " +
                LogTransactionTable.Cols.LOG_DATE + ", " +
                LogTransactionTable.Cols.NAME + ", " +
                LogTransactionTable.Cols.TRANSACTION_TYPE + ", " +
                LogTransactionTable.Cols.FLIGHT_NO + ", " +
                LogTransactionTable.Cols.TICKETS + ", " +
                LogTransactionTable.Cols.DEPART_LOC + ", " +
                LogTransactionTable.Cols.ARRIVAL + ", " +
                LogTransactionTable.Cols.DEPART_TIME + ", " +
                LogTransactionTable.Cols.TOTAL_AMOUNT + ", " +
                LogTransactionTable.Cols.RESERVATION_ID +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long logTransaction(LogTransaction log){
        ContentValues cv = getContentValues(log);
        db = this.getWritableDatabase();
        // ability to insert row into our DB
        return db.insert(LogTransactionTable.NAME, null, cv);
    }

    public long addReservation(Reservation reservation){
        /*List<Reservation> reservations = getAllReservations();
        for(Reservation res : reservations){
            if(reservation.equals(res)){
                return -1;
            }
        }*/

        ContentValues cv = getContentValues(reservation);
        db = this.getWritableDatabase();
        // ability to insert row into our DB
        return db.insert(ReservationTable.NAME, null, cv);
        /*
        ContentValues cv = getContentValues(user);
        db = this.getWritableDatabase();
        return db.insert(UserTable.NAME, null, cv);*/
    }

    public boolean deleteReservation(String uuid){
      try{
          db = this.getWritableDatabase();
          db.delete(ReservationTable.NAME, ReservationTable.Cols.UUID + " = ?",
                  new String[]{uuid});
          return true;
      }catch (Exception e){
          return false;
      }
    }

    public boolean deleteReservationByFlight(String username, String flight){
        try{
            db = this.getWritableDatabase();
            db.delete(ReservationTable.NAME, ReservationTable.Cols.USERNAME + " = ? AND "
                    + ReservationTable.Cols.FLIGHT_NO + " = ?",
                    new String[] {username, flight+""});
            return true;
        }catch (Exception e){
            return false;
        }
    }



    public long addUser(UserItem user){
        ContentValues cv = getContentValues(user);
        db = this.getWritableDatabase();
        // ability to insert row into our DB
        return db.insert(UserTable.NAME, null, cv);
        /*
        ContentValues cv = getContentValues(user);
        db = this.getWritableDatabase();
        return db.insert(UserTable.NAME, null, cv);*/
    }

    public boolean checkUser(UserItem user){
        UserItem temp = null;
        List<UserItem> users = new ArrayList<>();
        UserCursorWrapper cursor = new UserCursorWrapper(queryDB(UserTable.NAME, null, null ));
        try{
            if(cursor.getCount() == 0){
                Log.d(TAG, "No flights to display");
            }
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                //users.add(cursor.getUserItem());
                if(cursor.getUserItem()!= null && user != null){
                if(user.getUsername().equals(cursor.getUserItem().getUsername())){
                    return true;
                }}
                cursor.moveToNext();
            }
        }finally {
            // prevent memory leaks
            cursor.close();
        }
        return false;
    }

    public boolean validateAdmin(String user, String pass){
        UserItem temp = null;

        UserCursorWrapper cursor = new UserCursorWrapper(queryDB(UserTable.NAME, null, null ));
        try{
            if(cursor.getCount() == 0){
                Log.d(TAG, "No flights to display");
            }
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                //users.add(cursor.getUserItem());
                if(cursor.getUserItem()!= null && user != null){
                    if(cursor.getUserItem().getUsername().equals("admin2")){
                        temp = cursor.getUserItem();
                        break;
                    }
                }
                cursor.moveToNext();
            }
        }finally {
            // prevent memory leaks
            cursor.close();
        }

        if(temp!= null && temp.getUsername().equals(user) && temp.getPassword().equals(pass)){
            return true;
        }
        return false;
    }




    public long addFlight(Flight flight){
        ContentValues cv = getContentValues(flight);
        db = this.getWritableDatabase();
        return db.insert(FlightTable.NAME, null, cv);
    }

    public boolean checkFlight(Flight flight){

        UserCursorWrapper cursor = new UserCursorWrapper(queryDB(FlightTable.NAME, null, null ));

        try{
            if(cursor.getCount() == 0){
                Log.d(TAG, "No flights to display");
            }
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                //users.add(cursor.getUserItem());
                if(cursor.getFlightItem()!= null && flight != null){
                    if(flight.getFlight_no().equals(cursor.getFlightItem().getFlight_no())){
                        return true;
                    }
                }
                cursor.moveToNext();
            }
        }finally {
            // prevent memory leaks
            cursor.close();
        }
        return false;
    }

    private ContentValues getContentValues(Reservation reservation){
        ContentValues cv = new ContentValues();

        cv.put(ReservationTable.Cols.UUID, reservation.getReservationLogId().toString());
        cv.put(ReservationTable.Cols.FLIGHT_NO, reservation.getFlightNumber());
        cv.put(ReservationTable.Cols.DEPART_LOC, reservation.getDepart());
        cv.put(ReservationTable.Cols.DEPART_TIME, reservation.getDepart_time());
        cv.put(ReservationTable.Cols.ARRIVAL, reservation.getArrival());
        cv.put(ReservationTable.Cols.LOG_DATE, reservation.getLogDate().getTime());
        cv.put(ReservationTable.Cols.USERNAME, reservation.getUsername());
        cv.put(ReservationTable.Cols.TICKETS, reservation.getTickets());
        cv.put(ReservationTable.Cols.TOTAL_AMOUNT, reservation.getAmountDue());

        cv.put(ReservationTable.Cols.STATUS, reservation.isCanceled());



        return cv;

    }

    private ContentValues getContentValues(UserItem user){
        ContentValues cv = new ContentValues();

        cv.put(UserTable.Cols.UUID, user.getUserLogId().toString());
        cv.put(UserTable.Cols.USERNAME, user.getUsername());
        cv.put(UserTable.Cols.PASSWORD, user.getPassword());
        cv.put(UserTable.Cols.TRANSACTION_TYPE, user.getTransaction_type());
        cv.put(UserTable.Cols.DATE, user.getLogDate().getTime());

        return cv;

    }

    private ContentValues getContentValues(Flight flight){
        ContentValues cv = new ContentValues();

        cv.put(FlightTable.Cols.UUID, flight.getLogId().toString());
        cv.put(FlightTable.Cols.FLIGHT_NO, flight.getFlight_no());
        cv.put(FlightTable.Cols.DEPART_LOC, flight.getDeparture_location());
        cv.put(FlightTable.Cols.ARRIVAL, flight.getArrival_location());
        cv.put(FlightTable.Cols.DEPART_TIME, flight.getDate());
        cv.put(FlightTable.Cols.CAPACITY, flight.getCapacity());
        cv.put(FlightTable.Cols.PRICE, flight.getPrice());

        return cv;

    }
/*
    private ContentValues getContentValues(LogTransaction log){
        ContentValues cv = new ContentValues();

        cv.put(LogTransactionTable.Cols.UUID, log.getTransactionLogId().toString());
        cv.put(LogTransactionTable.Cols.NAME, log.getName());

        cv.put(LogTransactionTable.Cols.LOG_DATE, log.getTranscationLogDate().getTime());
        cv.put(LogTransactionTable.Cols.TRANSACTION_TYPE, log.getTransaction_type());

        return cv;

    }*/

    private ContentValues getContentValues(LogTransaction log){
        ContentValues cv = new ContentValues();

        cv.put(LogTransactionTable.Cols.UUID, log.getTransactionLogId().toString());
        cv.put(LogTransactionTable.Cols.NAME, log.getName());
        cv.put(LogTransactionTable.Cols.FLIGHT_NO, log.getFlightNumber());
        cv.put(LogTransactionTable.Cols.DEPART_LOC, log.getDeparture());
        cv.put(LogTransactionTable.Cols.ARRIVAL, log.getArrival());
        cv.put(LogTransactionTable.Cols.DEPART_TIME, log.getDepartureTime());
        cv.put(LogTransactionTable.Cols.TOTAL_AMOUNT, log.getTotalAmount());
        cv.put(LogTransactionTable.Cols.TICKETS, log.getNumberOfReservedTickets());
        cv.put(LogTransactionTable.Cols.RESERVATION_ID, log.getReservationID());
        cv.put(LogTransactionTable.Cols.LOG_DATE, log.getTranscationLogDate().getTime());
        cv.put(LogTransactionTable.Cols.TRANSACTION_TYPE, log.getTransaction_type());

        return cv;

    }


    public List<UserItem> getUsers(){

        UserItem temp = null;
        List<UserItem> users = new ArrayList<>();
        UserCursorWrapper cursor = new UserCursorWrapper(queryDB(UserTable.NAME, null, null ));
        try{
            if(cursor.getCount() == 0){
                Log.d(TAG, "No flights to display");
            }
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
               users.add(cursor.getUserItem());
                cursor.moveToNext();
            }
        }finally {
            // prevent memory leaks
            cursor.close();
        }
        return users;
    }

    public UserItem getUserAccount(String username, String password){

        UserItem temp = null;
        List<UserItem> users = new ArrayList<>();
        UserCursorWrapper cursor = new UserCursorWrapper(queryDB(UserTable.NAME, null, null ));
        try{
            if(cursor.getCount() == 0){
                Log.d(TAG, "No Users to display");
            }
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                if(username.equals(cursor.getUserItem().getUsername()) && password.equals(cursor.getUserItem().getPassword())){
                    temp = cursor.getUserItem();
                    break;
                }
                cursor.moveToNext();
            }
        }finally {
            // prevent memory leaks
            cursor.close();
        }
        return temp;
    }

    public Reservation getReservation(String uuid){

        Reservation temp = null;

        UserCursorWrapper cursor = new UserCursorWrapper(queryDB(ReservationTable.NAME, null, null ));
        try{
            if(cursor.getCount() == 0){
                Log.d(TAG, "No Users to display");
            }
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                if(cursor.getReservation() != null && uuid.equals(cursor.getReservation().getStringReservationLogId())){
                    return cursor.getReservation();
                }
                cursor.moveToNext();
            }
        }finally {
            // prevent memory leaks
            cursor.close();
        }
        return temp;
    }

    public Reservation getReservationID(int logID){

        Reservation temp = null;

        UserCursorWrapper cursor = new UserCursorWrapper(queryDB(ReservationTable.NAME, null, null ));
        try{
            if(cursor.getCount() == 0){
                Log.d(TAG, "No Users to display");
            }
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                if(cursor.getReservation() != null && logID ==(cursor.getReservation().getSqlLogId())){
                    return cursor.getReservation();
                }
                cursor.moveToNext();
            }
        }finally {
            // prevent memory leaks
            cursor.close();
        }
        return temp;
    }


    public List<Reservation> getReservationByUser(String username){

        Reservation temp = null;
        List<Reservation> reservations = new ArrayList<>();


        UserCursorWrapper cursor = new UserCursorWrapper(queryDB(ReservationTable.NAME, null, null ));
        try{
            if(cursor.getCount() == 0){
                Log.d(TAG, "No Reservations to display");
            }
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                if(cursor.getReservation() != null && username.equals(cursor.getReservation().getUsername())){
                    reservations.add(cursor.getReservation());
                }
                cursor.moveToNext();
            }
        }finally {
            // prevent memory leaks
            cursor.close();
        }
        return reservations;
    }

    public List<Reservation> getAllReservations(){

        List<Reservation> reservations = new ArrayList<>();

        UserCursorWrapper cursor = new UserCursorWrapper(queryDB(ReservationTable.NAME, null, null ));
        try{
            if(cursor.getCount() == 0){
                Log.d(TAG, "No Reservations to display");
            }
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                reservations.add(cursor.getReservation());
                cursor.moveToNext();
            }
        }finally {
            // prevent memory leaks
            cursor.close();
        }
        return reservations;
    }

    public List<Flight> getFlights(){

        List<Flight> flights = new ArrayList<>();
        UserCursorWrapper cursor = new UserCursorWrapper(queryDB(FlightTable.NAME, null, null ));

        try{
            if(cursor.getCount() == 0){
                Log.d(TAG, "No flights to display");
            }
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                flights.add(cursor.getFlightItem());
                cursor.moveToNext();
            }
        }finally {
          // prevent memory leaks
          cursor.close();
        }
        return flights;

    }

    public Flight getSelectFlight(String flightID){

        Flight temp = null;
        UserCursorWrapper cursor = new UserCursorWrapper(queryDB(FlightTable.NAME, null, null ));

        try{
            if(cursor.getCount() == 0){
                Log.d(TAG, "No flights to display");
            }
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                if(cursor.getFlightItem() != null && flightID.equals(cursor.getFlightItem().getFlight_no())){
                    return cursor.getFlightItem();
                }
                cursor.moveToNext();
            }
        }finally {
            // prevent memory leaks
            cursor.close();
        }
        return temp;

    }

    public boolean updateFlight(String uuidString, ContentValues values){

        try {
            db = this.getWritableDatabase();
            db.update(FlightTable.NAME, values, FlightTable.Cols.UUID + " = ?",
                    new String[]{uuidString});
            //guess what? we are preventing SQL injection!\
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public List<Flight> getFilterFlights(String depart, String arrive, int numOftickets){


        List<Flight> flights = new ArrayList<>();
        UserCursorWrapper cursor = new UserCursorWrapper(queryDB(FlightTable.NAME, null, null ));

        try{
            if(cursor.getCount() == 0){
                Log.d(TAG, "No flights to display");
            }
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
               if(cursor.getFlightItem().getArrival_location().equals(arrive) && cursor.getFlightItem().getDeparture_location().equals(depart)
                       && cursor.getFlightItem().getCapacity() >= numOftickets){
                   flights.add(cursor.getFlightItem());
               }
                cursor.moveToNext();
            }
        }finally {
            // prevent memory leaks
            cursor.close();
        }
        return flights;

    }

    public List<LogTransaction> getLogs(){


        List<LogTransaction> logs = new ArrayList<>();
        UserCursorWrapper cursor = new UserCursorWrapper(queryDB(LogTransactionTable.NAME, null, null ));

        try{
            if(cursor.getCount() == 0){
                Log.d(TAG, "No flights to display");
            }
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                logs.add(cursor.getLogTransaction());
                cursor.moveToNext();
            }
        }finally {
            // prevent memory leaks
            cursor.close();
        }
        return logs;

    }




    public Cursor queryDB(String dbName, String whererClause, String[] whereArgs){
        db = this.getWritableDatabase();

        try{
            return db.query(
                    dbName,
                    null,
                    whererClause,
                    whereArgs,
                    null,
                    null,
                    null
            );
        }catch (Exception e){
            Log.d(TAG, "UserHelper queryDB problem");
            return null;
        }


    }
}

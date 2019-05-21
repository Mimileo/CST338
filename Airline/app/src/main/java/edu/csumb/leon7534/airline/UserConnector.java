package edu.csumb.leon7534.airline;

import android.content.ContentValues;
import android.content.Context;

import java.util.List;

import edu.csumb.leon7534.airline.UserDatabase.UserDbSchema;
import edu.csumb.leon7534.airline.UserDatabase.UserHelper;
import edu.csumb.leon7534.airline.UserDatabase.UserDbSchema.FlightTable;


public class UserConnector {
    private static UserConnector mUserConnector;
    private Context mContext;
    private UserHelper mUserHelper;

    // singleton
    public static UserConnector get(Context context){
        if(mUserConnector == null){
            mUserConnector = new UserConnector(context);
        }
        return mUserConnector;
    }

    private UserConnector(Context context){
        mContext = context.getApplicationContext();
        mUserHelper = new UserHelper(mContext);
    }

    // adding user to db
    public long addUser(UserItem user){

        return mUserHelper.addUser(user);
    }

    public long logTransaction(LogTransaction log){

        return mUserHelper.logTransaction(log);
    }

    public long addFlight(Flight flight){

        return mUserHelper.addFlight(flight);
    }

    public long addReservation(Reservation reservation ){

        return mUserHelper.addReservation(reservation);
    }

    public boolean checkUser(UserItem user){
        // if user found return true else return false
        return mUserHelper.checkUser(user);
    }

    public UserItem getUserAccount(String user, String pass){
        // if user found return true else return false
        return mUserHelper.getUserAccount(user, pass);
    }

    public boolean validateAdmin(String user, String pass){
        // if user found return true else return false
        return mUserHelper.validateAdmin(user, pass);
    }

    public Reservation getReservation(String uuid){
        // if user found return true else return false
        return mUserHelper.getReservation(uuid);
    }

    public Reservation getReservationID(int logID){
        // if user found return true else return false
        return mUserHelper.getReservationID(logID);
    }

    public String getReservationByUser(String username){
        List<Reservation> reservations = mUserHelper.getReservationByUser(username);

        StringBuilder sb = new StringBuilder();
        if(reservations.size() == 0){
            sb.append( "No Reservations to Display for  "+ username + "\n");
        }else {
            sb.append("Reservations\n");

            for (Reservation reservation : reservations) {
                sb.append(reservation.toString() + "\n");
            }
        }

        return sb.toString();
    }

    public Reservation checkUserFlight(String username, Flight flight){
        List<Reservation> reservations = mUserHelper.getReservationByUser(username);
        Reservation temp = null;
        for(Reservation res : reservations) {
            if(res.getFlightNumber().equals(flight.getFlight_no())){
                return res;
            }
        }
        return temp;

    }

    public List<Reservation> getReservationsForUser(String username){
        return mUserHelper.getReservationByUser(username);
    }

    public boolean deleteReservationByFlight(String username, String flight){
       return mUserHelper.deleteReservationByFlight(username, flight);
    }

    public boolean userHasReservations(String username){
        List<Reservation> reservations = mUserHelper.getReservationByUser(username);

        if(reservations.size() == 0){
            return false;
        }
        return true;
    }

    public boolean deleteReservation(String uuid){
        // if user found return true else return false
        return mUserHelper.deleteReservation(uuid);
    }

    public boolean checkFlight(Flight flight){
        // if user found return true else return false
        return mUserHelper.checkFlight(flight);
    }

    public Flight getSelectFlight(String flightID){
        // if user found return true else return false
        return mUserHelper.getSelectFlight(flightID);
    }

    public void updateFlight(Flight flight){
        String uuidString = flight.getLogId().toString();
        ContentValues values = UserConnector.getContentValues(flight);

       mUserHelper.updateFlight(uuidString, values);

    }

    public String getUsers(){
        // all our logs
        List<UserItem> users = mUserHelper.getUsers();
        //List<String> logStrings = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        if(users == null) {
            return "Users\n";
        }
        sb.append("Users\n");

        for(UserItem currUser : users) {
            sb.append(currUser.toString());
        }
        return sb.toString();
    }

    //adding flight to db
   // public long addFlight(Flight flight){
      //  return mUserHelper.addFlight(flight);
    //}

    // get all records we inserted
    public String getFlights(){
        List<Flight> flights = mUserHelper.getFlights();

        StringBuilder sb = new StringBuilder();
        if(flights == null){
            return "Flights\n";
        }
        sb.append("Flights\n");

        for(Flight flight : flights){
            sb.append(flight.toString());
        }

        return sb.toString();
    }

    public String getAllReservations(){
        List<Reservation> reservations = mUserHelper.getAllReservations();

        StringBuilder sb = new StringBuilder();
        if(reservations == null){
            return "Reservations\n";
        }
        sb.append("Reservations\n");

        for(Reservation reservation : reservations){
            sb.append(reservation.toString());
        }

        return sb.toString();
    }

    public String getAvailableFlights(String depart, String arrival, int tickets){
        List<Flight> flights = mUserHelper.getFilterFlights(depart, arrival, tickets);

        StringBuilder sb = new StringBuilder();
        if(flights.size() == 0){
            return null;
        }
        sb.append("Flights\n");

        for(Flight f : flights){
            sb.append(f.toString());
        }

        return sb.toString();
    }

    public List<Flight> getAvailableFlightsLabels(String depart, String arrival, int tickets){
       return mUserHelper.getFilterFlights(depart, arrival, tickets);
    }

    public String getLogStrings(){
        // all our logs
        List<LogTransaction> logs = mUserHelper.getLogs();
        //List<String> logStrings = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        if(logs.size() == 0) {
           return null;
        }
        sb.append("Logs\n");

        for (LogTransaction log : logs) {
            sb.append(log.toString());
        }

        return sb.toString();
    }

    private static ContentValues getContentValues(Flight flight){
        ContentValues cv = new ContentValues();

        //TODO  when dates are done deprecate this.
       /* long dateAdded, dateDone;
        if (flight.getDate() == null){
            dateAdded = 0;
           // dateDone = 0;
        } else {
           // dateAdded = flight.getDate().getTime();
            //dateDone = todo.getDateDone().getTime();
        }*/

        cv.put(FlightTable.Cols.UUID, flight.getLogId().toString());
        cv.put(FlightTable.Cols.FLIGHT_NO, flight.getFlight_no());
        cv.put(FlightTable.Cols.DEPART_LOC, flight.getDeparture_location());
        cv.put(FlightTable.Cols.ARRIVAL, flight.getArrival_location());
        cv.put(FlightTable.Cols.DEPART_TIME, flight.getDate());
        cv.put(FlightTable.Cols.CAPACITY, flight.getCapacity());
        cv.put(FlightTable.Cols.PRICE, flight.getPrice());

        return cv;
    }


}

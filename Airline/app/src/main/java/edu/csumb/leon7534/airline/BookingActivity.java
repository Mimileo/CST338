package edu.csumb.leon7534.airline;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BookingActivity extends AppCompatActivity {
    public static final String USER_TICKETS = "edu.csumb.leon7534.airline.user_tickets";
    public static final String USER_DEPART = "edu.csumb.leon7534.airline.user_depart";
    public static final String USER_ARRIVE = "edu.csumb.leon7534.airline.user_arrive";
    public static final String USER_FLIGHTNO = "edu.csumb.leon7534.airline.user_flight_no";
    public static final String USERNAME = "edu.csumb.leon7534.airline.username";

    UserConnector mUserConnector;

    Button book;
    Button cancel;
    TextView info;

    String username;
    Flight selected;
    String flightNumber;
    String departure;
    String arrival;
    int tickets;
    long resid;
    double amountDue;

    Reservation mReservation;
    Flight tempFlight;
    LogTransaction log;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        book = findViewById(R.id.continuebookFlight);
        cancel = findViewById(R.id.cancelBookFlight);
        info = findViewById(R.id.infoList);
        mUserConnector = UserConnector.get(getApplicationContext());

        //if(getIntent().getExtras() != null) {

            username = getIntent().getExtras().getString(USERNAME);
            flightNumber = getIntent().getExtras().getString(USER_FLIGHTNO);
            selected = mUserConnector.getSelectFlight(flightNumber);
            departure = getIntent().getExtras().getString(USER_DEPART);
            arrival = getIntent().getExtras().getString(USER_ARRIVE);
            tickets = getIntent().getExtras().getInt(USER_TICKETS);

            tempFlight = mUserConnector.getSelectFlight(flightNumber);
            Log.i("PendingReservation: ", tempFlight.toString());
             amountDue = tickets * selected.getPrice();
            mReservation = getReservationFromDisplay();
            resid = mUserConnector.addReservation(mReservation);

            //resid=-1;


            Log.i("PendingReservation: ", resid + "");


            mReservation = mUserConnector.getReservation(mReservation.getReservationLogId().toString());

            // resid = mReservation.getSqlLogId();

//        Log.i("PendingReservation: ",  mReservation.getSqlLogId()+"");
            //reservationID =  reservation.getSqlLogId();

// use uuid to look up user
/*
            info.setText("UserName: " + username +
                    "\nFlight No.: " + flightNumber +
                    "\nDeparture: " + departure + "," + tempFlight.getDepart_time() +
                    "\nArrival: " + arrival +
                    "\nNumber of Tickets: " + tickets +
                    "\nReservation ID: " + mReservation.getSqlLogId() +
                    "\nAmount: $" + String.format("%.2f", amountDue));
                    */
        confirmationBook();
           /* book.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("Booking", "Cotinue");


                }
            });*/

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cancelBooking();
                }
            });

       // }
    }

    public Reservation getReservationFromDisplay(){

        //String username = usernameText.getText().toString();
        //String password = passwordText.getText().toString();


        Reservation reservation = new Reservation();

        reservation.setUsername(username);
        reservation.setTickets(tickets);
        reservation.setArrival(arrival);
        reservation.setDepart(departure);
        reservation.setFlightNumber(flightNumber);
        reservation.setDepart_time(tempFlight.getDepart_time());
        reservation.setTotalAmount(amountDue);
        //reservation.setCanceled();

        return reservation;

    }

    public LogTransaction logReservationDisplay(){

        //String username = usernameText.getText().toString();
        //String password = passwordText.getText().toString();


        LogTransaction log = new LogTransaction();

        log.setName(username);
        log.setFlightNumber(mReservation.getFlightNumber());
        log.setDeparture(departure);
        log.setArrival(arrival);
        log.setDepartureTime(tempFlight.getDepart_time());
        log.setNumberOfReservedTickets(tickets);
        log.setReservationID(mReservation.getSqlLogId() + "");
        log.setTotalAmount(amountDue);
        log.setTransaction_type("Reserve Seat");


        return log;

    }

    public void bookingProceeds(){
        Log.i("FLIGHTS: ", tempFlight.toString());
        Log.i("RESERVATION: ", mReservation.toString());
        if(tempFlight != null && mReservation != null){
            tempFlight.setCapacity(tempFlight.getCapacity() - tickets);
            mUserConnector.updateFlight(tempFlight);
            log = logReservationDisplay();
            resid = mUserConnector.logTransaction(log);
            Log.i("Booking: ", resid+"");

        }
        Intent intent = new Intent(BookingActivity.this, MainActivity.class);

        startActivity(intent);
    }


    public void confirmationBook()
    {
        Log.i("Booking", "Cotinue with confirm");


        // Create the object of
        // AlertDialog Builder class
        final AlertDialog.Builder builder = new AlertDialog.Builder(BookingActivity.this);
        final AlertDialog alertDialog = builder.create();
        // Set the message show for the Alert
        builder.setMessage(
                "\nReservation\nUserName: " + username +
                "\nFlight No.: " + flightNumber +
                "\nDeparture: " + departure + "," + tempFlight.getDepart_time() +
                "\nArrival: " + arrival +
                "\nNumber of Tickets: " + tickets +
                "\nReservation ID: " + mReservation.getSqlLogId() +
                "\nAmount: $" + String.format("%.2f", amountDue));

        // Set Alert Title
        builder.setTitle("Is this correct?");

        // Set Cancelable false
        // for when the user clicks on the outside
        // the Dialog Box then it will remain show
        builder.setCancelable(false);

        // Set the positive button with yes name
        // OnClickListener method is use of
        // DialogInterface interface.

        builder.setPositiveButton(
                "Confirm",
                new DialogInterface
                        .OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which)
                    {


                      bookingProceeds();
                    }
                });

        // Set the Negative button with No name
        // OnClickListener method is use
        // of DialogInterface interface.
        builder.setNegativeButton(
                "Cancel",
                new DialogInterface
                        .OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which)
                    {
                        cancelBooking();
                        dialog.cancel();
                    }
                });

        AlertDialog someAlert = builder.create();
        // Show the Alert Dialog box
        someAlert.show();
    }


    public void cancelBooking()
    {
        Log.i("Booking", "Cotinue with cancel");


        // Create the object of
        // AlertDialog Builder class
        final AlertDialog.Builder builder = new AlertDialog.Builder(BookingActivity.this);
        final AlertDialog alertDialog = builder.create();
        // Set the message show for the Alert
        builder.setMessage("You trying to cancel Reservation " + mReservation.getSqlLogId() + ". Are you sure you want to cancel booking this reservation?");

        // Set Alert Title
        builder.setTitle("Cancellation");

        // Set Cancelable false
        // for when the user clicks on the outside
        // the Dialog Box then it will remain show
        builder.setCancelable(false);

        // Set the positive button with yes name
        // OnClickListener method is use of
        // DialogInterface interface.

        builder.setPositiveButton(
                "Re-Confirm",
                new DialogInterface
                        .OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which)
                    {



                       confirmCancel();

                        //Log.i("FLIGHTS", "canceled reservation " + mUserConnector.getReservation(mReservation.getReservationLogId().toString()));

                        //Intent intent = new Intent(BookingActivity.this, MainActivity.class);

                        //startActivity(intent);
                    }
                });

        // Set the Negative button with No name
        // OnClickListener method is use
        // of DialogInterface interface.
        builder.setNegativeButton(
                "No Cancellation",
                new DialogInterface
                        .OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which)
                    {
                       bookingProceeds();
                        dialog.cancel();
                    }
                });

        AlertDialog someAlert = builder.create();
        // Show the Alert Dialog box
        someAlert.show();
    }

    public void confirmCancel()
    {
        Log.i("Booking", "Cotinue with cancel");


        // Create the object of
        // AlertDialog Builder class
        final AlertDialog.Builder builder = new AlertDialog.Builder(BookingActivity.this);
        final AlertDialog alertDialog = builder.create();
        // Set the message show for the Alert
        builder.setMessage("The reservation " + mReservation.getSqlLogId() + " has failed.");

        // Set Alert Title
        builder.setTitle("Reservation Failure");

        // Set Cancelable false
        // for when the user clicks on the outside
        // the Dialog Box then it will remain show
        builder.setCancelable(false);

        // Set the positive button with yes name
        // OnClickListener method is use of
        // DialogInterface interface.

        builder.setPositiveButton(
                "Confirm",
                new DialogInterface
                        .OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which)
                    {


                        Log.i("FLIGHTS", "pending reservation " + mReservation.getSqlLogId());
                        if(mUserConnector.deleteReservation(mReservation.getReservationLogId().toString())){
                            Log.i("FLIGHTS", "deleted pending reservation");

                        }

                       // builder.setMessage("Reservation Failure Due to Cancellation");

                                Intent startActivity = new Intent(BookingActivity.this, MainActivity.class);
                                startActivity(startActivity);
                                finish();

                    }
                });

        // Set the Negative button with No name


        AlertDialog someAlert = builder.create();
        someAlert.show();
    }



}

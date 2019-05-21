package edu.csumb.leon7534.airline;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.csumb.leon7534.airline.UserDatabase.UserHelper;

public class UserAccountActivity extends AppCompatActivity {
    public static final String USER = "USER_LOGIN";

    private TextView usernameTextView;
    private TextView userFlightsTextView;
    EditText cancelFlight;

    Button cancelButton;
    Button backButton;
    Spinner spinner;

    UserConnector userConnector;
    UserHelper dbHelper;
    LogTransaction log;
    Flight tempFlight;
    Reservation res;

    String username;

    String flightNumber;

    public static Intent viewReservationsIntent(Context packageContext, String value) {
        Intent intent = new Intent(packageContext, UserAccountActivity.class );
        intent.putExtra(USER, value);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);

        usernameTextView = findViewById(R.id.userTextView);
        userFlightsTextView = findViewById(R.id.userFlightsTextView);
        //cancelFlight = findViewById(R.id.cancelFlightEditText);
        cancelButton = findViewById(R.id.cancelButton);
        backButton = findViewById(R.id.disregardButton);

        spinner = findViewById(R.id.spinner1);
       // spinner.setPrompt("Select a Flight Number");

        // populate spinner from DB

        userFlightsTextView.setMovementMethod(new ScrollingMovementMethod());

        userConnector = UserConnector.get(this.getApplicationContext());




        if(getIntent().getExtras() != null) {
            username = getIntent().getExtras().getString(USER);
            usernameTextView.setText("Welcome " + username + "!");
            if(userConnector.userHasReservations(username)) {
                userFlightsTextView.setText(userConnector.getReservationByUser(username));
                populateSpinner();

            } else {
                noFlights();
            }

        }

      spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
              ((TextView) spinner.getSelectedView()).setTextColor(getResources().getColor(R.color.colorWhite));
              flightNumber = adapterView.getItemAtPosition(i).toString();
          }

          @Override
          public void onNothingSelected(AdapterView<?> adapterView) {

          }
      });
/*
        cancelFlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
*/

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(!flightNumber.equals("")) {
                    //flightNumber = "Otter" + cancelFlight.getText().toString();
                    tempFlight = userConnector.getSelectFlight(flightNumber);
                    if( tempFlight != null && userConnector.checkUserFlight(username, tempFlight) != null) {
                        res = userConnector.checkUserFlight(username, tempFlight);
                        confirmCancel();
                    }else {
                        Utils.toaster(UserAccountActivity.this, "Choose one of your reservation Number", Gravity.CENTER);
                    }

                } else {
                    Utils.toaster(UserAccountActivity.this, "Choose one of your reservation Number", Gravity.CENTER);
                }
            }



        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  i = new Intent(UserAccountActivity.this, MainActivity.class);
                startActivity(i);

            }
        });


    }

    public LogTransaction getLogTransactionDisplay(Reservation reservation){
        LogTransaction log = new LogTransaction();

        log.setName(reservation.getUsername());
        log.setReservationID(String.valueOf(res.getSqlLogId()));
        log.setFlightNumber(reservation.getFlightNumber());
        log.setDeparture(reservation.getDepart());
        log.setArrival(reservation.getArrival());
        log.setDepartureTime(reservation.getDepart_time());
        log.setNumberOfReservedTickets(reservation.getTickets());
        log.setTotalAmount(reservation.getTickets() * tempFlight.getPrice());
        log.setTransaction_type("Cancellation");

        return log;
    }

    public  void populateSpinner(){
        List<Reservation> userReservations = userConnector.getReservationsForUser(username);
        List<String> flightNumbers = new ArrayList<>();
        if(userReservations == null){
            return;
        }
        flightNumbers.add("Select a flight number");

        for(Reservation available : userReservations){
            flightNumbers.add(available.getFlightNumber());
        }


        // adpater for spinner
        // Initializing an ArrayAdapter
        final ArrayAdapter<String> flightNumberAdapter = new ArrayAdapter<String>(
                this,R.layout.support_simple_spinner_dropdown_item,flightNumbers){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {

                   // set hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0 ){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        //ArrayAdapter<String> flightNumberAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, flightNumbers);
       // ((TextView) spinner.getSelectedView()).setTextColor(getResources().getColor(R.color.colorWhite));

        flightNumberAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attach adpater to spinner
        spinner.setAdapter(flightNumberAdapter);
        //spinner.setSelection("Select a flight number");

    }


    public void noFlights()
    {

        // Create the object of
        // AlertDialog Builder class
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(UserAccountActivity.this);

        // Set the message show for the Alert time
        builder.setMessage(username + " there are no reservations for your account");

        // Set Alert Title
        builder.setTitle("Reservation Display Error");

        // Set Cancelable false
        // for when the user clicks on the outside
        // the Dialog Box then it will remain show
        builder.setCancelable(false);

        // Set the positive button with yes name
        // OnClickListener method is use of
        // DialogInterface interface.

        builder
                .setPositiveButton(
                        "Verify",
                        new DialogInterface
                                .OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which)
                            {

                                // When the user click yes button
                                // then app will close
                                Intent intent = new Intent(UserAccountActivity.this, MainActivity.class);
                                // finish();
                                startActivity(intent);
                            }
                        });
        // Set the Negative button with No name
        // OnClickListener method is use
        // of DialogInterface interface.
       /* builder
                .setNegativeButton(
                        "No",
                        new DialogInterface
                                .OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which)
                            {

                                // If user click no
                                // then dialog box is canceled.
                                dialog.cancel();
                            }
                        });
*/
        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();

        // Show the Alert Dialog box
        alertDialog.show();
    }

    public void confirmCancel()
    {
        Log.i("USERACCOUNT", "Cotinue with cancel");


        // Create the object of
        // AlertDialog Builder class
        final AlertDialog.Builder builder = new AlertDialog.Builder(UserAccountActivity.this);
        final AlertDialog alertDialog = builder.create();
        // Set the message show for the Alert
        builder.setMessage("You trying to cancel this reservation."+res.toString()+" Do you really want to cancel this reservation?");

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
                "Confirm",
                new DialogInterface
                        .OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which)
                    {


                        Log.i("FLIGHTS", "canceled reservation");
                        log = getLogTransactionDisplay(res);
                        userConnector.logTransaction(log);

                        if(userConnector.deleteReservation(res.getReservationLogId().toString())){
                            Log.i("FLIGHTS", "canceled reservation");

                            //userConnector.logTransaction();

                            tempFlight.setCapacity(tempFlight.getCapacity() + log.getNumberOfReservedTickets());
                            userConnector.updateFlight(tempFlight);

                        }
                        if(res.getReservationLogId() == null){
                            Log.i("FLIGHTS", " reservation deleted");

                        }

                        Intent startActivity = new Intent(UserAccountActivity.this, MainActivity.class);
                        startActivity(startActivity);
                        finish();
                    }
                });

        // Set the Negative button with No name
        // OnClickListener method is use
        // of DialogInterface interface.
        builder.setNegativeButton(
                "Disregard",
                new DialogInterface
                        .OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which)
                    {
                        disregardCancel();

                        // If user click no
                        // then dialog box is canceled.
                       // dialog.cancel();
                    }
                });

        AlertDialog someAlert = builder.create();
        // Show the Alert Dialog box
        someAlert.show();
    }




    public void disregardCancel() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(UserAccountActivity.this);
        final AlertDialog alertDialog = builder.create();
        // Set the message show for the Alert
        builder.setMessage("The cancellation for Reservation No. " + res.getSqlLogId() + " has failed.");

        // Set Alert Title
        builder.setTitle("Failed Cancellation");

        // Set Cancelable false
        // for when the user clicks on the outside
        // the Dialog Box then it will remain show
        builder.setCancelable(false);

        // Set the positive button with yes name
        // OnClickListener method is use of
        // DialogInterface interface.

        builder.setPositiveButton(
                "Verify",
                new DialogInterface
                        .OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which)
                    {

                        Intent startActivity = new Intent(UserAccountActivity.this, MainActivity.class);
                        startActivity(startActivity);
                        finish();
                    }
                });
        AlertDialog someAlert = builder.create();
        // Show the Alert Dialog box
        someAlert.show();
    }

}

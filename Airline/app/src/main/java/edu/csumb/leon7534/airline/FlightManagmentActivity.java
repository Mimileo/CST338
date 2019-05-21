package edu.csumb.leon7534.airline;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class FlightManagmentActivity extends AppCompatActivity {


    public static final String USERNAME = "edu.csumb.leon7534.airline.username";

    Button addFlight;
    Button setTime;

    EditText flightNum;
    EditText departLocation;
    EditText arrivalLocation;
    EditText time;
    EditText price;
    EditText cap;
    DateFormat formatter = new SimpleDateFormat("HH:mm");

    Flight inputFlight;
    String flightNumber;
    int capacity;
    double cost;
    String depart, arrive;
    String timeDepart;
    UserConnector mConnector;

    public static Intent adminIntent(Context packageContext, String username){
        Intent intent = new Intent(packageContext, FlightManagmentActivity.class);
        intent.putExtra(USERNAME, username);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_managment);

        flightNum = findViewById(R.id.flightNumberEditText);
        departLocation = findViewById(R.id.departure);
        arrivalLocation = findViewById(R.id.arrival);
        time = findViewById(R.id.timeEditText);
        price = findViewById(R.id.priceEditText);
        cap = findViewById(R.id.capacity);
        addFlight = findViewById(R.id.addFlightButton);

        mConnector = UserConnector.get(this.getApplicationContext());

        Intent intent = getIntent();
        String username = intent.getStringExtra(USERNAME);

        Log.i("FLIGHTS", username + "is managing");
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize a new time picker dialog fragment
                DialogFragment dFragment = new TimePickerFragment();

                // Show the time picker dialog fragment
                dFragment.show(getSupportFragmentManager(),"Time Picker");
            }
        });

        addFlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Utils.isEmpty(flightNum) || Utils.isEmpty(departLocation) || Utils.isEmpty(arrivalLocation) || Utils.isEmpty(time)
                        || Utils.isEmpty(price) || Utils.isEmpty(cap)){
                    invalidInput();

                } else{
                    inputFlight = getFlightFromDisplay();
                    if(mConnector.checkFlight(inputFlight)){
                        invalidInput();
                    } else {
                        ConfirmFlightprompt();

                    }
                    //flightNumber = Integer.parseInt(time.getText().toString());
                    //capacity = Integer.parseInt(cap.getText().toString());

                }


            }
        });

    }

    public Flight getFlightFromDisplay(){
       flightNumber = flightNum.getText().toString();
        try{
            cost = Double.parseDouble(price.getText().toString());
        } catch (NumberFormatException e){
            Log.i("FlightManagement", "Could not convert to double price");
        }
        try{
            capacity = Integer.parseInt(cap.getText().toString());
        } catch (NumberFormatException e){
            Log.i("FlightManagement", "Could not convert to integer capacity");
        }

        depart = departLocation.getText().toString();
        arrive = arrivalLocation.getText().toString();
        timeDepart = time.getText().toString();


        Flight flight = new Flight();

        flight.setFlight_no(flightNumber);
        flight.setDeparture_location(depart);
        flight.setArrival_location(arrive);
        flight.setDate(timeDepart);
        flight.setCapacity(capacity);
        flight.setPrice(cost);
        return flight;
    }

    public void ConfirmFlightprompt()
    {

        // Create the object of
        // AlertDialog Builder class
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(FlightManagmentActivity.this);

        // Set the message show for the Alert time
        builder.setMessage(getFlightFromDisplay().toString());

        builder.setTitle("Is this correct?\n"+
                inputFlight.toString());

        // Set Cancelable false
        builder.setCancelable(false);



        builder.setPositiveButton(
                "YES",
                new DialogInterface
                        .OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which)
                    {
                        mConnector.addFlight(inputFlight);
                        Log.i("FlightManage", "Added flight");

                        Intent someIntent = new Intent(FlightManagmentActivity.this, MainActivity.class);
                        startActivity(someIntent);

                    }
                });

        // Set the Negative button with No name
        // OnClickListener method is use
        // of DialogInterface interface.
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog,
                                int which)
            {



                dialog.dismiss();
            }
        });

        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();

        // Show the Alert Dialog box
        alertDialog.show();
    }

    public void invalidInput()
    {

        // Create the object of
        // AlertDialog Builder class
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(FlightManagmentActivity.this);

        // Set the message show for the Alert time
        builder.setMessage("Invalid input or the flight exists. Return to home screen?");

        builder.setTitle("Error: Invalid Input.");

        // Set Cancelable false
        builder.setCancelable(false);



        builder.setPositiveButton(
                "Confirm",
                new DialogInterface
                        .OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which)
                    {

                        Intent someIntent = new Intent(FlightManagmentActivity.this, MainActivity.class);
                        startActivity(someIntent);

                    }
                });

        // Set the Negative button with No name
        // OnClickListener method is use
        // of DialogInterface interface.
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog,
                                int which)
            {



                dialog.dismiss();
            }
        });

        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();

        // Show the Alert Dialog box
        alertDialog.show();
    }




}

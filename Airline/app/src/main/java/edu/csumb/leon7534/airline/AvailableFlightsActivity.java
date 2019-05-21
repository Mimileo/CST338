package edu.csumb.leon7534.airline;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

public class AvailableFlightsActivity extends AppCompatActivity {

    public static final String USER_TICKETS = "edu.csumb.leon7534.airline.user_tickets";
    public static final String USER_DEPART = "edu.csumb.leon7534.airline.user_depart";
    public static final String USER_ARRIVE = "edu.csumb.leon7534.airline.user_arrive";
    public static final String USER_FLIGHTNO = "edu.csumb.leon7534.airline.user_flight_no";


    TextView listFlights;
    EditText selected;
    Button bookButton;


    UserConnector mUserConnector;
    String selectFlightNumber;
    String arrival;
    String departure;
    int tickets;


    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_flights);

        listFlights = findViewById(R.id.infoList);
        //selected = findViewById(R.id.flightNumberEditText);
        bookButton = findViewById(R.id.continuebookFlight);
        spinner = findViewById(R.id.spinner2);

        if(getIntent().getExtras() != null) {

            departure = getIntent().getExtras().getString(USER_DEPART);
            arrival = getIntent().getExtras().getString(USER_ARRIVE);
            tickets = getIntent().getExtras().getInt(USER_TICKETS);
        }


        mUserConnector = UserConnector.get(getApplicationContext());

        if(mUserConnector.getAvailableFlights(departure, arrival, tickets) != null) {
            listFlights.setText(mUserConnector.getAvailableFlights(departure, arrival, tickets));
            populateSpinner();
        }






        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(spinner.equals("")){
                    Utils.toaster(AvailableFlightsActivity.this, "Please enter a valid number", Gravity.TOP);
                }else {
                    //selectFlightNumber = selected.getText().toString();
                    if(validateFlight(selectFlightNumber)){
                        Intent loginIntent = new Intent(AvailableFlightsActivity.this, LoginActivity.class);
                        loginIntent.putExtra(USER_TICKETS, tickets);
                        loginIntent.putExtra(USER_DEPART, departure);
                        loginIntent.putExtra(USER_ARRIVE, arrival);
                        loginIntent.putExtra(USER_FLIGHTNO, selectFlightNumber);
                        startActivity(loginIntent);


                    }
                    Log.i("AFlights", selectFlightNumber + "");
                }
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView) spinner.getSelectedView()).setTextColor(getResources().getColor(R.color.colorWhite));
                selectFlightNumber = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public boolean validateFlight(String selectFlightNumber){
        if(mUserConnector.getSelectFlight(selectFlightNumber) != null){
            return true;
        }
        return false;
    }


    public  void populateSpinner(){
        List<Flight> flights = mUserConnector.getAvailableFlightsLabels(departure, arrival, tickets);
        List<String> flightNumbers = new ArrayList<>();
        if(flights == null){
            return;
        }
        flightNumbers.add("Select a flight number");

        for(Flight  flight : flights){
            flightNumbers.add(flight.getFlight_no());
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



    public void goToLogin()
    {

        // Create the object of
        // AlertDialog Builder class
        AlertDialog.Builder builder = new AlertDialog.Builder(AvailableFlightsActivity.this);
        final AlertDialog alertDialog = builder.create();
        // Set the message show for the Alert
        builder.setMessage("Your password did not meet criteria.");

        // Set Alert Title
        builder.setTitle("Exiting!");

        // Set Cancelable false
        // for when the user clicks on the outside
        // the Dialog Box then it will remain show
        builder.setCancelable(false);

        // Set the positive button with yes name
        // OnClickListener method is use of
        // DialogInterface interface.

        builder.setPositiveButton(
                "Continue to Home",
                new DialogInterface
                        .OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which)
                    {

                        // When the user click yes button
                        // then app will close
                        Intent intent = new Intent(AvailableFlightsActivity.this, MainActivity.class);
                        // finish();
                        startActivity(intent);
                    }
                });

        // Set the Negative button with No name
        // OnClickListener method is use
        // of DialogInterface interface.
        builder.setNegativeButton(
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

        // Create the Alert dialog
        //AlertDialog alertDialog = builder.create();

        // Show the Alert Dialog box
        alertDialog.show();
    }
}

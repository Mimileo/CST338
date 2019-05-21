package edu.csumb.leon7534.airline;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ReserveSeatActivity extends AppCompatActivity {

    public static final String USER_TICKETS = "edu.csumb.leon7534.airline.user_tickets";
    public static final String USER_DEPART = "edu.csumb.leon7534.airline.user_depart";
    public static final String USER_ARRIVE = "edu.csumb.leon7534.airline.user_arrive";


    Button searchFlights;
    EditText depart;
    EditText arrive;
    EditText numOftickets;
    TextView listFlights;
    UserConnector userConnector;
    boolean viewFligts = false;

    String depature;
    String arrival;
    int tickets = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_seat);


        searchFlights = findViewById(R.id.searchFlightButton);
        depart = findViewById(R.id.flightNumberEditText);
        arrive = findViewById(R.id.arrive);
        numOftickets = findViewById(R.id.priceEditText);
        //listFlights = findViewById(R.id.infoList);

        userConnector = UserConnector.get(getApplicationContext());

//        listFlights.setMovementMethod(new ScrollingMovementMethod());

        searchFlights.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isEmpty(depart) || isEmpty(arrive) || isEmpty(numOftickets)){
                    noFlights();
                }
                else {
                    depature = depart.getText().toString();
                    arrival = arrive.getText().toString();

                    try {
                        if(Integer.parseInt(numOftickets.getText().toString()) <= 7) {
                            tickets = Integer.parseInt(numOftickets.getText().toString());
                        } else{
                           // Utils.toaster(ReserveSeatActivity.this, "Number of tickets cannot be over 7", Gravity.TOP);
                            reserveSeatLimit();
                            viewFligts = true;
                        }


                    }catch(NumberFormatException e){
                        Log.d("Create Reservation", "Could not convert to integer");
                    }

                    Intent viewFlightIntent = new Intent(ReserveSeatActivity.this, AvailableFlightsActivity.class);

                    viewFlightIntent.putExtra(USER_TICKETS, tickets);
                    viewFlightIntent.putExtra(USER_DEPART, depature);
                    viewFlightIntent.putExtra(USER_ARRIVE, arrival);


                    if(viewFligts == false && userConnector.getAvailableFlights(depature, arrival, tickets) != null) {
                        startActivity(viewFlightIntent);
                    } else if (userConnector.getAvailableFlights(depature, arrival, tickets) == null){
                        noFlights();
                    }
                }
            }
        });
    }

    private boolean isEmpty(EditText check){
        return check.getText().toString().trim().length() == 0;

    }



    public void noFlights()
    {

        // Create the object of
        // AlertDialog Builder class
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(ReserveSeatActivity.this);

        // Set the message show for the Alert time
        builder.setMessage("No Flights Matching Your Filters");

        // Set Alert Title
        builder.setTitle("Error");

        // Set Cancelable false
        // for when the user clicks on the outside
        // the Dialog Box then it will remain show
        builder.setCancelable(false);

        // Set the positive button with yes name
        // OnClickListener method is use of
        // DialogInterface interface.

        builder
                .setPositiveButton(
                        "Exit",
                        new DialogInterface
                                .OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which)
                            {

                                // When the user click yes button
                                // then app will close
                                Intent intent = new Intent(ReserveSeatActivity.this, MainActivity.class);
                                // finish();
                                startActivity(intent);
                            }
                        });

        // Set the Negative button with No name
        // OnClickListener method is use
        // of DialogInterface interface.
        builder
                .setNegativeButton(
                        "Stay",
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
        AlertDialog alertDialog = builder.create();

        // Show the Alert Dialog box
        alertDialog.show();
    }

    public void reserveSeatLimit()
    {

        // Create the object of
        // AlertDialog Builder class
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(ReserveSeatActivity.this);

        // Set the message show for the Alert time
        builder.setMessage("You cannot reserve more than 7 tickets at once. Enter a number that does not exceed 7.");

        // Set Alert Title
        builder.setTitle("Ticket Error");

        // Set Cancelable false
        // for when the user clicks on the outside
        // the Dialog Box then it will remain show
        builder.setCancelable(false);

        // Set the positive button with yes name
        // OnClickListener method is use of
        // DialogInterface interface.

        builder
                .setPositiveButton(
                        "Confirm",
                        new DialogInterface
                                .OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which)
                            {

                                // When the user click yes button
                                // then app will close
                                Intent intent = new Intent(ReserveSeatActivity.this, ReserveSeatActivity.class);
                                // finish();
                                startActivity(intent);
                            }
                        });
        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();

        // Show the Alert Dialog box
        alertDialog.show();


    }
}

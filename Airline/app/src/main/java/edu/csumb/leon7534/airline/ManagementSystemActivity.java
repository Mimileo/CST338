package edu.csumb.leon7534.airline;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ManagementSystemActivity extends AppCompatActivity {

    public static final String USERNAME = "edu.csumb.leon7534.airline.username";

    TextView transactionTextView;
    UserConnector mConnector;
    Button contd;

    String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management_system);

        transactionTextView = findViewById(R.id.logTextView);
        // set scroll movement for this textView
        transactionTextView.setMovementMethod(new ScrollingMovementMethod());

        contd = findViewById(R.id.cancelButton);

        Intent intent = getIntent();
        username = intent.getStringExtra(USERNAME);
        Log.i("FLIGHTS", username + "is managing");

        mConnector = mConnector.get(this.getApplicationContext());
        if(mConnector.getLogStrings()!= null) {
            transactionTextView.setText(mConnector.getLogStrings());
        }
        else{
            noLogs();
        }




        contd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               addFlightprompt();
            }
        });
    }
    public void addFlightprompt()
    {

        // Create the object of
        // AlertDialog Builder class
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(ManagementSystemActivity.this);

        // Set the message show for the Alert time
        builder.setMessage("Would you like to add a new Flight?.");

        builder.setTitle("Add Flight");

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

                                Intent someIntent = FlightManagmentActivity.adminIntent(ManagementSystemActivity.this,username);
                                //someIntent.putExtra(USERNAME, username);
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


                                Intent intent = new Intent(ManagementSystemActivity.this, MainActivity.class);
                                startActivity(intent);
                                dialog.cancel();
                            }
                        });

        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();

        // Show the Alert Dialog box
        alertDialog.show();
    }

    public void noLogs()
    {

        // Create the object of
        // AlertDialog Builder class
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(ManagementSystemActivity.this);

        // Set the message show for the Alert time
        builder.setMessage(username + ", no log information to display.");

        // Set Alert Title
        builder.setTitle("No logs");

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

                               addFlightprompt();
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
}

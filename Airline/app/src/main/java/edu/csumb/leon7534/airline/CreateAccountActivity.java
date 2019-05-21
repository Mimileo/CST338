package edu.csumb.leon7534.airline;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
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

import java.util.Set;

import edu.csumb.leon7534.airline.UserDatabase.UserCursorWrapper;

public class CreateAccountActivity extends AppCompatActivity {

    public static final String TAG = "LOGIN_INFO";

    private Button login;
    EditText usernameText;
    EditText passwordText;
    TextView logText;

    UserItem user;
    UserItem tempUser;
    LogTransaction log;
    UserCursorWrapper cursor;

    UserConnector userConnector;
    long rowID, logRowID;
    int invalidName = 0;
    int count =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);


        login = findViewById(R.id.cancelButton);
        usernameText = findViewById(R.id.userNameEditText);
        passwordText = findViewById(R.id.passwordEditText);
        //logText = findViewById(R.id.airlineName2);
        //logText.setMovementMethod(new ScrollingMovementMethod());


        userConnector = UserConnector.get(this.getApplicationContext());
        Log.i(TAG, count+"");


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               ;


                //String username = username.getText().toString();
                if(Utils.isEmpty(usernameText) || Utils.isEmpty(passwordText)) {
                    Log.i(TAG, "No username or password set");
                    Utils.toaster(CreateAccountActivity.this, "Enter both fields", Gravity.TOP);
                }

                else if(validate(usernameText.getText().toString(), passwordText.getText().toString()) == false){
                    count++;
                    Log.i(TAG, "Entered: username: " + usernameText.getText().toString() + " password:  " + passwordText.getText().toString());

                    if(count == 2){
                        failedCreateAccount();
                    } else {
                        Utils.toaster(CreateAccountActivity.this, "Error: Username/password must have at least 3 letters and 1 number", Gravity.TOP);
                        //Log.i(TAG, count + "");
                    }


                }
                else {
                    user = getUserFromDisplay();
                    Log.i(TAG, "AirLine" + user.toString());

                    Log.i(TAG, "Entered: username: " + usernameText.getText().toString()+ " password:  " + passwordText.getText().toString());

                    Log.i(TAG, userConnector.checkUser(user) + "");
                    if(userConnector.checkUser(user)){



                        invalidName++;
                        if(invalidName == 2){
                            takenUsername();
                        } else {
                            Utils.toaster(CreateAccountActivity.this, "Username taken", Gravity.TOP);
                        }
                    }else {

                        // takenCount = 0;
                        Log.i(TAG, "AirLine" + user.toString());
                        //else {
                        rowID = userConnector.addUser(user);
                        Log.i(TAG, "RowID added : " + rowID);
                       // logText.setText(userConnector.getUsers());
                        log = logUserDisplayTransaction();

                        logRowID = userConnector.logTransaction(log);

                        Log.i(TAG, "log rowID: " + logRowID  + "\n"+ log.toString());
                        Log.i(TAG, "Entered: username: " + usernameText.getText().toString()+ " password:  " + passwordText.getText().toString());

                        Utils.toaster(CreateAccountActivity.this,user.getUsername() + " , your Account has been created", Gravity.TOP);


                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent startActivity = new Intent(CreateAccountActivity.this, MainActivity.class);
                                startActivity(startActivity);
                                finish();
                            }
                        }, 4000);
                       // Intent intent = new Intent(CreateAccountActivity.this, MainActivity.class);
                        //startActivity(intent);
                    }

                }
                //rowID = userConnector.addUserItem(user);


            }
        });
    }


    public UserItem getUserFromDisplay(){

        String username = usernameText.getText().toString();
        String password = passwordText.getText().toString();


            UserItem user = new UserItem();

            user.setUsername(username);
            user.setPassword(password);
            user.setTransaction_type("New Account");

        return user;

    }

    public LogTransaction logUserDisplayTransaction(){

        String username = usernameText.getText().toString();
        //String password = passwordText.getText().toString();


        LogTransaction log = new LogTransaction();

        log.setName(username);
        log.setTransaction_type("New Account");


        return log;

    }


    private boolean validate(String username, String password){

        // "/^([a-z0-9]*[a-z]){3}[a-z0-9]*$/i"

        //(.*[a-zA-Z].*){3,}.*[0-9].*
        if(username.matches("(?=(?:.*\\d){1})(?=(?:.*[a-zA-Z]){3})^[a-zA-Z\\d]*$")
                && password.matches("(?=(?:.*\\d){1})(?=(?:.*[a-zA-Z]){3})^[a-zA-Z\\d]*$")){
            return true;
        }
        return false;
    }


    public void failedCreateAccount()
    {

        // Create the object of
        // AlertDialog Builder class
        AlertDialog.Builder builder = new AlertDialog.Builder(CreateAccountActivity.this);
        final AlertDialog alertDialog = builder.create();
        // Set the message show for the Alert
        builder.setMessage("Your username/password did not meet criteria. Username/Password must have at least 3 letters and 1 number");

        // Set Alert Title
        builder.setTitle("Error!");

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

                                // When the user click yes button
                                // then app will close
                                Intent intent = new Intent(CreateAccountActivity.this, MainActivity.class);
                               // finish();
                                startActivity(intent);
                            }
                        });


        /*
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
*/

        // Create the Alert dialog
        AlertDialog someAlert = builder.create();

        // Show the Alert Dialog box
        someAlert.show();
    }

    public void passCreateAccount()
    {

        // Create the object of
        // AlertDialog Builder class
        AlertDialog.Builder builder = new AlertDialog.Builder(CreateAccountActivity.this);
        final AlertDialog alertDialog = builder.create();
        // Set the message show for the Alert
        builder.setMessage(user.toString());

        // Set Alert Title
        builder.setTitle("Account Created for "  + user.getUsername());

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

                        // When the user click yes button
                        // then app will close
                        Intent intent = new Intent(CreateAccountActivity.this, MainActivity.class);
                        // finish();
                        startActivity(intent);
                    }
                });

        /*Set the Negative button with No name

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
        */


        // Create the Alert dialog
        AlertDialog someAlert = builder.create();

        // Show the Alert Dialog box
        someAlert.show();
    }

    public void takenUsername()
    {

        // Create the object of
        // AlertDialog Builder class
        AlertDialog.Builder builder = new AlertDialog.Builder(CreateAccountActivity.this);
        final AlertDialog alertDialog = builder.create();
        // Set the message show for the Alert
        builder.setMessage("The username/password you entered is not available. Going back to Home screen.");

        // Set Alert Title
        builder.setTitle("Error");

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

                        // When the user click yes button
                        // then app will close
                        Intent intent = new Intent(CreateAccountActivity.this, MainActivity.class);
                        // finish();
                        startActivity(intent);
                    }
                });

        /* Set the Negative button with No name

        builder.setNegativeButton(
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
                */


        // Create the Alert dialog
        AlertDialog someAlert = builder.create();

        // Show the Alert Dialog box
        someAlert.show();
    }



}

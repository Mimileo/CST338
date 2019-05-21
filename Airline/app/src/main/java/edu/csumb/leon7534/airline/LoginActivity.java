package edu.csumb.leon7534.airline;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    public static final String USER_TICKETS = "edu.csumb.leon7534.airline.user_tickets";
    public static final String USER_DEPART = "edu.csumb.leon7534.airline.user_depart";
    public static final String USER_ARRIVE = "edu.csumb.leon7534.airline.user_arrive";
    public static final String USER_FLIGHTNO = "edu.csumb.leon7534.airline.user_flight_no";
    public static final String USERNAME = "edu.csumb.leon7534.airline.username";

    private SessionManagament session;



    public static final String TAG = "LOGIN_INFO";
    public static final String TYPE_TAG = "TYPE";


    public static Intent cancelIntent(Context packageContext, String value){
        Intent i = new Intent(packageContext, LoginActivity.class);
        i.putExtra(TYPE_TAG, value);
        return i;
    }

    Button login;
    EditText usernameText;
    EditText password;
    String user;
    String flight_num;
    int tickets;

    String departure;
    String pass;
    String arrival;
    Double amountDue;

    Flight selected;
    String reservationID;
    UserItem tempUser;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor mEditor;

    UserConnector mUserConnector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //session = new SessionManagament(this);

        setContentView(R.layout.activity_login);
        login = findViewById(R.id.cancelButton);
        usernameText = findViewById(R.id.userNameEditText);
        password = findViewById(R.id.passwordEditText);



        mUserConnector = UserConnector.get(getApplicationContext());






        if(getIntent().getExtras() != null ) {


                if(getIntent().getStringExtra(TYPE_TAG) == null ) {
                    flight_num = getIntent().getExtras().getString(USER_FLIGHTNO);
                    selected = mUserConnector.getSelectFlight(flight_num);
                    tickets = getIntent().getExtras().getInt(USER_TICKETS);
                    departure = getIntent().getExtras().getString(USER_DEPART);
                    arrival = getIntent().getExtras().getString(USER_ARRIVE);
                    amountDue = tickets * selected.getPrice();
                    Reservation reservation = new Reservation();
                    reservationID = reservation.getReservationLogId().toString();


                    //Flight selectedFlight = mUserConnector.getSelectFlight(flight_num);
                    Log.i("LOGIN", tickets + " " + "depart: " + flight_num);

                }
                login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        user = usernameText.getText().toString();
                        pass = password.getText().toString();
                        Log.i("USER: ", user + "   password: " + pass);
                        tempUser = getUserDisplay();

                        if (verifyLogin(tempUser)) {
                            Log.i("USER: ", "" + verifyLogin(tempUser));

                            if(getIntent().getStringExtra(TYPE_TAG) != null && getIntent().getStringExtra(TYPE_TAG).equals("Cancel")) {
                                Intent i = UserAccountActivity.viewReservationsIntent(LoginActivity.this, user);
                                startActivity(i);
                            }else {
                                Intent accountIntent = new Intent(LoginActivity.this, BookingActivity.class);
                                accountIntent.putExtra(USERNAME, user);
                                accountIntent.putExtra(USER_TICKETS, tickets);
                                accountIntent.putExtra(USER_DEPART, departure);
                                accountIntent.putExtra(USER_ARRIVE, arrival);
                                accountIntent.putExtra(USER_FLIGHTNO, flight_num);
                                startActivity(accountIntent);
                            }
                            //confirmBooking();

                        }
                    }
                });

        }else{
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    user = usernameText.getText().toString();
                    pass = password.getText().toString();
                    if(mUserConnector.validateAdmin(user, pass)) {

                        Log.i("USER: ", user + "passwoed: " + pass);
                        Intent adminIntent = new Intent(LoginActivity.this, ManagementSystemActivity.class);
                        Bundle admin = new Bundle();
                        admin.putString(USERNAME, user);
                        //admin.putString(PASS, pass);
                        //admin.put("User", )
                        adminIntent.putExtras(admin);
                        startActivity(adminIntent);
                    }
                }
            });

        }
    }


    private void login(){
        String username = usernameText.getText().toString();
        String pass = password.getText().toString();
    }

    public boolean checkAdmin(){
        if (usernameText.getText().toString().equals("admin2") && password.getText().toString().equals("admin2")){
            return true;
        }
        return false;
    }

    public UserItem getUserDisplay(){
        UserItem user = new UserItem();
        String username = usernameText.getText().toString();
        String pass = password.getText().toString();
        user.setUsername(username);
        user.setPassword(pass);
       // user.setTransaction_type("New Account");

        return user;
    }


    public boolean verifyLogin(UserItem user) {
       // Log.i("USER: ", mUserConnector.getUserAccoun + "");

        //UserItem userAccount =;
//        Log.i("LOGIN_USER", userAccount.toString());
        if ( mUserConnector.checkUser(user)) {

           // session.setLoggedIn(true);
            return true;

        }
        Utils.toaster(LoginActivity.this, "Invalid Login", Gravity.TOP);

        return false;

    }

}

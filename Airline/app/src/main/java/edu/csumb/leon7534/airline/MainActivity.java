package edu.csumb.leon7534.airline;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button createAccount;
    Button makeRes;
    Button cancelRes;
    Button manageSystem;
    Button logout;
    private SessionManagament session;

    UserConnector userConnector;
    UserItem user1;
    UserItem user2;
    UserItem user3;
    UserItem user4;


    Flight flight1;
    Flight flight2;
    Flight flight3;
    Flight flight4;
    Flight flight5;



    public static final String USER_INFO = "USER_KEY";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        createAccount = findViewById(R.id.createAccountButton);
        makeRes = findViewById(R.id.cancelButton);
        cancelRes = findViewById(R.id.cancelReservationButton);
        manageSystem = findViewById(R.id.manageButton);




        // conncet to db, then insert default values in db

        userConnector = UserConnector.get(this.getApplicationContext());

        user1 = createUser("alice5", "csumb100", "Create Account");
        user2 = createUser("brian77", "123ABC", "Create Account");
        user3 = createUser("chris21", "CHRIS121", "Create Account");
        user4 = createUser("admin2", "admin2", "Create Account");


        flight1 = createFlight("Otter101", "Monterey", "Los Angeles", "10:00(AM)", 10, 150.00);
        flight2 = createFlight("Otter102","Los Angeles", "Monterey", "1:00(PM)", 10, 150.00);
        flight3 = createFlight("Otter201", "Monterey", "Seattle", "11:00(AM)", 5, 200.50 );
        flight4 = createFlight("Otter205", "Monterey", "Seattle", "3:00(PM)", 15, 150.00 );
        flight5 = createFlight("Otter202", "Seattle", "Monterey", "2:00(PM)", 5, 200.50 );

        if(userConnector.checkUser(user1) != true && userConnector.checkUser(user2) != true && userConnector.checkUser(user3) != true) {
            Log.i("MainDB", "adding users");
            userConnector.addUser(user1);
            userConnector.addUser(user2);
            userConnector.addUser(user3);
            userConnector.addUser(user4);

        }

        if(userConnector.checkFlight(flight1) != true && userConnector.checkFlight(flight2) != true && userConnector.checkFlight(flight3) != true &&
        userConnector.checkFlight(flight4) != true && userConnector.checkFlight(flight5) != true) {
            Log.i("MainDB", "adding flights");
            userConnector.addFlight(flight1);
            userConnector.addFlight(flight2);
            userConnector.addFlight(flight3);
            userConnector.addFlight(flight4);
            userConnector.addFlight(flight5);
        }




        createAccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, CreateAccountActivity.class);
                    startActivity(intent);
                }
        });


        makeRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ReserveSeatActivity.class);
                startActivity(intent);
            }
        });

        cancelRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(MainActivity.this, CancelReservationActivity.class);
                Intent intent = LoginActivity.cancelIntent(MainActivity.this, "Cancel");
                startActivity(intent);
            }
        });

        manageSystem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    public UserItem createUser(String username, String password, String type ){
        UserItem user = new UserItem();

        user.setUsername(username);
        user.setPassword(password);
        user.setTransaction_type(type);
        return user;
    }

    public Flight createFlight(String flight_no, String depart, String arrival, String time, int capacity, double price){
        Flight flight = new Flight();

        flight.setFlight(flight_no, depart, arrival, time, capacity, price );

        return flight;
    }




}

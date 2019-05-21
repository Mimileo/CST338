package edu.csumb.leon7534.airline.UserDatabase;

// Define the user table
public class UserDbSchema {
    // string constants to describe table
    public static final class UserTable{
        public static final String NAME = "Users";

        // the columns
        public static final class Cols {
            public static final String UUID       =  "uuid";
            public static final String USERNAME       =  "username";
            public static final String PASSWORD       =  "password";
            public static final String TRANSACTION_TYPE       =  "transaction_type";
            public static final String DATE       =  "date";
        }
    }

    public static final class FlightTable{
        public static final String NAME = "Flight";

        // the columns
        public static final class Cols {
            public static final String UUID       =  "uuid";
            public static final String FLIGHT_NO       =  "flight_no";
            public static final String DEPART_LOC       =  "departure_location";
            public static final String ARRIVAL       =  "arrival";
            public static final String DEPART_TIME       =  "departure_time";
            public static final String CAPACITY       =  "capacity";
            public static final String PRICE       =  "price";
        }
    }

    public static final class ReservationTable{
        public static final String NAME = "Reservation";

        // the columns
        public static final class Cols {
            public static final String UUID       =  "uuid";
            public static final String FLIGHT_NO       =  "flight_no";
            public static final String DEPART_LOC       =  "departure_location";
            public static final String ARRIVAL       =  "arrival";
            public static final String DEPART_TIME      =  "departure_time";
            public static final String LOG_DATE       =  "logDate";
            public static final String TICKETS       =  "tickets";
            public static final String TOTAL_AMOUNT = "total_amount";
            public static final String USERNAME       =  "username";
            public static final String STATUS       =  "status";
        }
    }

    public static final class LogTransactionTable{
        public static final String NAME = "LogTransaction";

        // the columns
        public static final class Cols {
            public static final String UUID       =  "uuid";
            public static final String NAME       =  "username";
            public static final String TRANSACTION_TYPE       =  "transaction_type";
            public static final String FLIGHT_NO       =  "flight_no";
            public static final String DEPART_LOC       =  "departure_location";
            public static final String ARRIVAL       =  "arrival";
            public static final String DEPART_TIME       =  "departure_time";
            public static final String TICKETS       =  "tickets";
            public static final String RESERVATION_ID       =  "reservation_id";
            public static final String TOTAL_AMOUNT       =  "total_amount";
            public static final String LOG_DATE       =  "logDate";






        }
    }

}

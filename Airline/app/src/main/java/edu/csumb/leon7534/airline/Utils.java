package edu.csumb.leon7534.airline;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.Toast;

import java.util.logging.LogRecord;

public class Utils {
    static boolean isEmpty(EditText check){
        return check.getText().toString().trim().length() == 0;

    }

    static void toaster(Context context, String mString, int gravity){
        Toast t = Toast.makeText(context, mString,Toast.LENGTH_LONG );
        t.setGravity(gravity, 0, 0 );
        t.show();
    }

    static void toaster(Context context, int str, int gravity){
        Toast t = Toast.makeText(context, str,Toast.LENGTH_LONG );
        t.setGravity(gravity, 0, 0 );
        t.show();
    }

    static void toaster(Context context, int str){
        Toast t = Toast.makeText(context, str,Toast.LENGTH_LONG );
        t.setGravity(Gravity.TOP, 0, 0 );
        t.show();
    }



}

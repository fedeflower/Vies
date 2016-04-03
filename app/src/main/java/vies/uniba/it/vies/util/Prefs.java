package vies.uniba.it.vies.util;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Federico on 03/04/2016.
 */
public class Prefs {
    private static final String PREFS_NAME = "ViesGeneralPrefs";

    private static SharedPreferences myObj=null;

    private static void create(AppCompatActivity app){
        myObj = app.getApplicationContext().getSharedPreferences(PREFS_NAME, 0);
    }

    public static SharedPreferences getInstance(AppCompatActivity app){
        if(myObj==null){
            create(app);
        }

        return myObj;
    }
}

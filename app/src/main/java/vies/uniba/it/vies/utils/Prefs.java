package vies.uniba.it.vies.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Federico on 03/04/2016.
 */
public class Prefs {
    private static final String PREFS_NAME = "ViesGeneralPrefs";

    private static SharedPreferences myObj=null;

    private static void create(Context context){
        myObj = context.getSharedPreferences(PREFS_NAME, 0);
    }

    public static SharedPreferences getInstance(Context context){
        if(myObj==null){
            create(context);
        }

        return myObj;
    }
}

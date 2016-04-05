package vies.uniba.it.vies.utils;

import android.app.Application;
import android.content.Context;

/**
 * Created by Marco on 4/5/2016.
 */
public class App extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext(){
        return mContext;
    }
}
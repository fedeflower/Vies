package vies.uniba.it.vies.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vies.uniba.it.vies.R;
import vies.uniba.it.vies.model.Album;
import vies.uniba.it.vies.model.Photo;

public class LaunchScreenActivity extends AppCompatActivity {

    private static final int SPLASH_TIME = 3000;
    public static List<Photo> photos;
    public static List<LatLng> pos = new ArrayList<>();
    public static Map<String,List<LatLng>> map=new HashMap<String,List<LatLng>>();
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        Transparent Status Bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.activity_launch_screen);

        //CAMBIO COLORE BARRA PROGRESSBAR DI CARICAMENTO
        pb = (ProgressBar) findViewById(R.id.pb);
        pb.getIndeterminateDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        new BackgroundTask().execute();
    }


    private class BackgroundTask extends AsyncTask {
        Intent intent;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            intent = new Intent(LaunchScreenActivity.this, MainActivity.class);
        }

        @Override
        protected Object doInBackground(Object[] params) {

            /*  Use this method to load background
            * data that your app needs. */

            photos = Album.getList("BARI");
            for (Photo photo : photos) {
                pos.add(photo.getLocation().getLatLng());
            }
            List<LatLng> posbari = new ArrayList<>();
            map.put("BARI", new ArrayList<LatLng>(pos));
            pos.clear();
            photos = Album.getList("ROMA");
            for (Photo photo : photos) {
                pos.add(photo.getLocation().getLatLng());
            }
            map.put("ROMA", new ArrayList<LatLng>(pos));
            pos.clear();
            photos = Album.getList("PARIGI");
            for (Photo photo : photos) {
                pos.add(photo.getLocation().getLatLng());
            }
            map.put("PARIGI", new ArrayList<LatLng>(pos));
            pos.clear();
            try {
                Thread.sleep(SPLASH_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
//            Pass your loaded data here using Intent

//            intent.putExtra("data_key", "");
            startActivity(intent);
            finish();
        }
    }
}

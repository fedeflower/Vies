package vies.uniba.it.vies.activity;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import vies.uniba.it.vies.R;
import vies.uniba.it.vies.database.DBHelper;
import vies.uniba.it.vies.fragment.GalleryFragment;
import vies.uniba.it.vies.fragment.MapFragment;
import vies.uniba.it.vies.model.Album;
import vies.uniba.it.vies.utils.App;
import vies.uniba.it.vies.utils.viesAlert;

public class WorldActivity extends AppCompatActivity {
    MapView mMapView;
    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_world);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("ViesMap");
        getSupportActionBar().setSubtitle("Le mie foto intorno al mondo");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mMapView = (MapView) findViewById(R.id.worldMap);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();// needed to get the map to display immediately

        try {
            MapsInitializer.initialize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        googleMap = mMapView.getMap();
        List<LatLng> pos=null;

        Log.d("Comments", "1");
        pos= DBHelper.getInstance(this).getAllCoord();

        if(!pos.isEmpty()) {
            for (LatLng posz : pos) {

                googleMap.addMarker(new MarkerOptions().position(posz).title("Marker"));
            }
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos.get(0), 0));
            //googleMap.addPolyline(new PolylineOptions().addAll(pos));
        }
        else{
                Toast.makeText(App.getContext(), "Nessun GeoTag presente.", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cerca, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.openMap:{
                viesAlert.openAlert(this);
                return true;}
        }
        return super.onOptionsItemSelected(item);
    }

}

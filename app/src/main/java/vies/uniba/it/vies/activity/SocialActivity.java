package vies.uniba.it.vies.activity;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import vies.uniba.it.vies.R;
import vies.uniba.it.vies.adapter.GalleryAdapter;
import vies.uniba.it.vies.adapter.RecyclerItemClickListener;
import vies.uniba.it.vies.database.DBHelper;
import vies.uniba.it.vies.fragment.GalleryFragment;
import vies.uniba.it.vies.fragment.MapFragment;
import vies.uniba.it.vies.model.Album;
import vies.uniba.it.vies.model.ImageModel;
import vies.uniba.it.vies.utils.App;
import vies.uniba.it.vies.utils.Utils;
import vies.uniba.it.vies.utils.viesAlert;

public class SocialActivity extends AppCompatActivity {

    MapView mMapView;
    private GoogleMap googleMap;
    private RecyclerView recyclerView;
    private ArrayList<ImageModel> data = new ArrayList<>();
    private List<LatLng> pos=new ArrayList<>();
    String album_name;
    String album_location;
    private Marker lastMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social);

        album_name = getIntent().getStringExtra("album_name");
        album_location = getIntent().getStringExtra("album_location");

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Social");
        //getSupportActionBar().setSubtitle(album_location);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mMapView = (MapView) findViewById(R.id.mapView);

        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();// needed to get the map to display immediately

        try {
            MapsInitializer.initialize(this.getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        googleMap = mMapView.getMap();

        pos = DBHelper.getInstance(this).getCoord(album_location.toUpperCase());

        if (pos != null) {
            for (LatLng posz : pos) {
                googleMap.addMarker(new MarkerOptions().position(posz).title("Marker"));
            }
        }
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos.get(0), 12));

        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                @Override
                public void onMapLongClick(LatLng latLng) {
                    if (lastMarker != null) lastMarker.remove();
                    data.clear();
                    lastMarker = googleMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                    TextView tx = (TextView) findViewById(R.id.bottom_text);
                    String Lat = "Lat:" + latLng.latitude;
                    String Long = "Long:" + latLng.longitude;
                    tx.setText(Lat.substring(0, 12) + " - " + Long.substring(0, 12));

                    String IMGS[] = {
                            "https://dl.dropboxusercontent.com/u/66373804/VIES/Parigi-Louvre.jpg",
                            "https://dl.dropboxusercontent.com/u/66373804/VIES/Parigi-Monnalisa.jpg",
                            "https://dl.dropboxusercontent.com/u/66373804/VIES/Parigi-Montmartre.jpg",
                            "https://dl.dropboxusercontent.com/u/66373804/VIES/Parigi-Notre_Dame.jpg",
                            "https://dl.dropboxusercontent.com/u/66373804/VIES/Parigi-Torre_Eiffel.jpg",
                            "https://dl.dropboxusercontent.com/u/66373804/VIES/Parigi-Arco_di_Trionfo.jpg"};

                    int random_volte = new Random().nextInt(IMGS.length);

                    for (int i = 0; i < random_volte; i++) {
                        int random_no = new Random().nextInt(IMGS.length);
                        ImageModel imageModel = new ImageModel();
                        imageModel.setName(Utils.getNameFileFromUrl(IMGS[random_no]));
                        imageModel.setUrl(IMGS[random_no]);
                        data.add(imageModel);
                    }

                    GalleryAdapter adapter = new GalleryAdapter(getBaseContext(), data);
                    recyclerView.setAdapter(adapter);

                    Toast.makeText(getBaseContext(), "Trovate " + random_volte + " foto nei dintorni.",
                            Toast.LENGTH_LONG).show();
                }
            });

            final FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frame);

            recyclerView = (RecyclerView) findViewById(R.id.social_recycler);
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2)); //numero riquadri
            recyclerView.setHasFixedSize(true);


            recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,
                    new RecyclerItemClickListener.OnItemClickListener() {

                        @Override
                        public void onItemClick(View view, int position) {


                            Animation animFadein = AnimationUtils.loadAnimation(view.getContext(), R.anim.fade_in);
                            Animation animFadeout = AnimationUtils.loadAnimation(view.getContext(), R.anim.fade_out);
                            recyclerView.getLayoutManager().findViewByPosition(position).startAnimation(animFadeout);
                            recyclerView.getLayoutManager().findViewByPosition(position).startAnimation(animFadein);


                            Intent intent = new Intent(view.getContext(), DetailPhotoActivity.class);
                            intent.putParcelableArrayListExtra("data", data);
                            intent.putExtra("album_name", "Social");
                            intent.putExtra("pos", position);
                            startActivity(intent);

                        }
                    }));

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

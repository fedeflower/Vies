package vies.uniba.it.vies.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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
import vies.uniba.it.vies.model.Album;
import vies.uniba.it.vies.model.ImageModel;
import vies.uniba.it.vies.utils.App;
import vies.uniba.it.vies.utils.Utils;
import vies.uniba.it.vies.utils.viesAlert;

public class SocialActivityVero extends AppCompatActivity {
    RecyclerView recyclerView;
    MapView mMapView;
    private GoogleMap googleMap;
    String album_name;
    String album_location;
    private ArrayList<ImageModel> data = new ArrayList<>();
    private List<LatLng> pos=new ArrayList<>();
    boolean showFAB = true;
    private Marker lastMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trending);

        album_name = getIntent().getStringExtra("album_name");
        album_location = getIntent().getStringExtra("album_location");

        final Toolbar toolbar = (Toolbar) findViewById(R.id.gmail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("ViesSocial");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mMapView = (MapView) findViewById(R.id.trendingMap);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();// needed to get the map to display immediately

        try {
            MapsInitializer.initialize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        googleMap = mMapView.getMap();

        recyclerView = (RecyclerView) findViewById(R.id.trending_recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3)); //numero riquadri
        recyclerView.setHasFixedSize(true);

        pos = DBHelper.getInstance(this).getCoord(album_location.toUpperCase());


        if (pos != null) {
            for (LatLng posz : pos) {
                googleMap.addMarker(new MarkerOptions().position(posz).title("Marker"));
            }
        }
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos.get(0), 12));


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

        final TextView bottom=(TextView) findViewById(R.id.bottom_text);


        GalleryAdapter adapter = new GalleryAdapter(getBaseContext(), data);
        recyclerView.setAdapter(adapter);


        // To handle FAB animation upon entrance and exit
        final Animation growAnimation = AnimationUtils.loadAnimation(this, R.anim.simple_grow);
        final Animation shrinkAnimation = AnimationUtils.loadAnimation(this, R.anim.simple_shrink);
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.gmail_coordinator);
        View bottomSheet = coordinatorLayout.findViewById(R.id.gmail_bottom_sheet);

        final BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.gmail_fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(behavior.getState()==behavior.STATE_COLLAPSED){
                    fab.startAnimation(shrinkAnimation);
                    behavior.setState(behavior.STATE_EXPANDED);

                } else {
                    behavior.setState(behavior.STATE_COLLAPSED);
                    fab.startAnimation(shrinkAnimation);
                }
            }
        });

        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                if (lastMarker != null) lastMarker.remove();
                data.clear();
                lastMarker = googleMap.addMarker(new MarkerOptions().position(latLng).
                        icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                TextView tx = (TextView) findViewById(R.id.found_text);
                String Lat = "Lat:" + latLng.latitude;
                String Long = "Long:" + latLng.longitude;
                bottom.setText("Nelle vicinanze di "+Lat.substring(0, 11) + " - " + Long.substring(0, 12));


                String IMGS[] = Album.SOCIAL;

                int random_volte = new Random().nextInt(6);

                for (int i = 0; i < random_volte; i++) {
                    int random_no = new Random().nextInt(IMGS.length);
                    ImageModel imageModel = new ImageModel();
                    imageModel.setName(Utils.getNameFileFromUrl(IMGS[random_no]));
                    imageModel.setUrl(IMGS[random_no]);
                    data.add(imageModel);
                }

                GalleryAdapter adapter = new GalleryAdapter(getBaseContext(), data);
                recyclerView.setAdapter(adapter);
                tx.setText("Guarda "+random_volte + " scatti di altri utenti...");

                Toast.makeText(getBaseContext(), "Trovate " + random_volte + " foto nei dintorni.",
                        Toast.LENGTH_LONG).show();


/* DA RICONTROLLARE
                if (behavior.getState() == behavior.STATE_COLLAPSED) {
                    fab.startAnimation(shrinkAnimation);
                    behavior.setState(behavior.STATE_EXPANDED);
                }*/
            }
        });

        fab.setRotation(0);
        fab.setVisibility(View.VISIBLE);
        fab.startAnimation(growAnimation);


        shrinkAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                fab.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

                switch (newState) {

                    case BottomSheetBehavior.STATE_DRAGGING:
                        if (showFAB)
                            fab.startAnimation(shrinkAnimation);
                        break;

                    case BottomSheetBehavior.STATE_COLLAPSED:
                        showFAB = true;
                        fab.setVisibility(View.VISIBLE);
                        fab.setRotation(0);
                        fab.startAnimation(growAnimation);
                        break;

                    case BottomSheetBehavior.STATE_EXPANDED:
                        showFAB = true;
                        fab.setVisibility(View.VISIBLE);
                        fab.setRotation(180);
                        fab.startAnimation(growAnimation);
                        break;


                }

            }

            @Override
            public void onSlide(View bottomSheet, float slideOffset) {

            }
        });

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
                supportFinishAfterTransition();
                return true;

                    case R.id.openMap:{
                        viesAlert.openAlert(this);
                        return true;}
                }


        return super.onOptionsItemSelected(item);
    }
}



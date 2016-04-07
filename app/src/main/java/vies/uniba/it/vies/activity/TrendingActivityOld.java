package vies.uniba.it.vies.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import vies.uniba.it.vies.R;
import vies.uniba.it.vies.adapter.GalleryAdapter;
import vies.uniba.it.vies.adapter.RecyclerItemClickListener;
import vies.uniba.it.vies.model.Album;
import vies.uniba.it.vies.model.ImageModel;
import vies.uniba.it.vies.utils.App;
import vies.uniba.it.vies.utils.Utils;

public class TrendingActivityOld extends AppCompatActivity {
    MapView mMapView;
    private GoogleMap googleMap;
    private RecyclerView recyclerView;
    private ArrayList<ImageModel> data = new ArrayList<>();

    String [] coords={
            "31.2243084,120.9162742",
            "39.9390731,116.1172682",
            "41.0055005,28.7319893",
            "35.6735408,139.5703019",
            "55.74929,37.0720805",
            "-23.6815315,-46.8754945",
            "22.5555518,113.7736804",
            "37.5652894,126.8494641",
            "30.0596185,31.1884238",
            "19.3910038,-99.2837002",
            "40.7058316,-74.2581938",
            "51.5287352,-0.3817821",
            "10.769,106.4141616",
            "22.3580723,113.8408197",
            "-22.9103552,-43.7285297",
            "1.3150701,103.7069311",
            "34.0207504,-118.6919263",
            "35.4620696,139.5492096",
            "35.1646501,128.9317138",
            "52.507629,13.1449537",
            "40.4381311,-3.8196221",
            "40.3915658,-3.6560971",
            "41.9102415,12.3959126",
            "45.4628329,9.1076921"
    };

    String [] foto = Album.TRENDING;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trending_old);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Trending");
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
        List<LatLng> pos=new ArrayList<>();

        int random= new Random().nextInt(3)+3;

        for(int i=0;i<random;i++) {
            int random_no = new Random().nextInt(coords.length);
            String img = (coords[random_no]);

            int random_foto = new Random().nextInt(foto.length);

            pos.add(new LatLng(Double.parseDouble(img.split(",")[0]), Double.parseDouble(img.split(",")[1])));
            ImageModel imageModel = new ImageModel();
            imageModel.setName(Utils.getNameFileFromUrl(foto[random_foto]));
            imageModel.setUrl(foto[random_foto]);
            data.add(imageModel);
        }

        Toast.makeText(App.getContext(), "Ci sono "+random+" trending foto.", Toast.LENGTH_LONG).show();

        if(pos!=null) {
            for (LatLng posz : pos) {

                googleMap.addMarker(new MarkerOptions().position(posz).title("Marker"));
            }
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos.get(0), 0));
        }
        else{
                Toast.makeText(App.getContext(), "Nessun GeoTag presente.", Toast.LENGTH_LONG).show();
        }

        recyclerView = (RecyclerView) findViewById(R.id.trending_recycler);
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
                        intent.putExtra("album_name", "Trending Now");
                        intent.putExtra("pos", position);
                        startActivity(intent);
                    }
                }));

        GalleryAdapter adapter = new GalleryAdapter(getBaseContext(), data);
        recyclerView.setAdapter(adapter);

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

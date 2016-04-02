package vies.uniba.it.vies.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import vies.uniba.it.vies.R;
import vies.uniba.it.vies.adapter.PhotoAdapter;
import vies.uniba.it.vies.adapter.TravelAdapter;
import vies.uniba.it.vies.model.Photo;

public class TravelDetailActivity extends AppCompatActivity {

    private List<Photo> photos = new ArrayList<Photo>();
    private PhotoAdapter photoAdapter;
    private RecyclerView photosRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_detail);

        /*Integer travelId = this.getIntent().getExtras().getInt("travelID");
        // TODO: user travelID in order to retrieve the photos' travel
        Log.d("test", "travel ID = " + travelId);*/
        initFakePhotos(1);

        photosRecyclerView = (RecyclerView) findViewById(R.id.photosRecyclerView);

        photoAdapter = new PhotoAdapter(photos);
        photosRecyclerView.setAdapter(photoAdapter);
        photosRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        final FloatingActionButton newTravelButton = (FloatingActionButton) findViewById(R.id.saveTravelButton);
        newTravelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                Intent i = new Intent(getApplicationContext(), TravelManagementActivity.class);
                startActivity(i);
            }
        });
    }

    private void initFakePhotos(Integer n) {

        for (int i = 0; i < n; i++) {
            photos.add(new Photo());
        }

    }

}

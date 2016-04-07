package vies.uniba.it.vies.fragment;

/**
 * Created by Marco on 4/5/2016.
 */

import android.annotation.SuppressLint;
import android.location.LocationManager;
import android.os.Bundle;
        import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
        import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.MapView;
        import com.google.android.gms.maps.MapsInitializer;
        import com.google.android.gms.maps.model.BitmapDescriptorFactory;
        import com.google.android.gms.maps.model.CameraPosition;
        import com.google.android.gms.maps.model.LatLng;
        import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

import vies.uniba.it.vies.R;
import vies.uniba.it.vies.activity.LaunchScreenActivity;
import vies.uniba.it.vies.database.DBHelper;
import vies.uniba.it.vies.model.Album;
import vies.uniba.it.vies.model.Location;
import vies.uniba.it.vies.model.Photo;

/**
 * A fragment that launches other parts of the demo application.
 */
public class MapFragment extends Fragment {

    MapView mMapView;
    private GoogleMap googleMap;
    String album_location;
    private List<Photo> photos;
    public static boolean click=false;
    public static boolean noTag=false;


    public static void setClick() {
        click=true;
    }

    public static boolean getnoTag(){return noTag;}
    public MapFragment() {
    }

    @SuppressLint("ValidFragment")
    public MapFragment(String album_location) {
        this.album_location=album_location;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflat and return the layout
        View v = inflater.inflate(R.layout.fragment_map, container,
                false);

        mMapView = (MapView) v.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();// needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        googleMap = mMapView.getMap();
        List<LatLng> pos=null;
        switch(album_location.toUpperCase()){
            case "BARI":{
                pos= DBHelper.getInstance(getContext()).getCoord("BARI");
                //pos=LaunchScreenActivity.map.get("BARI");
                break;
            }
            case "ROMA":{
                pos= DBHelper.getInstance(getContext()).getCoord("ROMA");
                //pos=LaunchScreenActivity.map.get("ROMA");
                break;
            }
            case "PARIGI":{
                pos= DBHelper.getInstance(getContext()).getCoord("PARIGI");
                //pos=LaunchScreenActivity.map.get("PARIGI");
                break;
            }
            default:{

                break;
            }
        }

        if(pos!=null) {
            for (LatLng posz : pos) {
                noTag=false;
                googleMap.addMarker(new MarkerOptions().position(posz).title("Marker"));
            }
            googleMap.addPolyline(new PolylineOptions().addAll(pos));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos.get(0), 12));
        }
        else{
            noTag=true;

        }
        /*if(click=true) {
            photos = Album.getList("BARI");
            for (Photo photo : photos) {
                googleMap.addMarker(new MarkerOptions().position(photo.getLocation().getLatLng()).title("Marker"));
            }
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(photos.get(0).getLocation().getLatLng(), 12));
        }*/
        /*
        // latitude and longitude
        double latitude = 17.385044;
        double longitude = 78.486671;

        // create marker
        MarkerOptions marker = new MarkerOptions().position(
                new LatLng(latitude, longitude)).title("Hello Maps");

        // Changing marker icon
        marker.icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

        // adding marker
        googleMap.addMarker(marker);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(17.385044, 78.486671)).zoom(12).build();
        googleMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));
        */
        // Perform any camera updates here
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}
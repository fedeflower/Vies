package vies.uniba.it.vies.fragment;

/**
 * Created by Marco on 4/5/2016.
 */

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import vies.uniba.it.vies.R;
import vies.uniba.it.vies.activity.DetailPhotoActivity;
import vies.uniba.it.vies.activity.LaunchScreenActivity;
import vies.uniba.it.vies.database.DBHelper;
import vies.uniba.it.vies.model.Album;
import vies.uniba.it.vies.model.Location;
import vies.uniba.it.vies.model.Photo;
import vies.uniba.it.vies.utils.App;
import vies.uniba.it.vies.utils.Utils;

/**
 * A fragment that launches other parts of the demo application.
 */
public class MapFragment extends Fragment  implements GoogleMap.OnMarkerClickListener {

    MapView mMapView;
    private GoogleMap googleMap;
    String album_location;
    private List<Photo> photos;
    public static boolean click=false;
    public static boolean noTag=false;
    List <Marker> listaMarker = new ArrayList<>();
    private boolean zoom=false;
    List<LatLng> pos;


    public static void setClick() {
        click=true;
    }

    public static String IMGS[]={}; //FF

    public static boolean getnoTag(){return noTag;}
    public MapFragment() {
    }
    private Marker myMarker;

    @SuppressLint("ValidFragment")
    public MapFragment(String album_location) {
        this.album_location=album_location;
        IMGS=Album.getAlbum(album_location); //FF
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
        googleMap = mMapView.getMap();



        googleMap.setOnMarkerClickListener(this);

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }



        //List<LatLng> pos=null;
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
        //Log.w("Comments","PosSize: "+pos.size());
        int i=0;
        if(pos!=null) {
            for (LatLng posz : pos) {
                Log.w("Comments","Index "+i);
                noTag=false;
                //googleMap.addMarker(new MarkerOptions().position(posz).title("Marker"));
                Marker gino = googleMap.addMarker(new MarkerOptions().position(posz).title(Utils.getNameFileFromUrl(IMGS[i])));
                listaMarker.add(gino);//FF
                i++;
            }
            googleMap.addPolyline(new PolylineOptions().addAll(pos).color(Color.rgb(33,150,243)).width(20));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos.get(0), 12));
            zoom=false;
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
        if(!zoom){
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pos.get(0), 12));
            zoom=true;}
        if(DetailPhotoActivity.mapClick==true) {
            DetailPhotoActivity.mapClick=false;
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(DetailPhotoActivity.posizione, 12));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(DetailPhotoActivity.posizione, 15));
        }
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

    @Override
    public boolean onMarkerClick(Marker marker) {

        if (marker.equals(myMarker)) {
            //handle click here

            Intent intent = new Intent(getActivity(), DetailPhotoActivity.class);
            intent.putParcelableArrayListExtra("data", GalleryFragment.data);
            intent.putExtra("pos",  listaMarker.lastIndexOf(myMarker));
            intent.putExtra("album_location",album_location);
            startActivity(intent);
        }
        myMarker = marker;
        return false;

    }

}
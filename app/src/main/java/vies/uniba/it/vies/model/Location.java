package vies.uniba.it.vies.model;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Daniele on 27/03/2016.
 */
public class Location {

    private String name;
    private LatLng latLng;

    public Location() {    }

    public Location(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LatLng getLatLng() {
            return latLng;
        }

    public void setLatLng(LatLng latLng) {
            this.latLng = latLng;
        }

}

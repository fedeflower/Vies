package vies.uniba.it.vies.model;

/**
 * Created by Daniele on 27/03/2016.
 */
public class Location {

    private String name;
    private Double latitude;
    private Double longitude;

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

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}

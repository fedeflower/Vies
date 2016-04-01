package vies.uniba.it.vies.model;

import android.location.Location;

/**
 * Created by Daniele on 27/03/2016.
 */
public class Photo {

    private byte[] img;
    private Location location;
    private String note;

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}

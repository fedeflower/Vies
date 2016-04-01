package vies.uniba.it.vies.model;


import android.os.Parcelable;

import java.util.Date;
import java.util.List;

import vies.uniba.it.vies.util.DateUtils;

/**
 * Created by Daniele on 27/03/2016.
 */
public class Travel {

    private Integer id;
    private String name;
    private long dateOut;
    private long dateIn;
    private Location location;
    private List<Photo> photos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDateOut() {
        return dateOut;
    }

    public void setDateOut(long dateOut) {
        this.dateOut = dateOut;
    }

    public long getDateIn() {
        return dateIn;
    }

    public void setDateIn(long dateIn) {
        this.dateIn = dateIn;
    }

    public Location getLocation() {
        if(location == null) {
            location = new Location();
        }
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public String getFullDate() {
        return new StringBuilder()
                .append(DateUtils.formatLong(getDateOut()))
                .append(" - ")
                .append(DateUtils.formatLong(getDateIn()))
                .toString();
    }

    @Override
    public String toString() {
        return "Travel{" +
                "name='" + name + '\'' +
                ", dateOut=" + dateOut +
                ", dateIn=" + dateIn +
                ", location=" + location +
                ", photos=" + photos +
                '}';
    }
}

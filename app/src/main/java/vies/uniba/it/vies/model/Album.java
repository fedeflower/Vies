package vies.uniba.it.vies.model;

import android.util.Log;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vies.uniba.it.vies.R;
import vies.uniba.it.vies.utils.App;

/**
 * Created by Marco on 4/4/2016.
 */

public class Album {
    public static String ALBUMS[]={"BARI","PARIGI","ROMA"};
    public static String BARI[] = {
            "https://dl.dropboxusercontent.com/u/66373804/VIES/Bari-Lungomare.jpg",
            "https://dl.dropboxusercontent.com/u/66373804/VIES/Bari-Petruzzelli.jpg",
            "https://dl.dropboxusercontent.com/u/66373804/VIES/Bari-Basilica.jpg",
            "https://dl.dropboxusercontent.com/u/66373804/VIES/Bari-Castello.jpg"};
    public static String BARILAT[] = {
            "41.12241388888889","41.12353888888889","41.13014444444444","41.12911111111111"};
    public static String BARILONG[] = {
            "16.879466666666666","16.872566666666668","16.870194444444444","16.866166666666665"};

    public static String PARIGI[] = {
    "https://dl.dropboxusercontent.com/u/66373804/VIES/Parigi-Louvre.jpg",
    "https://dl.dropboxusercontent.com/u/66373804/VIES/Parigi-Monnalisa.jpg",
    "https://dl.dropboxusercontent.com/u/66373804/VIES/Parigi-Montmartre.jpg",
    "https://dl.dropboxusercontent.com/u/66373804/VIES/Parigi-Notre_Dame.jpg",
    "https://dl.dropboxusercontent.com/u/66373804/VIES/Parigi-Torre_Eiffel.jpg",
    "https://dl.dropboxusercontent.com/u/66373804/VIES/Parigi-Arco_di_Trionfo.jpg"};
    public static String PARIGILAT[] = {
            "48.86114444444444","48.86103055555555","48.88610833333333","48.85272777777778","48.85836944444445","48.87378055555555"};
    public static String PARIGILONG[] = {
            "2.3354833333333334","2.3359666666666667","2.343288888888889","2.350563888888889","2.2944805555555554","2.2950166666666667"};

    public static String ROMA[] = {
           "https://dl.dropboxusercontent.com/u/66373804/VIES/Roma-Piazza_Navona.jpg",
            "https://dl.dropboxusercontent.com/u/66373804/VIES/Roma-Piazza_di_Spagna.jpg",
            "https://dl.dropboxusercontent.com/u/66373804/VIES/Roma-San_Pietro.jpg",
            "https://dl.dropboxusercontent.com/u/66373804/VIES/Roma-Colosseo.jpg",
            "https://dl.dropboxusercontent.com/u/66373804/VIES/Roma-Fontana_di_Trevi.jpg",
            "https://dl.dropboxusercontent.com/u/66373804/VIES/Roma-Fori_Imperiali.jpg"};
    public static String ROMALAT[] = {
            "41.89916111111111","41.906288888888895","41.90226944444445","41.89113611111111","41.90093333333333","41.89187777777778"};
    public static String ROMALONG[] = {
            "12.473072222222221","12.4815","12.458797222222222","12.4919","12.483036111111112","12.484030555555556"};

    public static String DEF[] = {
            "https://dl.dropboxusercontent.com/u/66373804/VIES/Cuccioli%20(6).jpg",
            "https://dl.dropboxusercontent.com/u/66373804/VIES/Cuccioli%20(2).jpg",
            "https://dl.dropboxusercontent.com/u/66373804/VIES/Cuccioli%20(3).jpg",
            "https://dl.dropboxusercontent.com/u/66373804/VIES/Cuccioli%20(4).jpg",
            "https://dl.dropboxusercontent.com/u/66373804/VIES/Cuccioli%20(5).jpg",
            "https://dl.dropboxusercontent.com/u/66373804/VIES/Cuccioli%20(1).jpg",
            "https://dl.dropboxusercontent.com/u/66373804/VIES/Cuccioli%20(7).jpg",
            "https://dl.dropboxusercontent.com/u/66373804/VIES/Cuccioli%20(8).jpg"
            };

    public static String TRENDING[] = {
            "https://dl.dropboxusercontent.com/u/66373804/VIES/trending%20(1).jpg",
            "https://dl.dropboxusercontent.com/u/66373804/VIES/trending%20(2).jpg",
            "https://dl.dropboxusercontent.com/u/66373804/VIES/trending%20(3).jpg",
            "https://dl.dropboxusercontent.com/u/66373804/VIES/trending%20(4).jpg",
            "https://dl.dropboxusercontent.com/u/66373804/VIES/trending%20(5).jpg",
            "https://dl.dropboxusercontent.com/u/66373804/VIES/trending%20(6).jpg",
            "https://dl.dropboxusercontent.com/u/66373804/VIES/trending%20(7).jpg",
            "https://dl.dropboxusercontent.com/u/66373804/VIES/trending%20(8).jpg",
            "https://dl.dropboxusercontent.com/u/66373804/VIES/trending%20(9).jpg",
            "https://dl.dropboxusercontent.com/u/66373804/VIES/trending%20(10).jpg"
    };

    public static String SOCIAL[] = {
            "https://dl.dropboxusercontent.com/u/66373804/VIES/social%20(1).jpg",
            "https://dl.dropboxusercontent.com/u/66373804/VIES/social%20(2).jpg",
            "https://dl.dropboxusercontent.com/u/66373804/VIES/social%20(3).jpg",
            "https://dl.dropboxusercontent.com/u/66373804/VIES/social%20(4).jpg",
            "https://dl.dropboxusercontent.com/u/66373804/VIES/social%20(5).jpg",
            "https://dl.dropboxusercontent.com/u/66373804/VIES/social%20(6).jpg",
            "https://dl.dropboxusercontent.com/u/66373804/VIES/social%20(7).jpg",
            "https://dl.dropboxusercontent.com/u/66373804/VIES/social%20(8).jpg",
            "https://dl.dropboxusercontent.com/u/66373804/VIES/social%20(9).jpg",
            "https://dl.dropboxusercontent.com/u/66373804/VIES/social%20(10).jpg"

    };

    public static List<String> tags = Arrays.asList(App.getContext().getResources().getStringArray(R.array.tags));
    public static Map<String, String> values = new HashMap<String, String>();

    public static List<Photo> getList(String str) {
        List<Photo> photos = new ArrayList<Photo>();
        String IMGS[] = {};
        IMGS=getAlbum(str);

        /*switch (str.toUpperCase()) {
            case "BARI": {
                IMGS = Album.BARI;
                break;
            }
            case "ROMA": {
                IMGS = Album.ROMA;
                break;
            }
            case "PARIGI": {
                IMGS = Album.PARIGI;
                break;
            }
            default: {
                IMGS = Album.DEF;
                break;
            }
        }*/

        for (int i = 0; i < IMGS.length; i++) {
            Photo photo = new Photo();
            photo.setImg(IMGS[i].getBytes());
            Location location = new Location();
            location.setLatLng(getLatLngFromUrl(IMGS[i]));
            location.setName("test");
            photo.setLocation(location);
            photos.add(photo);
            Log.d("Comments", "foto"+i+": "+photo.getLocation().getLatLng());
        }


        return photos;
    }

    ;

    public static LatLng getLatLngFromUrl(String Url) {
        final String url = Url;

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    try {
                        InputStream is = new URL(url).openStream();
                        BufferedInputStream bis = new BufferedInputStream(is);
                        Metadata metadata = ImageMetadataReader.readMetadata(bis);

                        for (Directory directory : metadata.getDirectories()) {
                            for (Tag tag : directory.getTags()) {
                                /*Log.d("Comments",tag.toString());
                                Log.d("Comments",tag.getTagName());
                                Log.d("Comments",tag.getTagTypeHex());
                                Log.d("Comments",tag.getDirectoryName());
                                Log.d("Comments", tag.getDescription());*/
                                if (tags.contains(tag.getTagName())) {
                                    values.put(tag.getTagName(), tag.getDescription());
                                }
                            }
                        }

                    } catch (ImageProcessingException e) {
                        Log.d("Comments", "errore1");
                    } catch (IOException e) {
                        Log.d("Comments", "errore2");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String LATITUDE = values.get("GPS Latitude");
        String LATITUDE_REF = values.get("GPS Latitude Ref");
        String LONGITUDE = values.get("GPS Longitude");
        String LONGITUDE_REF = values.get("GPS Longitude Ref");

        //FORMATO DDD:MM:SS.SSSSS;
        Double Latitude = android.location.Location.convert(convert(LATITUDE));
        Double Longitude = android.location.Location.convert(convert(LONGITUDE));



        LatLng latlng = new LatLng(Latitude, Longitude);
        return latlng;

    }

    public static String convert(String as) {

        String d = as.split("°")[0];
        String m = as.split("°")[1].split("'")[0].substring(1);
        String s = as.split("°")[1].split("'")[1].split("\"")[0].substring(1);
        if (s.contains("59")) s = s.replace("59", "58");
        s = s.replace(",", ".");
        String fin = d.concat(":").concat(m).concat(":").concat(s);

        /*Log.d("Comments", "as" + as);
        Log.d("Comments", "DDD" + d);
        Log.d("Comments", "MM" + m);
        Log.d("Comments", "SSS" + s);
        Log.d("Comments", "RET" + fin);*/

        return fin;
    }

    public static String[] getAlbum(String album_name) {
        String[] IMGS;
        switch (album_name.toUpperCase()) {
            case "BARI": {
                IMGS = Album.BARI;
                break;
            }
            case "ROMA": {
                IMGS = Album.ROMA;
                break;
            }
            case "PARIGI": {
                IMGS = Album.PARIGI;
                break;
            }
            default: {
                IMGS = Album.DEF;
                break;
            }
        }
        return IMGS;
    }

    public static boolean checkAlbum(String nome){
     List<String> nomi=new ArrayList<>(Arrays.asList(ALBUMS));
        if (nomi.contains(nome)) return true;
        else return false;

    }

}


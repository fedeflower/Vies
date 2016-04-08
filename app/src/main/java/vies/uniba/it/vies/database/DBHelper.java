package vies.uniba.it.vies.database;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import vies.uniba.it.vies.model.Album;
import vies.uniba.it.vies.model.Location;
import vies.uniba.it.vies.model.Travel;
import vies.uniba.it.vies.utils.Utils;

/**
 * Created by Daniele on 27/03/2016.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static DBHelper instance;

    public static final Integer DB_VERSION = 2;
    public static final String DATABASE_NAME = "vies.db";
    public static final String TRAVEL_TABLE = "travel";
    public static final String ALBUM_TABLE = "album";
    public static final String FOTO_TABLE = "foto";

    private HashMap hp;

    private DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    public static DBHelper getInstance(Context context) {
        if(instance == null) {
            instance = new DBHelper(context);
        }

        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String CREATE_LISTAFOTO_SQL = new StringBuilder()
                .append(" CREATE TABLE ")
                .append(FOTO_TABLE)
                .append(" (")
                .append(" id integer primary key, ")
                .append(" url text, ")
                .append(" name text, ")
                .append(" lat text, ")
                .append(" long text, ")
                .append(" album text ")
                .append(" ) ")
                .toString();

        final String CREATE_TRAVEL_SQL = new StringBuilder()
                .append(" CREATE TABLE ")
                .append(TRAVEL_TABLE)
                .append(" (")
                .append(" id integer primary key, ")
                .append(" name text, ")
                .append(" dateOut date, ")
                .append(" descrizione text, ")
                .append(" locationName text, ")
                .append(" lat text, ")
                .append(" lon text ")
                .append(" ) ")
                .toString();

        final String CREATE_ALBUM_SQL = new StringBuilder()
                .append(" CREATE TABLE ")
                .append(ALBUM_TABLE)
                .append(" (")
                .append(" id integer primary key, ")
                .append(" name text, ")
                .append(" dateOut date, ")
                //.append(" descrizione text, ")
                .append(" lat text, ")
                .append(" lon text ")
                .append(" ) ")
                .toString();

        db.execSQL(CREATE_LISTAFOTO_SQL);
        db.execSQL(CREATE_TRAVEL_SQL);
        db.execSQL(CREATE_ALBUM_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TRAVEL_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ALBUM_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + FOTO_TABLE);
        onCreate(db);
    }

    public boolean insertFoto(String url,String lat,String lng,String album) {
        Log.d(getClass().getCanonicalName(), "insertFoto <<START>>");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("url", url);
        contentValues.put("name", Utils.getNameFileFromUrl(url));
        contentValues.put("lat", lat);
        contentValues.put("long", lng);
        contentValues.put("album", album);

        db.insert(FOTO_TABLE, null, contentValues);
        Log.d(getClass().getCanonicalName(), "insertFoto <<END>>");
        return true;
    }

    public boolean insertTravel(Travel t) {
        Log.d(getClass().getCanonicalName(), "insertTravel <<START>>");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", t.getName());
        contentValues.put("dateOut", t.getDateOut());
        contentValues.put("descrizione", t.getDescrizione());
        Log.w("Comments", "inserimento " + t.getDescrizione());
        contentValues.put("locationName", t.getLocation().getName());
        //contentValues.put("lat", t.getLocation().getLatLng().latitude);
        //contentValues.put("lon", t.getLocation().getLatLng().longitude);
        db.insert(TRAVEL_TABLE, null, contentValues);
        Log.d(getClass().getCanonicalName(), "insertTravel <<END>>");
        return true;
    }

    public List<Travel> getTravels()
    {
        List<Travel> travels = new ArrayList<Travel>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from " + TRAVEL_TABLE, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            Travel t = new Travel();
            //t.setId(res.getInt(res.getColumnIndex("id")));
            t.setName(res.getString(res.getColumnIndex("name")));
            t.setDateOut(res.getLong(res.getColumnIndex("dateOut")));
            //t.setDateIn(res.getLong(res.getColumnIndex("dateIn")));
            t.setDescrizione(res.getString(res.getColumnIndex("descrizione")));
            Log.w("Comments", ""+res.getString(res.getColumnIndex("descrizione")));

            Location l = new Location(res.getString(res.getColumnIndex("locationName")));
            l.setLatLng(new LatLng(res.getDouble(res.getColumnIndex("lat")), res.getDouble(res.getColumnIndex("lon"))));
            t.setLocation(l);
            travels.add(t);

            res.moveToNext();
        }

        return travels;
    }

  /*  public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from contacts where id=" + id + "", null);
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TRAVEL_TABLE);
        return numRows;
    }

    public boolean updateContact (Integer id, String name, String phone, String email, String street,String place)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("street", street);
        contentValues.put("place", place);
        db.update("contacts", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteContact (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TRAVEL_TABLE, "id = ? ", new String[] { Integer.toString(id) });
    }
*/

    public void riempiDB() {
        for(String album_name:Album.ALBUMS){
            String[] album=getAlbumField(album_name);
            String[] lat=getAlbumField(album_name+"LAT");
            String[] lng=getAlbumField(album_name+"LONG");
            for(int i=0;i<album.length;i++){
                String url=album[i];
                insertFoto(url,lat[i],lng[i],album_name);
            }
        }
    }

    public String[] getAlbumField(String name) {
        Field field=null;
        String[] album={};
        try{
        field = Album.class.getDeclaredField(name);
        field.setAccessible(true);
        Object value = field.get(Album.class);
            album = (String[]) value;
        }catch (NoSuchFieldException e){
            e.printStackTrace();
        } catch (IllegalAccessException e){
        e.printStackTrace();
    }
        return album;

    }

    public List<LatLng> getCoord(String album){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + FOTO_TABLE + " where album='"+album+"' group by name order by id", null );
        res.moveToFirst();
        Double lat;
        Double lng;
        List<LatLng> pos=new ArrayList<LatLng>();
        while(res.isAfterLast() == false) {
            lat=res.getDouble(res.getColumnIndex("lat"));
            lng=res.getDouble(res.getColumnIndex("long"));
            pos.add(new LatLng(lat,lng));
            /*
            ret.concat(res.getString(res.getColumnIndex("id"))).concat("---");
            ret.concat(res.getString(res.getColumnIndex("url"))).concat("---");
            ret.concat(res.getString(res.getColumnIndex("name"))).concat("---");
            ret.concat(res.getString(res.getColumnIndex("long")));
            ret.concat(res.getString(res.getColumnIndex("album"))).concat("---");
*/
            res.moveToNext();
        }
        return pos;
    }

    public List<LatLng> getAllCoord(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from " + TRAVEL_TABLE, null);
        res.moveToFirst();
        List<String> album_locations= new ArrayList<>();
        List<String> locations=new ArrayList<String>(Arrays.asList(Album.ALBUMS));
        List<String> locations_found=new ArrayList<>();
        List<LatLng> pos = new ArrayList<LatLng>();

        while(res.isAfterLast() == false) {
            album_locations.add(res.getString(res.getColumnIndex("locationName")).toUpperCase());
            res.moveToNext();
        }
        for (String name:album_locations) {
            if(locations.contains(name) & !locations_found.contains(name)){
                locations_found.add(name);
            }
        }
        for(String found:locations_found) {
            res = db.rawQuery("select * from " + FOTO_TABLE + " where album='" + found + "'", null);
            res.moveToFirst();
            Double lat;
            Double lng;
            while (res.isAfterLast() == false) {
                lat = res.getDouble(res.getColumnIndex("lat"));
                lng = res.getDouble(res.getColumnIndex("long"));
                pos.add(new LatLng(lat, lng));
                res.moveToNext();
            }
        }
        return pos;
    }

    public Integer deleteTravel (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TRAVEL_TABLE, "id = ? ", new String[] { Integer.toString(id) });
    }

}

package vies.uniba.it.vies.database;

import java.util.ArrayList;
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

import vies.uniba.it.vies.model.Location;
import vies.uniba.it.vies.model.Travel;

/**
 * Created by Daniele on 27/03/2016.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static DBHelper instance;

    public static final Integer DB_VERSION = 2;
    public static final String DATABASE_NAME = "vies2.db";
    public static final String TRAVEL_TABLE = "travel";
    public static final String ALBUM_TABLE = "album";

    private HashMap hp;

    private DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, DB_VERSION);
    }

    public static DBHelper getInstance(Context context) {
        if(instance == null) {
            instance = new DBHelper(context);
        }

        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
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

        db.execSQL(CREATE_TRAVEL_SQL);
        db.execSQL(CREATE_ALBUM_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TRAVEL_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ALBUM_TABLE);
        onCreate(db);
    }

    public boolean insertTravel(Travel t) {
        Log.d(getClass().getCanonicalName(), "insertTravel <<START>>");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", t.getName());
        contentValues.put("dateOut", t.getDateOut());
        contentValues.put("descrizione", t.getDescrizione());
        contentValues.put("locationName", t.getLocation().getName());
        contentValues.put("lat", t.getLocation().getLatitude());
        contentValues.put("lon", t.getLocation().getLongitude());
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

            Location l = new Location(res.getString(res.getColumnIndex("locationName")));
            l.setLatitude(res.getDouble(res.getColumnIndex("lat")));
            l.setLongitude(res.getDouble(res.getColumnIndex("lon")));

            t.setLocation(l);
            travels.add(t);

            res.moveToNext();
        }

        return travels;
    }

    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from contacts where id="+id+"", null );
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

}

package hackathon.honeywell.potholedetectionandlogging;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.nfc.Tag;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.ContentValues.TAG;



public class DBHandler extends SQLiteOpenHelper {

    public HashMap<Double,Double> coordinates = new HashMap<Double, Double>();

    //Definition of database name, version table and column names
    public static final String TABLE_NAME = "location_info";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_LAT = "latitude";
    public static final String COLUMN_LONG = "longitude";
    public static final String COLUMN_NUM = "number";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "location.db";

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE "+TABLE_NAME+"("+
                COLUMN_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COLUMN_LAT+ " REAL, "+
                COLUMN_LONG+" REAL, "+
                COLUMN_NUM+ " INTEGER "+
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public void addLocation(Location location){
        SQLiteDatabase db = getWritableDatabase();
     //   double longi = Math.round(location.getLongitude()*1000000000000.0)/1000000000000.0;
        double longi = location.getLongitude();

     //   double lati = Math.round(location.getLatitude()*1000000000000.0)/1000000000000.0;

        double lati = location.getLatitude();
        String q1 = "SELECT * FROM " +TABLE_NAME+ " WHERE " + COLUMN_LONG+ "=" + Double.toString(longi) + " AND "+ COLUMN_LAT+"=" + Double.toString(lati) + ";";
        Log.i("Query",q1);
        Cursor c = db.rawQuery(q1,null);
        String q2;
        if(c.moveToFirst()){
            q2 = "UPDATE "+TABLE_NAME+ " SET "+ COLUMN_NUM+"="+COLUMN_NUM+"+1" + " WHERE "  + COLUMN_LONG+ "=" + Double.toString(longi) + " AND "+ COLUMN_LAT+"=" + Double.toString(lati) + ";";
        }
        else{
            q2 = "INSERT INTO "+ TABLE_NAME +" VALUES(NULL,"+ Double.toString(lati)+ ","+ Double.toString(longi) + ",1);";
        }
        Log.i("Query",q2);
        db.execSQL(q2);
        db.close();
    }

    public void deleteTable(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public void deleteLocation(Location location){
        SQLiteDatabase db = getWritableDatabase();
        String q4 = "DELETE FROM "+ TABLE_NAME + " WHERE "+COLUMN_LAT+"="+location.getLatitude()+" AND "+COLUMN_LONG+"="+location.getLongitude()+" ;";
        db.execSQL(q4);
        Log.i("Query",q4);
        db.close();
    }

    public String printDataBase(){
        SQLiteDatabase db = getWritableDatabase();
        StringBuffer buffer = new StringBuffer();
        String q4 = "SELECT * FROM "+ TABLE_NAME + ";";
        Cursor c = db.rawQuery(q4, null);

        while(c.moveToNext()){
                buffer.append(c.getInt(0)+" "+c.getDouble(1)+" "+c.getDouble(2)+ " "+c.getDouble(3)+"\n");
        }
        db.close();
        String s1 = buffer.toString();
        Log.i("Query",s1);
        return s1;

    }

    public ArrayList<String> getPotholeList(){

        SQLiteDatabase db = getWritableDatabase();
        String q4 = "SELECT * FROM "+ TABLE_NAME + ";";
        Cursor cursor = db.rawQuery(q4, null);
        //ArrayList<Double> lati = new ArrayList<Double>();
        //ArrayList<Double> longi = new ArrayList<Double>();
        ArrayList <String> coordinates = new ArrayList<String>();
        while(cursor.moveToNext()){
            Double templati = cursor.getDouble(1);
            Double templongi = cursor.getDouble(2);
            coordinates.add(templati.toString()+","+templongi.toString());
        }

        return coordinates;

    }

    public ArrayList<String> getList(){

        SQLiteDatabase db = getWritableDatabase();
        String q4 = "SELECT * FROM "+ TABLE_NAME + ";";
        Cursor cursor = db.rawQuery(q4, null);
        //ArrayList<Double> lati = new ArrayList<Double>();
        //ArrayList<Double> longi = new ArrayList<Double>();
        ArrayList <String> coordinateslist = new ArrayList<String>();
        while(cursor.moveToNext()){
            int incr = cursor.getInt(3);
            Double templati = cursor.getDouble(1);
            Double templongi = cursor.getDouble(2);

            coordinateslist.add(templati.toString()+","+templongi.toString()+","+incr);
        }

        return coordinateslist;

    }


    /*
    public HashMap<Double,Double> getPotholeList(){
        SQLiteDatabase db= this.getWritableDatabase();
        String query ="SELECT * FROM "+TABLE_NAME+";";
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()) {
            do {
                    //Double latitude = cursor.getDouble(1);
                    //Double longitude = cursor.getDouble(2);
                    //LatLng location = new LatLng(latitude, longitude);
                    //Location locs = new Location  ("Test");
                    //locs.setLatitude(cursor.getDouble(1));
                    //locs.setLongitude(cursor.getDouble(2));

                    coordinates.put(cursor.getDouble(1),cursor.getDouble(2));

            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return coordinates;
     */


}

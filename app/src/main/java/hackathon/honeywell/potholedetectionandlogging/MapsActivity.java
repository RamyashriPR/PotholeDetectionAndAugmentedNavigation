package hackathon.honeywell.potholedetectionandlogging;

import android.content.Context;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
//import android.location.Address;

import com.google.android.gms.identity.intents.Address;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

//import static hackathon.honeywell.potholedetectionandlogging.DBHandler.coordinates;
import static hackathon.honeywell.potholedetectionandlogging.MainActivity.dbHandler;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    double latitude = 12.925150;
    double longitude = 77.686311;

    public static Location initlocation = new Location("gps");
    Button addpothole;
    public LatLng newpoint;
    public Marker toberemoved;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        addpothole = (Button) findViewById(R.id.add_pothole);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //Get the bundle
        /*
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            latitude = extras.getDouble("lat");
            longitude = extras.getDouble("long");
        }
        */


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */




    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        LatLng initposition = new LatLng(latitude,longitude);
        mMap.addMarker(new MarkerOptions().position(initposition).title("Initial Position"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(initposition));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));





        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
/*
                if (marker.getTitle().equals("Pothole")) {

                        LatLng pos = marker.getPosition();
                        Location location = new Location("Test");
                        location.setLatitude(pos.latitude);
                        location.setLongitude(pos.longitude);
                        //dbHandler.deleteLocation(location);
                        //marker.remove();
                        Toast.makeText(getApplicationContext(),"False positive removed ",Toast.LENGTH_SHORT).show();
                        return true;
                }
*/
                return false;
            }
        });



        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {

                newpoint = point;
                toberemoved = mMap.addMarker(new MarkerOptions().position(point).title("destination"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(point));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));

            }
        });



      //  while (loc.hasNext()) {

            //HashMap.Entry pair = (HashMap.Entry) loc.next();

            //double latitude1 = (double) pair.getKey();
            //double longitude1 = (double) pair.getValue();
            //double longitude1 = Math.round(loc.next().getLongitude()*1000.0)/1000.0;
            //double latitude1 = Math.round(loc.next().getLatitude()*1000.0)/1000.0;

            ArrayList <String> result=dbHandler.getPotholeList();
            Iterator <String> iter = result.iterator();

             while(iter.hasNext()){
                 String [] temparr = iter.next().split(",");
                 LatLng pothole = new LatLng(Double.parseDouble(temparr[0]), Double.parseDouble(temparr[1]));
                 mMap.addMarker(new MarkerOptions().position(pothole).title("Pothole").icon(BitmapDescriptorFactory.fromResource(R.drawable.potholesmall)));
           }
            //LatLng pothole = new LatLng(latitude, longitude);
           // mMap.addMarker(new MarkerOptions().position(pothole).title("Pothole").icon(BitmapDescriptorFactory.fromResource(R.drawable.potholesmall)));
           // mMap.moveCamera(CameraUpdateFactory.newLatLng(pothole));
            //mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));

        /*    LatLng position = loc.next();
            mMap.addMarker(new MarkerOptions().position( pothole).title("Pothole").icon(BitmapDescriptorFactory.fromResource(R.drawable.potholesmall)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(pothole));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f)); */

      //  }
    }

    public void addPothole(View view) {


        toberemoved.remove();

        Marker mrk = mMap.addMarker(new MarkerOptions().position(newpoint).title("manPothole").icon(BitmapDescriptorFactory.fromResource(R.drawable.manpot)));
        Location location = new Location("point");
        location.setLatitude(newpoint.latitude);
        location.setLongitude(newpoint.longitude);
        dbHandler.addLocation(location);
    }
}
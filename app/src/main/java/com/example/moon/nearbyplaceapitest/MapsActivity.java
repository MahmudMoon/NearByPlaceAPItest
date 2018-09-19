package com.example.moon.nearbyplaceapitest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Button school;
    Button hospital;
    Button restaurant;
    LatLng ZeroPoint;
    ArrayList<ObjectCreator> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        school = (Button) findViewById(R.id.school);
        hospital = (Button)findViewById(R.id.hospital);
        restaurant = (Button)findViewById(R.id.resturant);
        arrayList = new ArrayList<>();
        boolean isGooglePlayServiceAvailable = CheckGooglePlayServices();
        if (isGooglePlayServiceAvailable) {


        } else {
            Toast.makeText(getApplicationContext(), "Google play service not found", Toast.LENGTH_SHORT).show();
        }

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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        ZeroPoint = new LatLng(24.3694533, 88.5785055);
        mMap.addMarker(new MarkerOptions().position(ZeroPoint).title("Rajshahi Zero Point").icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ZeroPoint, 16.0f));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

// there is a billing issue with this
        restaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url =  getURL(ZeroPoint.latitude,ZeroPoint.longitude,"restaurant");
                TaskDone asTask = (TaskDone) new TaskDone() {
                    @Override
                    protected void onPostExecute(String s) {
                        Log.i("data",s);
                        //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                        arrayList = customParsar(s);
                        if(arrayList!=null) {
                           // Toast.makeText(getApplicationContext(), String.valueOf(arrayList.size()), Toast.LENGTH_SHORT).show();
                            Log.d("ArrayList",String.valueOf(arrayList.size()));
                            putMarkers(arrayList,"restaurant");

                        }
                    }
                }.execute(url);
            }
        });




        hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url =  getURL(ZeroPoint.latitude,ZeroPoint.longitude,"hospital");
                TaskDone asTask = (TaskDone) new TaskDone() {
                    @Override
                    protected void onPostExecute(String s) {
                        Log.i("data",s);
                        //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                        arrayList = customParsar(s);
                        if(arrayList!=null) {
                           // Toast.makeText(getApplicationContext(), String.valueOf(arrayList.size()), Toast.LENGTH_SHORT).show();
                            Log.d("ArrayList",String.valueOf(arrayList.size()));
                            putMarkers(arrayList,"hospital");

                        }
                    }
                }.execute(url);
            }
        });





        school.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url =  getURL(ZeroPoint.latitude,ZeroPoint.longitude,"school");
                TaskDone asTask = (TaskDone) new TaskDone() {
                    @Override
                    protected void onPostExecute(String s) {
                        Log.i("data",s);
                        //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                        arrayList = customParsar(s);
                        if(arrayList!=null) {
                            //Toast.makeText(getApplicationContext(), String.valueOf(arrayList.size()), Toast.LENGTH_SHORT).show();
                            Log.d("ArrayList",String.valueOf(arrayList.size()));
                            putMarkers(arrayList,"school");

                        }
                    }
                }.execute(url);

            }
        });



    }

    private void putMarkers(ArrayList<ObjectCreator> arrayList,String type) {
        for(int i=0;i<arrayList.size();i++){
            MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(arrayList.get(i).getLat(),arrayList.get(i).getLon()))
                    .title(arrayList.get(i).getName());


                    if(type.equals("school")){
                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    }else if(type.equals("hospital")){
                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    }else if(type.equals("restaurant")){
                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
                    }
            mMap.addMarker(markerOptions);

        }

    }

    private ArrayList<ObjectCreator> customParsar(String s) {

        try {
            JSONObject root = new JSONObject(s);
            JSONArray results = root.getJSONArray("results");
            if(results.length()>0){
                for(int i=0;i<results.length();i++){
                    JSONObject ValueOfArray = (JSONObject) results.get(i);
                    JSONObject geometry = ValueOfArray.getJSONObject("geometry");
                    JSONObject location = geometry.getJSONObject("location");
                    double latitide = location.getDouble("lat");
                    double longitude = location.getDouble("lng");
                    String name = ValueOfArray.getString("name");
                    arrayList.add(new ObjectCreator(latitide,longitude,name));
                    Log.i("lat",String.valueOf(latitide));

                }
            }else {
                return null;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
       return arrayList;
    }

    private String getURL(double latitude, double longitude, String type) {
        StringBuilder stringBuilder = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        stringBuilder.append("location="+latitude+","+longitude);
        stringBuilder.append("&radius=1500");
        stringBuilder.append("&type="+type);
        stringBuilder.append("&sensor=true");
        stringBuilder.append("&key=" + "AIzaSyDjRKlJhUCPJszW2A6G6l-K-YfjdGgFpUo");
        String link = stringBuilder.toString();
        Log.i("link",link);
        return link;
    }


    private boolean CheckGooglePlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);
        if(result != ConnectionResult.SUCCESS) {
            if(googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(this, result,
                        0).show();
            }
            return false;
        }
        return true;
    }

}

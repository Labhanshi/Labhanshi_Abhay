package com.geu.student.railwayenquiry;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Internet.ApiCal;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String  num;
    List<String> number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        number = new ArrayList<>();

        String response = getIntent().getExtras().getString("response");
        Toast.makeText(MapsActivity.this, response, Toast.LENGTH_SHORT).show();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        showdialog(response);


    }

    public void showdialog(String res) {

        try {
            JSONObject jsonObject = new JSONObject(res);

            final StringBuilder sb = new StringBuilder();
            JSONArray jsonArray = jsonObject.getJSONArray("train");
            for (int s = 0; s < jsonArray.length(); s++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(s);

                String name = jsonObject1.getString("name");
                sb.append(name + "\t" + jsonObject1.getString("number") + "\n");
                number.add(jsonObject1.getString("number"));
            }

            final Dialog dg = new Dialog(MapsActivity.this);
            dg.setContentView(R.layout.dialog_layout);
            dg.setTitle("Select a Train...");
            ListView lv = (ListView) dg.findViewById(R.id.lv1);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(MapsActivity.this, android.R.layout.simple_list_item_1, number);
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    num = (String) parent.getItemAtPosition(position);
                    Toast.makeText(MapsActivity.this, sb.toString(), Toast.LENGTH_SHORT).show();
                    getLatLong(num);
                    dg.dismiss();
                }
            });


            dg.show();
            Toast.makeText(MapsActivity.this, sb.toString(), Toast.LENGTH_SHORT).show();


        } catch (Exception e) {

        }
    }

    private void getLatLong(String number) {




    List<String> lat = new ArrayList<String>();
     List<String> code = new ArrayList<String>();
    List<String> state = new ArrayList<String>();
    List<String> lng = new ArrayList<String>();
    List<String> fullname = new ArrayList<String>();

    try{// start
        String response = new ApiCal(MapsActivity.this).execute("http://api.railwayapi.com/route/train/" + number + "/apikey/cfpwq4935/").get();
        JSONObject jsonObject = new JSONObject(response);
        StringBuilder sb1 = new StringBuilder();
        JSONArray jsonArray = jsonObject.getJSONArray("route");
        JSONObject jsonObject2 = jsonObject.getJSONObject("train");
        JSONArray jsonArray2 = jsonObject2.getJSONArray("days");
        for (int x = 0; x < jsonArray.length(); x++) {

            lat.add(jsonArray.getJSONObject(x).getString("lat"));
             state.add(jsonArray.getJSONObject(x).getString("state"));
              lng.add(jsonArray.getJSONObject(x).getString("lng"));
            fullname.add(jsonArray.getJSONObject(x).getString("fullname"));



        }
    }
    catch(Exception e)
    {





    }



        for(int i=0;i<lat.size();i++)
        {
Toast.makeText(MapsActivity.this,""+lat.get(i),Toast.LENGTH_LONG
).show();
            if(Double.parseDouble(lat.get(i))!=0.0&&!fullname.get(i).contains("NEW DELHI")) {
                if (i == 0) {
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(lat.get(i)), Double.parseDouble(lng.get(i))), 10));
                    mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(lat.get(i)), Double.parseDouble(lng.get(i)))).title(fullname.get(i)).snippet(state.get(i)));
                } else {
                    mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(lat.get(i)), Double.parseDouble(lng.get(i)))).title(fullname.get(i)).snippet(state.get(i)));

                }
            }
            else if(fullname.get(i).contains("NEW DELHI"))
            {
                lat.set(i,"28.643");
                lng.set(i,"77.222");
                state.set(i,"Delhi");
                mMap.addMarker(new MarkerOptions().position(new LatLng(28.643,77.222)).title(fullname.get(i)).snippet(state.get(i)));

            }


        }





        for(int i=0;i<lat.size()-1;)
        {

            int x=i;
            int y;
            if(Double.parseDouble(lat.get(i+1))!=0.0) {

                  y=i+1;
            }
            else
            {
                y=i+2;
            }




                     mMap.addPolyline(new PolylineOptions().add(new LatLng(Double.parseDouble(lat.get(x)),Double.parseDouble(lng.get(x))),new LatLng(Double.parseDouble(lat.get(y)),Double.parseDouble(lng.get(y)))));
i=y;

        }





    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
    }
}

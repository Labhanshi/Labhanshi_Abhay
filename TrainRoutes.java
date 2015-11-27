package com.geu.student.railwayenquiry;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import AdaptersForViews.RouteAdapters;
import Internet.ApiCal;

public class TrainRoutes extends AppCompatActivity {
    //http://api.railwayapi.com/route/train/12046/apikey/makkf7604/
    TextView tv;
    String res;
    String sour, dest;
    EditText tnu;
    Button a;
    ListView lv;
    List<String> schdep, scharr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_routes);
        tv = (TextView) findViewById(R.id.tinfo);
        lv = (ListView) findViewById(R.id.listView3);
        a = (Button) findViewById(R.id.button4);
        tnu = (EditText) findViewById(R.id.editText3);

        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String restInfo = "";
                StringBuilder sb = new StringBuilder();

                List<String> halt = new ArrayList<String>();
                List<String> lat = new ArrayList<String>();
                List<String> distance = new ArrayList<String>();
                List<String> route = new ArrayList<String>();
                List<String> no = new ArrayList<String>();
                List<String> code = new ArrayList<String>();
                List<String> state = new ArrayList<String>();
                List<String> day = new ArrayList<String>();
                List<String> lng = new ArrayList<String>();
                List<String> fullname = new ArrayList<String>();
                schdep = new ArrayList<String>();
                scharr = new ArrayList<String>();
                try {
                    String response = new ApiCal(TrainRoutes.this).execute("http://api.railwayapi.com/route/train/" + tnu.getText().toString() + "/apikey/cfpwq4935/").get();
                    JSONObject jsonObject = new JSONObject(response);
                    StringBuilder sb1 = new StringBuilder();
                    JSONArray jsonArray = jsonObject.getJSONArray("route");
                    JSONObject jsonObject2 = jsonObject.getJSONObject("train");
                    JSONArray jsonArray2 = jsonObject2.getJSONArray("days");
                    for (int x = 0; x < jsonArray.length(); x++) {
                        halt.add(jsonArray.getJSONObject(x).getString("halt"));
                        lat.add(jsonArray.getJSONObject(x).getString("lat"));
                        distance.add(jsonArray.getJSONObject(x).getString("distance"));
                        route.add(jsonArray.getJSONObject(x).getString("route"));
                        no.add(jsonArray.getJSONObject(x).getString("no"));
                        code.add(jsonArray.getJSONObject(x).getString("code"));
                        state.add(jsonArray.getJSONObject(x).getString("state"));
                        // day.add(jsonArray.getJSONObject(x).getString("day"));

                        lng.add(jsonArray.getJSONObject(x).getString("lng"));
                        fullname.add(jsonArray.getJSONObject(x).getString("fullname"));
                        schdep.add(jsonArray.getJSONObject(x).getString("schdep"));
                        scharr.add(jsonArray.getJSONObject(x).getString("scharr"));

                        for (int r = 0; r < jsonArray2.length(); r++) {
                            JSONObject jsonObject3 = jsonArray2.getJSONObject(r);

                            if (jsonObject3.getString("runs").contains("Y")) {


                                if (!sb.toString().contains(jsonObject3.getString("day-code"))) {
                                    sb.append(jsonObject3.getString("day-code"));
                                    day.add(sb.toString());
                                }
                            }
                        }

                    }

                    tv.setText(jsonObject2.getString("number") + "\n" + jsonObject2.getString("name"));


                    RouteAdapters adap = new RouteAdapters(TrainRoutes.this, code, day
                            , distance, fullname,
                            halt, lat,
                            lng, no,
                            route, scharr
                            , schdep, state);
                    lv.setAdapter(adap);


                } catch (Exception e) {

                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.trainbetween, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item1) {
            Intent i = new Intent(TrainRoutes.this, StationCodes.class);
            startActivity(i);
        } else if (item.getItemId() == R.id.item2) {

Intent i1=new Intent(TrainRoutes.this,RouteMapsActivity.class);
            i1.putExtra("response",tnu.getText().toString());
            startActivity(i1);


                    //Toast.makeText(TrainBetween.this, "" + train, Toast.LENGTH_SHORT).show();


                //Toast.makeText(TrainBetween.this, "" + train, Toast.LENGTH_SHORT).show();


        }
        return super.onOptionsItemSelected(item);
    }
}

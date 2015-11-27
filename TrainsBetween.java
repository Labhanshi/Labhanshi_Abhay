package com.geu.student.railwayenquiry;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import AdaptersForViews.RecyclerAdapter;
import Internet.ApiCal;

public class TrainBetween extends AppCompatActivity {
    EditText a, b;
    String res,num;

    Button s;
    RecyclerView rv;
    List<String>number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_between);
        a = (EditText) findViewById(R.id.stationA);
        b = (EditText) findViewById(R.id.stationB);
        s = (Button) findViewById(R.id.search);
        number=new ArrayList<>();
        rv = (RecyclerView) findViewById(R.id.rv1);
        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiCal apc = new ApiCal(TrainBetween.this);
                try {

                    // http://api.railwayapi.com/between/source/a.getText().toString()/dest/ngp/date/27-11-2015/apikey/makkf7604/
                    res = apc.execute("http://api.railwayapi.com/between/source/" + a.getText().toString() + "/dest/" + b.getText().toString() + "/date/27-11-2015/apikey/makkf7604/").get();
                    List<String> dest_arr, sour_dept, to, from, name, number;
                    dest_arr = new ArrayList<>();
                    sour_dept = new ArrayList<>();
                    to = new ArrayList<>();
                    from = new ArrayList<>();
                    name = new ArrayList<>();
                    number = new ArrayList<>();


                    JSONObject jsonObject = new JSONObject(res);

                    JSONArray jsonArray = jsonObject.getJSONArray("train");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        dest_arr.add(jsonObject1.getString("dest_arrival_time"));
                        sour_dept.add(jsonObject1.getString("src_departure_time"));
                        to.add(jsonObject1.getJSONObject("to").getString("name"));
                        from.add(jsonObject1.getJSONObject("from").getString("name"));
                        name.add(jsonObject1.getString("name"));
                        number.add(jsonObject1.getString("number"));

                    }

                    RecyclerAdapter rad = new RecyclerAdapter(TrainBetween.this, name, number, dest_arr, sour_dept, to, from);

                    StaggeredGridLayoutManager lm = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);

                    rv.setLayoutManager(lm);
                    rv.setHasFixedSize(true);
                    rv.setAdapter(rad);


                    for (int z = 0; z < name.size(); z++) {
                        Toast.makeText(TrainBetween.this, name.get(z), Toast.LENGTH_SHORT).show();
                    }

                    //Toast.makeText(TrainBetween.this, "" + train, Toast.LENGTH_SHORT).show();
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
            Intent i = new Intent(TrainBetween.this, StationCodes.class);
            startActivity(i);
        } else if (item.getItemId() == R.id.item2) {
            ApiCal apc = new ApiCal(TrainBetween.this);
            try {

                // http://api.railwayapi.com/between/source/a.getText().toString()/dest/ngp/date/27-11-2015/apikey/makkf7604/
                res = apc.execute("http://api.railwayapi.com/between/source/" + a.getText().toString() + "/dest/" + b.getText().toString() + "/date/27-11-2015/apikey/cfpwq4935/").get();
                Intent i1 = new Intent(TrainBetween.this, MapsActivity.class);
                i1.putExtra("response", res);
                startActivity(i1);

                //Toast.makeText(TrainBetween.this, "" + train, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {

            }

        }
        return super.onOptionsItemSelected(item);
    }
}

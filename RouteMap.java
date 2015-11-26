package com.geu.student.railwayenquiry;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import Internet.ApiCal;

public class RouteMap extends AppCompatActivity {
    EditText et;
    Button a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_map);
        et=(EditText)findViewById(R.id.editText5);
        a=(Button)findViewById(R.id.button6);
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String response = new ApiCal(RouteMap.this).execute("http://api.railwayapi.com/route/train/"+et.getText().toString()+"/apikey/makkf7604/").get();
                   /* Toast.makeText(RouteMap.this, response, Toast.LENGTH_SHORT).show();*/
                    Intent i=new Intent(RouteMap.this,MapsActivity.class);
                    i.putExtra("response",response);
                    startActivity(i);
                }catch (Exception e){

                }
            }
        });
    }
}

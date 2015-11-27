package com.geu.student.railwayenquiry;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import Internet.ApiCal;

public class StationCodes extends AppCompatActivity {
Button a;
    EditText stationName;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_codes);
stationName=(EditText)findViewById(R.id.editText2);
        a=(Button)findViewById(R.id.button2);
        tv=(TextView)findViewById(R.id.stCode);
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
//http://api.railwayapi.com/name_to_code/station/Delhi/apikey/makkf7604/
                        ApiCal apc=new ApiCal(StationCodes.this);
                    StringBuilder sb=new StringBuilder();
                  String  usname=stationName.getText().toString();
                        String response=apc.execute("http://api.railwayapi.com/name_to_code/station/"+stationName.getText().toString()+"/apikey/cfpwq4935/").get();
                   // Toast.makeText(StationCodes.this,response,Toast.LENGTH_LONG).show();
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("stations");
                    for (int ia=0;ia<jsonArray.length();ia++){
                        String sname=jsonArray.getJSONObject(ia).getString("fullname");

                         sb.append(sname+"   :"+jsonArray.getJSONObject(ia).getString("code")+"\n\n") ;

                    }

                    tv.setText(sb.toString());
                    }catch (Exception e){

                }

            }
        });

    }
}

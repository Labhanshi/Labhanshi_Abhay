package com.geu.student.railwayenquiry;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;



public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button trainBetween,route;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        trainBetween=(Button)findViewById(R.id.imageButton1);
        trainBetween.setOnClickListener(this);
        
        route=(Button)findViewById(R.id.imageButton3);
        route.setOnClickListener(this);
        
        arrival=(Button)findViewById(R.id.imageButton6);
        arrival.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageButton1:
                Intent i1=new Intent(MainActivity.this,TrainBetween.class);
                startActivity(i1);

                break;
            
            case R.id.imageButton2:
                Intent i3=new Intent(MainActivity.this,TrainRoutes.class);
                startActivity(i2);
                break;
            
            default:
            
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.item1)
        {
            Intent i=new Intent(MainActivity.this,RouteMap.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}

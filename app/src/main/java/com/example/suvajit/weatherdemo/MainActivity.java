package com.example.suvajit.weatherdemo;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView ed1,ed2,ed3,ed4,ed5,ed6;

    private String url1 = "http://api.openweathermap.org/data/2.5/weather?q=";
    private String url2 = "&mode=xml&APPID=XXXXXXXXX&units=metric";               //XXXXXXXXX = your api key
    private HandleXML obj;
    boolean connected = false;
    Button b1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1=(Button)findViewById(R.id.button);
        ed1=(TextView) findViewById(R.id.editText);
        ed2=(TextView)findViewById(R.id.editText2);
        ed3=(TextView) findViewById(R.id.editText3);
        ed4=(TextView) findViewById(R.id.editText4);
        ed5=(TextView) findViewById(R.id.editText5);
        ed6=(TextView) findViewById(R.id.textView8);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                    //we are connected to a network
                    connected = true;
                }
                else
                    connected = false;

                String url = ed1.getText().toString();
                if(connected) {
                    if (url.length() > 0) {
                        String finalUrl = url1 + url + url2;
                        ed2.setText(finalUrl);

                        obj = new HandleXML(finalUrl);
                        obj.fetchXML();

                        while (obj.parsingComplete) ;
                        ed2.setText(obj.getCountry());
                        ed3.setText(obj.getTemperature());
                        ed4.setText(obj.getHumidity());
                        ed5.setText(obj.getPressure());
                        ed6.setText(obj.getWeather());
                    } else {
                        Toast.makeText(getApplicationContext(), "Enter Location", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "No Connectivity", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu_main; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent about = new Intent(MainActivity.this,About.class);
            startActivityForResult(about,0);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
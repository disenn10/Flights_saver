package com.example.disen.myflights;

import android.content.ContentValues;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.disen.myflights.data.FlightContract;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks <ArrayList<FlightsInfoList>>{

    FloatingActionButton myflights;
    String minneapolis;
    String denver;
    String los_angeles;
    String boston;
    Integer x;
    String mydate;
    String temps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        minneapolis = "MSP";
        denver = "DEN";
        los_angeles = "LAX";
        boston = "BOS";
        myflights = (FloatingActionButton)findViewById(R.id.dsiplay_flights);
        myflights.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Flights.class);
                startActivity(intent);
            }
        });
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        mydate = df.format(c.getTime());

        Date mytime = c.getTime();
        SimpleDateFormat tf = new SimpleDateFormat("HH:mm:ss");
        temps = tf.format(mytime);
        getSupportLoaderManager().initLoader(0,null,this);
        getSupportLoaderManager().initLoader(1,null,this);
        getSupportLoaderManager().initLoader(2,null,this);
    }

    @Override
    public Loader<ArrayList<FlightsInfoList>> onCreateLoader(int id, Bundle args) {
        switch (id){
            case 0:
                x = 0;
                return new FlightsLoader(this,minneapolis,denver);
            case 1:
                x=1;
                return new FlightsLoader(this,minneapolis,los_angeles);
            case 2:
                x=2;
                return new FlightsLoader(this,los_angeles, boston);
            default:
                throw new IllegalArgumentException("Impossible to load loader");
        }
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<FlightsInfoList>> loader, ArrayList<FlightsInfoList> data) {
        if (data != null) {
            Log.e(MainActivity.class.getSimpleName(), "data not null: ");
            updateUi(data, loader.getId());
        }
        else{
            Log.e(MainActivity.class.getSimpleName(), "data is null: ");
        }
    }

    public ArrayList getplaces(Integer i){
        String origin = null;
        String dest = null;
        ArrayList places = new ArrayList();
        if(i == 0){
            origin = minneapolis;
            dest = denver;
        }
        else if(i == 1){
            origin = minneapolis;
            dest = los_angeles;
        }
        else if (i ==2){
            origin = los_angeles;
            dest = boston;
        }
        places.add(origin);
        places.add(dest);
        return places;
    }


    private void updateUi(ArrayList<FlightsInfoList>data, Integer i) {
        ArrayList arrayList = getplaces(i);
        String origin = String.valueOf(arrayList.get(0));
        String destination = String.valueOf(arrayList.get(1));
        String date = data.get(0).getDate();
        String time = data.get(0).getTime();
        String depAirline = data.get(0).getDepAirline();
        String depFlightNo = data.get(0).getDepFlightNo();
        String retAirline = data.get(0).getRetAirline();
        String retFlightNo = data.get(0).getRetFlightNo();
        String fare = data.get(0).getFare();
        String comment = data.get(0).getComment();
        ContentValues values = new ContentValues();
        values.put(FlightContract.FlightEntry.COLUMN_DATE,date);
        values.put(FlightContract.FlightEntry.COLUMN_TIME, time);
        values.put(FlightContract.FlightEntry.COLUMN_DEP_FLIGHT_NO, depFlightNo);
        values.put(FlightContract.FlightEntry.COLUMN_RET_AIRLINE,retAirline);
        values.put(FlightContract.FlightEntry.COLUMN_DEP_AIRLINE,depAirline);
        values.put(FlightContract.FlightEntry.COLUMN_RET_FLIGHT_NO,retFlightNo);
        values.put(FlightContract.FlightEntry.COLUMN_FARE,fare);
        values.put(FlightContract.FlightEntry.COLUMN_ORIGIN,origin);
        values.put(FlightContract.FlightEntry.COLUMN_DESTINATION,destination);
        values.put(FlightContract.FlightEntry.COLUMN_COMMENT,comment);
        values.put(FlightContract.FlightEntry.COLUMN_DATE,mydate);
        values.put(FlightContract.FlightEntry.COLUMN_TIME,temps);
        getContentResolver().insert(FlightContract.contentUri, values);
        Toast.makeText(getApplicationContext(),"Added to database", Toast.LENGTH_LONG).show();
        Log.e(Utils.class.getSimpleName(), "dep airline data data data : "+ depAirline);
        Log.e(Utils.class.getSimpleName(), "fare data data data : "+ fare);
    }


    @Override
    public void onLoaderReset(Loader loader) {

    }
}

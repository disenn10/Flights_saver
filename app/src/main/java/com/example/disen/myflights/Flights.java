package com.example.disen.myflights;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.disen.myflights.data.FlightContract;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Flights extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    ListView listView;
    FlightsAdapter flightsAdapter;
    FloatingActionButton export;
    Cursor data_copy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flights);
        export = (FloatingActionButton) findViewById(R.id.export);
        listView = (ListView)findViewById(R.id.listview);
        flightsAdapter = new FlightsAdapter(getApplicationContext(),null);
        listView.setAdapter(flightsAdapter);
        getSupportLoaderManager().initLoader(0,null,this);
        export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exportCSV();
            }
        });

    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        String[] projection = {FlightContract.FlightEntry.COLUMN_ID,FlightContract.FlightEntry.COLUMN_DATE,FlightContract.FlightEntry.COLUMN_TIME, FlightContract.FlightEntry.COLUMN_ORIGIN, FlightContract.FlightEntry.COLUMN_DESTINATION, FlightContract.FlightEntry.COLUMN_DEP_AIRLINE,
                FlightContract.FlightEntry.COLUMN_RET_AIRLINE,FlightContract.FlightEntry.COLUMN_COMMENT, FlightContract.FlightEntry.COLUMN_DEP_FLIGHT_NO, FlightContract.FlightEntry.COLUMN_RET_FLIGHT_NO, FlightContract.FlightEntry.COLUMN_FARE};

        return new android.support.v4.content.CursorLoader(this, FlightContract.contentUri, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null){
            flightsAdapter.swapCursor(data);
            data_copy = data;
            while(data_copy.moveToNext()){
                Log.e(MainActivity.class.getSimpleName(), "exportCSV: "+ data_copy.getString(data_copy.getColumnIndex(FlightContract.FlightEntry.COLUMN_ORIGIN)) );
            }
        }
    }


    @Override
    public void onLoaderReset(Loader loader) {
        flightsAdapter.swapCursor(null);

    }
    public void exportCSV(){

        int dep_airline_col = data_copy.getColumnIndex(FlightContract.FlightEntry.COLUMN_DEP_AIRLINE);
        int dep_flightNo_col = data_copy.getColumnIndex(FlightContract.FlightEntry.COLUMN_DEP_FLIGHT_NO);
        int ret_airline_col = data_copy.getColumnIndex(FlightContract.FlightEntry.COLUMN_RET_AIRLINE);
        int ret_flight_col = data_copy.getColumnIndex(FlightContract.FlightEntry.COLUMN_RET_FLIGHT_NO);
        int fare_col = data_copy.getColumnIndex(FlightContract.FlightEntry.COLUMN_FARE);
        int comment_col = data_copy.getColumnIndex(FlightContract.FlightEntry.COLUMN_COMMENT);
        int origin_col = data_copy.getColumnIndex(FlightContract.FlightEntry.COLUMN_ORIGIN);
        int dest_col = data_copy.getColumnIndex(FlightContract.FlightEntry.COLUMN_DESTINATION);
        int date_col = data_copy.getColumnIndex(FlightContract.FlightEntry.COLUMN_DATE);
        int time_col = data_copy.getColumnIndex(FlightContract.FlightEntry.COLUMN_TIME);


        String origin = null;
        String destination = null;
        String fare = null;
        String date = null;
        String time = null;
        String dep_airline = null;
        String ret_airline = null;
        String dep_flight = null;
        String ret_flight = null;
        File file   = null;
        File root   =  Environment.getExternalStorageDirectory();
            File dir    =   new File (root.getAbsolutePath() + "/PersonData");
            file   =   new File(dir, "Data.csv");
            FileOutputStream out   =   null;
            try {
                dir.mkdirs();
                out = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                out.write(String.format("%1$-20s %2$-20s %3$-20s %4$-20s %5$-20s %6$-20s %7$-20s %8$-20s %9$-20s \r\n", "Origin", "Destination", "Fare", "Date", "Time", "Flight departure", "Flight return", "Airline dep", "Airline ret").getBytes());
                while(data_copy.moveToNext()){
                    origin = data_copy.getString(origin_col);
                    destination =data_copy.getString(dest_col);
                    fare =data_copy.getString(fare_col);
                    dep_flight = data_copy.getString(dep_flightNo_col);
                    ret_flight = data_copy.getString(ret_flight_col);
                    dep_airline = data_copy.getString(dep_airline_col);
                    ret_airline = data_copy.getString(ret_airline_col);
                    date = data_copy.getString(date_col);
                    time = data_copy.getString(time_col);
                    out.write(String.format("%1$-20s %2$-20s %3$-20s %4$-20s %5$-20s %6$-20s %7$-20s %8$-20s %9$-20s \r\n", ""+origin, ""+destination, ""+fare, ""+date, ""+time, ""+dep_flight, ""+ret_flight, ""+dep_airline, ""+ret_airline).getBytes());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        Uri u1  =   null;
        u1  =   Uri.fromFile(file);
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Person Details");
        sendIntent.putExtra(Intent.EXTRA_STREAM, u1);
        sendIntent.setType("text/html");
        startActivity(sendIntent);
    }
}

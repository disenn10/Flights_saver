package com.example.disen.myflights;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v4.widget.CursorAdapter;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.disen.myflights.data.FlightContract;

/**
 * Created by disen on 9/2/2017.
 */

public class FlightsAdapter extends CursorAdapter {
    public FlightsAdapter(Context context, Cursor c) {
        super(context, c);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_items, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView dep_airline = (TextView)view.findViewById(R.id.dep_airline);
        TextView dep_flightNo = (TextView)view.findViewById(R.id.dep_flightno);
        TextView ret_airline = (TextView)view.findViewById(R.id.ret_airline);
        TextView ret_flight = (TextView)view.findViewById(R.id.ret_flightno);
        TextView fare = (TextView)view.findViewById(R.id.fare);
        TextView comment = (TextView)view.findViewById(R.id.cooment);
        TextView origin = (TextView)view.findViewById(R.id.origin);
        TextView dest = (TextView)view.findViewById(R.id.dest);
        TextView date = (TextView)view.findViewById(R.id.date);
        TextView time = (TextView)view.findViewById(R.id.time);

        int dep_airline_col = cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_DEP_AIRLINE);
        int dep_flightNo_col = cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_DEP_FLIGHT_NO);
        int ret_airline_col = cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_RET_AIRLINE);
        int ret_flight_col = cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_RET_FLIGHT_NO);
        int fare_col = cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_FARE);
        int comment_col = cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_COMMENT);
        int origin_col = cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_ORIGIN);
        int dest_col = cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_DESTINATION);
        int date_col = cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_DATE);
        int time_col = cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_TIME);

        dep_airline.setText("Airline departure:           "+cursor.getString(dep_airline_col));
        dep_flightNo.setText("Departure flight number:           "+cursor.getString(dep_flightNo_col));
        ret_airline.setText("Return airline:           "+cursor.getString(ret_airline_col));
        ret_flight.setText("Return flight number:           "+cursor.getString(ret_flight_col));
        fare.setText("Fare:           "+cursor.getString(fare_col));
        origin.setText("Origin:           "+cursor.getString(origin_col));
        dest.setText("Destination:           "+cursor.getString(dest_col));
        comment.setText("Comment:           "+cursor.getString(comment_col));
        date.setText("Date:           "+cursor.getString(date_col));
        time.setText("Time :          "+cursor.getString(time_col));
    }
}

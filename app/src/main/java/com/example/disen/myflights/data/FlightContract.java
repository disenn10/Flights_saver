package com.example.disen.myflights.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by disen on 8/30/2017.
 */

public class FlightContract implements BaseColumns{

    public static String TABLE_NAME = "Myflights";
    public static final String content_Authority = "com.example.disen.myflights";
    public static final Uri baseContent = Uri.parse("content://"+content_Authority);
    public static final Uri contentUri = Uri.withAppendedPath(baseContent, "myflights");
    public static final String path = "myflights";

    public static class FlightEntry {
        public static String COLUMN_ID = BaseColumns._ID;
        public static String COLUMN_DATE = "date";
        public static String COLUMN_TIME = "time";
        public static String COLUMN_DEP_AIRLINE = "dep_airline";
        public static String COLUMN_DEP_FLIGHT_NO = "dep_flight_number";
        public static String COLUMN_RET_AIRLINE = "ret_airline";
        public static String COLUMN_RET_FLIGHT_NO = "ret_flight_no";
        public static String COLUMN_FARE = "fare";
        public static String COLUMN_COMMENT = "comment";
        public static String COLUMN_ORIGIN = "origin";
        public static String COLUMN_DESTINATION = "destination";
    }
}

package com.example.disen.myflights.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by disen on 8/30/2017.
 */

public class FlightDatabase extends SQLiteOpenHelper {
    public final static String db_name = FlightContract.TABLE_NAME;
    public static int db_version = 5;
    String CreateEntries = "CREATE TABLE " + FlightContract.TABLE_NAME + "("
            + FlightContract.FlightEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FlightContract.FlightEntry.COLUMN_DEP_AIRLINE + " TEXT, "
            + FlightContract.FlightEntry.COLUMN_DEP_FLIGHT_NO + " TEXT, "
            + FlightContract.FlightEntry.COLUMN_RET_AIRLINE + " TEXT, "
            + FlightContract.FlightEntry.COLUMN_RET_FLIGHT_NO + " TEXT, "
            + FlightContract.FlightEntry.COLUMN_DATE + " TEXT, "
            + FlightContract.FlightEntry.COLUMN_TIME+ " TEXT, "
            + FlightContract.FlightEntry.COLUMN_FARE + " TEXT, "
            + FlightContract.FlightEntry.COLUMN_ORIGIN+ " TEXT, "
            + FlightContract.FlightEntry.COLUMN_DESTINATION + " TEXT, "
            + FlightContract.FlightEntry.COLUMN_COMMENT + " TEXT )";
    public String delete = "DROP TABLE IF EXISTS " + FlightContract.TABLE_NAME;

    public FlightDatabase(Context context) {
        super(context, db_name, null, db_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CreateEntries);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(delete);
        onCreate(db);
    }
}

package com.example.disen.myflights.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by disen on 8/30/2017.
 */

public class FlightProvider extends ContentProvider {
    private FlightDatabase myDbHelper;

    private static final int Flights = 100;
    private static final int Flights_id = 101;
    private static UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(FlightContract.content_Authority, FlightContract.path, Flights);
        sUriMatcher.addURI(FlightContract.content_Authority,  FlightContract.path + "/#", Flights_id);
    }
    @Override
    public boolean onCreate() {
        myDbHelper = new FlightDatabase(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase cursor = myDbHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);
        switch (match){
            case 100:
                return cursor.query(FlightContract.TABLE_NAME, projection, selection,selectionArgs,null,null,null);
            case 101:
                return cursor.query(FlightContract.TABLE_NAME, projection, selection,selectionArgs,null,null,null);
        }
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase sqLiteOpenHelper = myDbHelper.getWritableDatabase();
        long result = sqLiteOpenHelper.insert(FlightContract.TABLE_NAME,null,values);
        return ContentUris.withAppendedId(FlightContract.contentUri, result);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int match = sUriMatcher.match(uri);
        SQLiteDatabase liteDatabase = myDbHelper.getWritableDatabase();
        switch (match){
            case Flights:
                return liteDatabase.delete(FlightContract.TABLE_NAME,null,null);
            case Flights_id:
                selection = FlightContract.FlightEntry.COLUMN_ID +"=?";
                selectionArgs =  new String[] {String.valueOf(ContentUris.parseId(uri))};
                return liteDatabase.delete(FlightContract.TABLE_NAME,selection,selectionArgs);
            default:
                throw new IllegalArgumentException("Can't delete");
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}

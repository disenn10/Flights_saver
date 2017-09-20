package com.example.disen.myflights;

import android.content.Context;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;

import com.google.api.client.googleapis.util.*;
import com.google.api.services.qpxExpress.model.TripOptionsResponse;

import java.util.ArrayList;

/**
 * Created by disen on 8/29/2017.
 */

public class FlightsLoader extends AsyncTaskLoader {
    String origin;
    String destination;
    public FlightsLoader(Context context, String origin, String destination) {
        super(context);
        this.origin = origin;
        this.destination = destination;
    }

    @Override
    public ArrayList<FlightsInfoList> loadInBackground() {
        ArrayList<FlightsInfoList> response = Utils.Performrequest(origin, destination);
        return response;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
}

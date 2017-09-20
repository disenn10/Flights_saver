package com.example.disen.myflights;

import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.apache.GoogleApacheHttpTransport;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.qpxExpress.QPXExpress;
import com.google.api.services.qpxExpress.QPXExpressRequestInitializer;
import com.google.api.services.qpxExpress.model.FlightInfo;
import com.google.api.services.qpxExpress.model.LegInfo;
import com.google.api.services.qpxExpress.model.PassengerCounts;
import com.google.api.services.qpxExpress.model.PricingInfo;
import com.google.api.services.qpxExpress.model.SegmentInfo;
import com.google.api.services.qpxExpress.model.SliceInfo;
import com.google.api.services.qpxExpress.model.SliceInput;
import com.google.api.services.qpxExpress.model.TripOption;
import com.google.api.services.qpxExpress.model.TripOptionsRequest;
import com.google.api.services.qpxExpress.model.TripOptionsResponse;
import com.google.api.services.qpxExpress.model.TripsSearchRequest;
import com.google.api.services.qpxExpress.model.TripsSearchResponse;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by disen on 8/29/2017.
 */

public class Utils {
    List <String> carrier;

    //perform the flights request
    public static ArrayList<FlightsInfoList> Performrequest(String origin, String destination){
        ArrayList<SliceInput> slices = fill_slices(origin, destination);
        List<TripOption> response = null;
        try {
            response = makeTherequest(slices, origin, destination);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<FlightsInfoList> request = extractrequest(response);
        return request;
    }

    //Set up infos about the flights we want such as origins, destinations, carriers permitted, maximum of stops and dates..
    public static ArrayList<SliceInput> fill_slices(String origin, String destination) {
        ArrayList<SliceInput> slices = new ArrayList<SliceInput>();
        SliceInput slice = new SliceInput();
        List <String> carrier = new ArrayList<>();
        carrier.add("F9");
        carrier.add("NK");
        carrier.add("B6");
        slice.setMaxStops(0);
        slice.setOrigin(destination);
        slice.setDestination(origin);
        slice.setDate("2017-11-14");
        slices.add(slice);
        slice.setPermittedCarrier(carrier);
        return slices;
    }

    //add a round trip and make the request 
    public static List<TripOption> makeTherequest(ArrayList<SliceInput> slices,String origin, String destination ) throws GeneralSecurityException, IOException {
        final String Api_key = "AIzaSyD6xZt5VCya8Cv-JYxNXjzKKuuxXEREuBc";
        final String App_name = "com.example.disen.Myflights";
        HttpTransport httpTransport = AndroidHttp.newCompatibleTransport();
        final JsonFactory json_factory = AndroidJsonFactory.getDefaultInstance();
        //roundtrip
        ArrayList<SliceInput> slice_second = new ArrayList<>();
        List <String> carrier = new ArrayList<>();
        carrier.add("F9");
        carrier.add("NK");
        carrier.add("B6");
        SliceInput slice = new SliceInput();
        slice.setMaxStops(0);
        slice.setOrigin(origin);
        slice.setDestination(destination);
        slice.setDate("2017-11-19");
        slice.setPermittedCarrier(carrier);
        slice_second.add(slice);
        slices.add(slice);
        //-----------------------------------------------------
        TripsSearchRequest request = new TripsSearchRequest();
        TripOptionsRequest trip = new TripOptionsRequest();
        PassengerCounts passengers= new PassengerCounts();
        passengers.setAdultCount(1);
        trip.setSolutions(1);
        trip.setPassengers(passengers);
        trip.setSlice(slices);
        request.setRequest(trip);
        //get the info that is returned
        List<TripOption> trip_option = new ArrayList<>();
        QPXExpress qpx = new QPXExpress
                .Builder(httpTransport, json_factory, null)
                .setApplicationName(App_name)
                .setGoogleClientRequestInitializer(new QPXExpressRequestInitializer(Api_key))
                .build();
        TripsSearchResponse response = null;
        try {
            response = qpx.trips().search(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        trip_option = response.getTrips().getTripOption();
        return trip_option;
    }
    //Here we extract the request results
    public static ArrayList<FlightsInfoList> extractrequest(List<TripOption> trip) {
        String date = "";
        String time = "";
        String comment = "";
        ArrayList<FlightsInfoList> flights_info = new ArrayList<>();
        List<SliceInfo> sliceInfo = trip.get(0).getSlice();
        String fare = trip.get(0).getSaleTotal();
        String dep_flightNo = trip.get(0).getSlice().get(0).getSegment().get(0).getFlight().getNumber();
        String ret_flightNo = trip.get(0).getSlice().get(1).getSegment().get(0).getFlight().getNumber();

        String dep_airline = trip.get(0).getSlice().get(0).getSegment().get(0).getFlight().getCarrier();
        String ret_airline = trip.get(0).getSlice().get(1).getSegment().get(0).getFlight().getCarrier();
        flights_info.add(new FlightsInfoList(date,time,dep_airline,dep_flightNo,ret_airline,ret_flightNo,fare,""));
        return flights_info ;

    }
}

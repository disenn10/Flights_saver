package com.example.disen.myflights;

/**
 * Created by disen on 8/31/2017.
 */

public class FlightsInfoList {
    String date;
    String time;
    String Origin;
    String destination;
    String depAirline;
    String depFlightNo;
    String retAirline;
    String retFlightNo;
    String fare;
    String comment;

    //later add destination and origin

    public FlightsInfoList(String date, String time,String depAirline,
                           String depFlightNo, String retAirline, String retFlightNo, String fare, String comment){
        this.date = date;
        this.time = time;
        this.depAirline = depAirline;
        this.depFlightNo = depFlightNo;
        this.retAirline = retAirline;
        this.retFlightNo = retFlightNo;
        this.fare = fare;
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getDepAirline() {
        return depAirline;
    }

    public String getDepFlightNo() {
        return depFlightNo;
    }

    public String getDestination() {
        return destination;
    }

    public String getFare() {
        return fare;
    }

    public String getOrigin() {
        return Origin;
    }

    public String getRetAirline() {
        return retAirline;
    }

    public String getRetFlightNo() {
        return retFlightNo;
    }
}

package seng202.group4.data.dataType;

import java.util.ArrayList;


/**
 * Created by jjg64 on 15/08/16.
 */
public class Route {
    private String airline;
    private int ID;
    private String srcAirport;
    private int srcAirportID;
    private String destAirport;
    private int destAirportID;
    private String codeshare;
    private int stops;
    private double distance;
    private ArrayList<String> equipments = new ArrayList<String>();

    Route() {

    }


    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getSrcAirport() {
        return srcAirport;
    }

    public void setSrcAirport(String srcAirport) {
        this.srcAirport = srcAirport;
    }

    public int getSrcAirportID() {
        return srcAirportID;
    }

    public void setSrcAirportID(int srcAirportID) {
        this.srcAirportID = srcAirportID;
    }

    public String getDestAirport() {
        return destAirport;
    }

    public void setDestAirport(String destAirport) {
        this.destAirport = destAirport;
    }

    public int getDestAirportID() {
        return destAirportID;
    }

    public void setDestAirportID(int destAirportID) {
        this.destAirportID = destAirportID;
    }

    public String getCodeshare() {
        return codeshare;
    }

    public void setCodeshare(String codeshare) {
        this.codeshare = codeshare;
    }

    public int getStops() {
        return stops;
    }

    public void setStops(int stops) {
        this.stops = stops;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }


    public ArrayList<String> getEquipments() {
        return equipments;
    }

    public void setEquipments(String equips) {
        for (String equipment: equips.split(" ")) {
            equipments.add(equipment);
        }
    }
}

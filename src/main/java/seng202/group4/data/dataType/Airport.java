package seng202.group4.data.dataType;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Stores information about an Airport.
 * The airport class creates the airport object using the information for the airport and allows for the construction of
 * the airport table.
 */
public class Airport implements Serializable {
    private int ID;
    private String name;
    private String city;
    private String country;
    private String IATA; // FAA if the country is USA
    private String ICAO;
    private double latitude;
    private double longitude;
    private double altitude;
    private float timezone;
    private DaylightSavingsTime DST;
    private String tz;
    private ArrayList<Route> routes = new ArrayList<Route>();


    /**
     * Initializes all of the variables for the airport class.
     * @param ID An Integer for the airport ID
     * @param name A String for the airport name
     * @param city A String for the airport city
     * @param country A String for the airport country
     * @param IATA A String for the airport IATA
     * @param ICAO A String for the airport ICAO
     * @param latitude A Double for the airport latitude
     * @param longitude A Double for the airport longitude
     * @param altitude A Double for the airport altitude
     * @param timezone A Float for the airport timezone
     * @param DST A DaylightSavingsTime enum for the airport DST
     * @param tz A String for the airport tzdata
     */
    public Airport(int ID, String name, String city, String country, String IATA, String ICAO, double latitude,
            double longitude, double altitude, float timezone, DaylightSavingsTime DST, String tz) {
        this.ID = ID;
        this.name = name;
        this.city = city;
        this.country = country;
        this.IATA = IATA;
        this.ICAO = ICAO;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.timezone = timezone;
        this.DST = DST;
        this.tz = tz;
    }


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getIATA() {
        return IATA;
    }

    public void setIATA(String IATA) {
        this.IATA = IATA;
    }

    public String getICAO() {
        return ICAO;
    }

    public void setICAO(String ICAO) {
        this.ICAO = ICAO;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public float getTimezone() {
        return timezone;
    }

    public void setTimezone(float timezone) {
        this.timezone = timezone;
    }

    public DaylightSavingsTime getDST() {
        return DST;
    }

    public void setDST(DaylightSavingsTime DST) {
        this.DST = DST;
    }

    public String getTz() {
        return tz;
    }

    public void setTz(String tz) {
        this.tz = tz;
    }

    public ArrayList<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(ArrayList<Route> routes) {
        this.routes = routes;
    }

    /**
     * Add route where this airport is the source
     * @param route Route
     */
    public void addRoute(Route route) {
        routes.add(route);
    }
}

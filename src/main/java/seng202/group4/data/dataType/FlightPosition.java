package seng202.group4.data.dataType;

import java.io.Serializable;

/**
 * Stores a single flight position.
 * The FlightPosition class gets the information from the specific row in the flight table which the flight exists in.
 */
public class FlightPosition implements Serializable {
    private String type;
    private String ID;
    private double altitude;
    private double latitude;
    private double longitude;


    public FlightPosition(String type, String ID, double altitude, double latitude, double longitude) {
        this.type = type;
        this.ID = ID;
        this.altitude = altitude;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
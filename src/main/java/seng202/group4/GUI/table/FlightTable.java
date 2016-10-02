package seng202.group4.GUI.table;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Used to make a row of information to populate the flight table based upon given data.
 */
public class FlightTable {
    private SimpleStringProperty fid;
    private SimpleStringProperty ftype;
    private SimpleDoubleProperty faltitude;
    private SimpleDoubleProperty flatitude;
    private SimpleDoubleProperty flongitude;

    /**
     * Initializes all the variable values in FlightTable, making a row in the table..
     * @param source String
     * @param destination String
     * @param altitude Double
     * @param latitude Double
     * @param longitude Double
     */
    public FlightTable(String source, String destination, Double altitude, Double latitude, Double longitude){
        this.fid = new SimpleStringProperty(source);
        this.ftype = new SimpleStringProperty(destination);
        this.faltitude = new SimpleDoubleProperty(altitude);
        this.flatitude = new SimpleDoubleProperty(latitude);
        this.flongitude = new SimpleDoubleProperty(longitude);
    }

    /**
     * A getter for the if value of the flight in the flight table.
     * @return fid
     */
    public String getFid() {
        return fid.get();
    }

    /**
     * Retrieves the id value of the flight from the parsed data.
     * @return fid
     */
    public SimpleStringProperty fidProperty() {
        return fid;
    }

    /**
     * Sets the id value of the flight in the flight table.
     * @param fid String
     */
    public void setFid(String fid) {
        this.fid.set(fid);
    }

    /**
     * A getter for the flight type in the flight table.
     * @return ftype
     */
    public String getFtype() {
        return ftype.get();
    }

    /**
     * Retrieves the flight type from the parsed data.
     * @return ftype
     */
    public SimpleStringProperty ftypeProperty() {
        return ftype;
    }

    /**
     * Sets the flight type value in the flight table.
     * @param ftype String
     */
    public void setFtype(String ftype) {
        this.ftype.set(ftype);
    }

    /**
     * A getter for the altitude of the flight in the flight table.
     * @return faltitude
     */
    public double getFaltitude() {
        return faltitude.get();
    }

    /**
     * Retrieves the flight altitude from the parsed flight data.
     * @return faltitude
     */
    public SimpleDoubleProperty faltitudeProperty() {
        return faltitude;
    }

    /**
     * Sets the flight altitude in the flight table.
     * @param faltitude double
     */
    public void setFaltitude(double faltitude) {
        this.faltitude.set(faltitude);
    }

    /**
     * A getter for the flight latitude in the flight table.
     * @return flatitude
     */
    public double getFlatitude() {
        return flatitude.get();
    }

    /**
     * Retrieves the flight latitude from the parsed flight data.
     * @return flatitude
     */
    public SimpleDoubleProperty flatitudeProperty() {
        return flatitude;
    }

    /**
     * Sets the flight latitude for in the flight table.
     * @param flatitude double
     */
    public void setFlatitude(double flatitude) {
        this.flatitude.set(flatitude);
    }

    /**
     * A getter for the flight longitude in the flight table.
     * @return flongitude
     */
    public double getFlongitude() {
        return flongitude.get();
    }

    /**
     * Retrieves the flight longitude from the parsed flight data.
     * @return flongitude
     */
    public SimpleDoubleProperty flongitudeProperty() {
        return flongitude;
    }

    /**
     * Sets the flight longitude in the flight table.
     * @param flongitude double
     */
    public void setFlongitude(double flongitude) {
        this.flongitude.set(flongitude);
    }



}

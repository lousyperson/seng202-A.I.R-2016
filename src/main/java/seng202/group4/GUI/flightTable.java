package seng202.group4.GUI;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Pang on 14/09/16.
 */
public class flightTable {
    private SimpleStringProperty fid;
    private SimpleStringProperty ftype;
    private SimpleDoubleProperty faltitude;
    private SimpleDoubleProperty flatitude;
    private SimpleDoubleProperty flongitude;

    public flightTable(String source, String destination, Double altitude, Double latitude, Double longitude){
        this.fid = new SimpleStringProperty(source);
        this.ftype = new SimpleStringProperty(destination);
        this.faltitude = new SimpleDoubleProperty(altitude);
        this.flatitude = new SimpleDoubleProperty(latitude);
        this.flongitude = new SimpleDoubleProperty(longitude);
    }

    public String getFid() {
        return fid.get();
    }

    public SimpleStringProperty fidProperty() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid.set(fid);
    }

    public String getFtype() {
        return ftype.get();
    }

    public SimpleStringProperty ftypeProperty() {
        return ftype;
    }

    public void setFtype(String ftype) {
        this.ftype.set(ftype);
    }

    public double getFaltitude() {
        return faltitude.get();
    }

    public SimpleDoubleProperty faltitudeProperty() {
        return faltitude;
    }

    public void setFaltitude(double faltitude) {
        this.faltitude.set(faltitude);
    }

    public double getFlatitude() {
        return flatitude.get();
    }

    public SimpleDoubleProperty flatitudeProperty() {
        return flatitude;
    }

    public void setFlatitude(double flatitude) {
        this.flatitude.set(flatitude);
    }

    public double getFlongitude() {
        return flongitude.get();
    }

    public SimpleDoubleProperty flongitudeProperty() {
        return flongitude;
    }

    public void setFlongitude(double flongitude) {
        this.flongitude.set(flongitude);
    }



}

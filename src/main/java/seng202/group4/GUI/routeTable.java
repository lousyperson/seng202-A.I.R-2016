package seng202.group4.GUI;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * The routeTable class is called for singular pieces of flight data to enable information that has been parsed in from
 * a data file to be used in populating a route table.
 * Created by psu43 on 28/08/16.
 */
public class routeTable {
    private SimpleStringProperty rairline;
    private SimpleStringProperty rid;
    private SimpleStringProperty rsource;
    private SimpleStringProperty rsourceid;
    private SimpleStringProperty rdest;
    private SimpleStringProperty rdestid;
    private SimpleStringProperty rcodeshare;
    private SimpleStringProperty rstops;
    private SimpleStringProperty requipment;

    public routeTable(String airline, String id, String source, String sourceid, String dest, String destid, String codeshare, String stops, String equipment){
        this.rairline = new SimpleStringProperty(airline);
        this.rid = new SimpleStringProperty(id);
        this.rsource = new SimpleStringProperty(source);
        this.rsourceid = new SimpleStringProperty(sourceid);
        this.rdest = new SimpleStringProperty(dest);
        this.rdestid = new SimpleStringProperty(destid);
        this.rcodeshare = new SimpleStringProperty(codeshare);
        this.rstops = new SimpleStringProperty(stops);
        this.requipment = new SimpleStringProperty(equipment);

    }


    /**
     * A getter for the airline name that operates the route for the route table.
     * @return
     */
    public String getRairline() {
        return rairline.get();
    }

    /**
     * Retrieves the name of the airline that operates the route from the parsed route data.
     * @return
     */
    public SimpleStringProperty rairlineProperty() {
        return rairline;
    }

    /**
     * Sets the name of the airline that operates the route in the route table.
     * @param rairline
     */
    public void setRairline(String rairline) {
        this.rairline.set(rairline);
    }

    /**
     * A getter for the route id for the route table.
     * @return
     */
    public String getRid() {
        return rid.get();
    }

    /**
     * Retrieves the route id value from the parsed route data.
     * @return
     */
    public SimpleStringProperty ridProperty() {
        return rid;
    }

    /**
     * Sets the route id in the route table.
     * @param rid
     */
    public void setRid(String rid) {
        this.rid.set(rid);
    }

    /**
     * A getter for the souce airport of the route for the route table.
     * @return
     */
    public String getRsource() {
        return rsource.get();
    }

    /**
     * Retrieves the source airport from the parsed route data.
     * @return
     */
    public SimpleStringProperty rsourceProperty() {
        return rsource;
    }

    /**
     * Sets the source airport in the route table.
     * @param rsource
     */
    public void setRsource(String rsource) {
        this.rsource.set(rsource);
    }

    /**
     * A getter for the source airport id for the route table.
     * @return
     */
    public String getRsourceid() {
        return rsourceid.get();
    }

    /**
     * Retrieves the source airport id from the parsed route data.
     * @return
     */
    public SimpleStringProperty rsourceidProperty() {
        return rsourceid;
    }

    /**
     * Sets the source airport id in the route table.
     * @param rsourceid
     */
    public void setRsourceid(String rsourceid) {
        this.rsourceid.set(rsourceid);
    }

    /**
     * A getter for the destination airport of the route for the route table.
     * @return
     */
    public String getRdest() {
        return rdest.get();
    }

    /**
     * Retrieves the destination airport from the parsed route data.
     * @return
     */
    public SimpleStringProperty rdestProperty() {
        return rdest;
    }

    /**
     * Sets the destination airport for the route in the route table.
     * @param rdest
     */
    public void setRdest(String rdest) {
        this.rdest.set(rdest);
    }

    /**
     * A getter for the route's destination airport identification number in the route table.
     * @return
     */
    public String getRdestid() {
        return rdestid.get();
    }

    /**
     * Retrieves the route's destination airport identifaction number from the parsed route data.
     * @return
     */
    public SimpleStringProperty rdestidProperty() {
        return rdestid;
    }

    /**
     * Sets the route's destination airport identification number in the route table.
     * @param rdestid
     */
    public void setRdestid(String rdestid) {
        this.rdestid.set(rdestid);
    }

    /**
     * A getter for the route codeshare for the route table.
     * @return
     */
    public String getRcodeshare() {
        return rcodeshare.get();
    }

    /**
     *Retrieves the codeshare value for the route from the parsed route data.
     * @return
     */
    public SimpleStringProperty rcodeshareProperty() {
        return rcodeshare;
    }

    /**
     * Sets the codeshare value for the route in the route table.
     * @param rcodeshare
     */
    public void setRcodeshare(String rcodeshare) {
        this.rcodeshare.set(rcodeshare);
    }

    /**
     * A getter for the routes number of stops for the route table.
     * @return
     */
    public String getRstops() {
        return rstops.get();
    }

    /**
     * Retrieves the number of stops for the route from the parsed route data.
     * @return
     */
    public SimpleStringProperty rstopsProperty() {
        return rstops;
    }

    /**
     * Sets the routes number of stops in the route table.
     * @param rstops
     */
    public void setRstops(String rstops) {
        this.rstops.set(rstops);
    }

    /**
     * A getter for the equipment utilised by the route for the route table.
     * @return
     */
    public String getRequipment() {
        return requipment.get();
    }

    /**
     * Retrieves the equipment information from the parsed route data.
     * @return
     */
    public SimpleStringProperty requipmentProperty() {
        return requipment;
    }

    /**
     * Sets the route equipment information in the route table.
     * @param requipment
     */
    public void setRequipment(String requipment) {
        this.requipment.set(requipment);
    }






}

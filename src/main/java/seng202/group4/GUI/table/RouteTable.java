package seng202.group4.GUI.table;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * The RouteTable class is called for singular pieces of flight data to enable information that has been parsed in from
 * a data file to be used in populating a route table.
 */
public class RouteTable {
    private SimpleStringProperty rairline;
    private SimpleStringProperty rid;
    private SimpleStringProperty rsource;
    private SimpleStringProperty rsourceid;
    private SimpleStringProperty rdest;
    private SimpleStringProperty rdestid;
    private SimpleStringProperty rcodeshare;
    private SimpleStringProperty rstops;
    private SimpleStringProperty requipment;

    public RouteTable(String airline, String id, String source, String sourceid, String dest, String destid, String codeshare, String stops, String equipment){
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
     * @return rairline
     */
    public String getRairline() {
        return rairline.get();
    }

    /**
     * Retrieves the name of the airline that operates the route from the parsed route data.
     * @return rairline
     */
    public SimpleStringProperty rairlineProperty() {
        return rairline;
    }

    /**
     * Sets the name of the airline that operates the route in the route table.
     * @param rairline String
     */
    public void setRairline(String rairline) {
        this.rairline.set(rairline);
    }

    /**
     * A getter for the route id for the route table.
     * @return rid
     */
    public String getRid() {
        return rid.get();
    }

    /**
     * Retrieves the route id value from the parsed route data.
     * @return rid
     */
    public SimpleStringProperty ridProperty() {
        return rid;
    }

    /**
     * Sets the route id in the route table.
     * @param rid String
     */
    public void setRid(String rid) {
        this.rid.set(rid);
    }

    /**
     * A getter for the souce airport of the route for the route table.
     * @return rsource
     */
    public String getRsource() {
        return rsource.get();
    }

    /**
     * Retrieves the source airport from the parsed route data.
     * @return rsource
     */
    public SimpleStringProperty rsourceProperty() {
        return rsource;
    }

    /**
     * Sets the source airport in the route table.
     * @param rsource String
     */
    public void setRsource(String rsource) {
        this.rsource.set(rsource);
    }

    /**
     * A getter for the source airport id for the route table.
     * @return rsourceid
     */
    public String getRsourceid() {
        return rsourceid.get();
    }

    /**
     * Retrieves the source airport id from the parsed route data.
     * @return rsourceid
     */
    public SimpleStringProperty rsourceidProperty() {
        return rsourceid;
    }

    /**
     * Sets the source airport id in the route table.
     * @param rsourceid String
     */
    public void setRsourceid(String rsourceid) {
        this.rsourceid.set(rsourceid);
    }

    /**
     * A getter for the destination airport of the route for the route table.
     * @return rdest
     */
    public String getRdest() {
        return rdest.get();
    }

    /**
     * Retrieves the destination airport from the parsed route data.
     * @return rdest
     */
    public SimpleStringProperty rdestProperty() {
        return rdest;
    }

    /**
     * Sets the destination airport for the route in the route table.
     * @param rdest String
     */
    public void setRdest(String rdest) {
        this.rdest.set(rdest);
    }

    /**
     * A getter for the route's destination airport identification number in the route table.
     * @return rdestid
     */
    public String getRdestid() {
        return rdestid.get();
    }

    /**
     * Retrieves the route's destination airport identifaction number from the parsed route data.
     * @return rdestid
     */
    public SimpleStringProperty rdestidProperty() {
        return rdestid;
    }

    /**
     * Sets the route's destination airport identification number in the route table.
     * @param rdestid String
     */
    public void setRdestid(String rdestid) {
        this.rdestid.set(rdestid);
    }

    /**
     * A getter for the route codeshare for the route table.
     * @return rcodeshare
     */
    public String getRcodeshare() {
        return rcodeshare.get();
    }

    /**
     *Retrieves the codeshare value for the route from the parsed route data.
     * @return rcodeshare
     */
    public SimpleStringProperty rcodeshareProperty() {
        return rcodeshare;
    }

    /**
     * Sets the codeshare value for the route in the route table.
     * @param rcodeshare String
     */
    public void setRcodeshare(String rcodeshare) {
        this.rcodeshare.set(rcodeshare);
    }

    /**
     * A getter for the routes number of stops for the route table.
     * @return rstops
     */
    public String getRstops() {
        return rstops.get();
    }

    /**
     * Retrieves the number of stops for the route from the parsed route data.
     * @return rstops
     */
    public SimpleStringProperty rstopsProperty() {
        return rstops;
    }

    /**
     * Sets the routes number of stops in the route table.
     * @param rstops String
     */
    public void setRstops(String rstops) {
        this.rstops.set(rstops);
    }

    /**
     * A getter for the equipment utilised by the route for the route table.
     * @return requipment
     */
    public String getRequipment() {
        return requipment.get();
    }

    /**
     * Retrieves the equipment information from the parsed route data.
     * @return requipment
     */
    public SimpleStringProperty requipmentProperty() {
        return requipment;
    }

    /**
     * Sets the route equipment information in the route table.
     * @param requipment String
     */
    public void setRequipment(String requipment) {
        this.requipment.set(requipment);
    }






}

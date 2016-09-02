package seng202.group4.GUI;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
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


    public String getRairline() {
        return rairline.get();
    }

    public SimpleStringProperty rairlineProperty() {
        return rairline;
    }

    public void setRairline(String rairline) {
        this.rairline.set(rairline);
    }

    public String getRid() {
        return rid.get();
    }

    public SimpleStringProperty ridProperty() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid.set(rid);
    }

    public String getRsource() {
        return rsource.get();
    }

    public SimpleStringProperty rsourceProperty() {
        return rsource;
    }

    public void setRsource(String rsource) {
        this.rsource.set(rsource);
    }

    public String getRsourceid() {
        return rsourceid.get();
    }

    public SimpleStringProperty rsourceidProperty() {
        return rsourceid;
    }

    public void setRsourceid(String rsourceid) {
        this.rsourceid.set(rsourceid);
    }

    public String getRdest() {
        return rdest.get();
    }

    public SimpleStringProperty rdestProperty() {
        return rdest;
    }

    public void setRdest(String rdest) {
        this.rdest.set(rdest);
    }

    public String getRdestid() {
        return rdestid.get();
    }

    public SimpleStringProperty rdestidProperty() {
        return rdestid;
    }

    public void setRdestid(String rdestid) {
        this.rdestid.set(rdestid);
    }

    public String getRcodeshare() {
        return rcodeshare.get();
    }

    public SimpleStringProperty rcodeshareProperty() {
        return rcodeshare;
    }

    public void setRcodeshare(String rcodeshare) {
        this.rcodeshare.set(rcodeshare);
    }

    public String getRstops() {
        return rstops.get();
    }

    public SimpleStringProperty rstopsProperty() {
        return rstops;
    }

    public void setRstops(String rstops) {
        this.rstops.set(rstops);
    }

    public String getRequipment() {
        return requipment.get();
    }

    public SimpleStringProperty requipmentProperty() {
        return requipment;
    }

    public void setRequipment(String requipment) {
        this.requipment.set(requipment);
    }






}

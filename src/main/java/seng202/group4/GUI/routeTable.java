package seng202.group4.GUI;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by psu43 on 28/08/16.
 */
public class routeTable {
    private SimpleStringProperty rairline;
    private SimpleIntegerProperty rid;
    private SimpleStringProperty rsource;
    private SimpleIntegerProperty rsourceid;
    private SimpleStringProperty rdest;
    private SimpleIntegerProperty rdestid;
    private SimpleStringProperty rcodeshare;
    private SimpleIntegerProperty rstops;
    private SimpleStringProperty requipment;

    public routeTable(String airline, Integer id, String source, Integer sourceid, String dest, Integer destid, String codeshare, Integer stops, String equipment){
        this.rairline = new SimpleStringProperty(airline);
        this.rid = new SimpleIntegerProperty(id);
        this.rsource = new SimpleStringProperty(source);
        this.rsourceid = new SimpleIntegerProperty(sourceid);
        this.rdest = new SimpleStringProperty(dest);
        this.rdestid = new SimpleIntegerProperty(destid);
        this.rcodeshare = new SimpleStringProperty(codeshare);
        this.rstops = new SimpleIntegerProperty(stops);
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

    public int getRid() {
        return rid.get();
    }

    public SimpleIntegerProperty ridProperty() {
        return rid;
    }

    public void setRid(int rid) {
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

    public int getRsourceid() {
        return rsourceid.get();
    }

    public SimpleIntegerProperty rsourceidProperty() {
        return rsourceid;
    }

    public void setRsourceid(int rsourceid) {
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

    public int getRdestid() {
        return rdestid.get();
    }

    public SimpleIntegerProperty rdestidProperty() {
        return rdestid;
    }

    public void setRdestid(int rdestid) {
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

    public int getRstops() {
        return rstops.get();
    }

    public SimpleIntegerProperty rstopsProperty() {
        return rstops;
    }

    public void setRstops(int rstops) {
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

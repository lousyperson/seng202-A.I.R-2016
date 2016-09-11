package seng202.group4.GUI;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import seng202.group4.data.dataType.DaylightSavingsTime;


/**
 * Created by psu43 on 25/08/16.
 */
public class airportTable {

    private SimpleIntegerProperty atid;
    private SimpleStringProperty atname;
    private SimpleStringProperty atcity;
    private SimpleStringProperty atcountry;
    private SimpleStringProperty atiata;
    private SimpleStringProperty aticao;
    private SimpleDoubleProperty atlatitude;
    private SimpleDoubleProperty atlongitude;
    private SimpleDoubleProperty ataltitude;
    private SimpleFloatProperty attimezone;
    private SimpleStringProperty atdst;
    private SimpleStringProperty attzdatabase;

    public airportTable(int ttid, String ttname, String ttcity, String ttcountry, String ttiata, String tticao,
                        double ttlatitude, double ttlongitude, double ttaltitude, float tttimezone, String ttdst, String tttzdatabase){
        this.atid = new SimpleIntegerProperty(ttid);
        this.atname = new SimpleStringProperty(ttname);
        this.atcity = new SimpleStringProperty(ttcity);
        this.atcountry = new SimpleStringProperty(ttcountry);
        this.atiata = new SimpleStringProperty(ttiata);
        this.aticao = new SimpleStringProperty(tticao);
        this.atlatitude = new SimpleDoubleProperty(ttlatitude);
        this.atlongitude = new SimpleDoubleProperty(ttlongitude);
        this.ataltitude = new SimpleDoubleProperty(ttaltitude);
        this.attimezone = new SimpleFloatProperty(tttimezone);
        this.atdst = new SimpleStringProperty(ttdst);
        this.attzdatabase = new SimpleStringProperty(tttzdatabase);



    }


    public int getAtid() {
        return atid.get();
    }

    public SimpleIntegerProperty atidProperty() {
        return atid;
    }

    public void setAtid(int atid) {
        this.atid.set(atid);
    }

    public String getAtname() {
        return atname.get();
    }

    public SimpleStringProperty atnameProperty() {
        return atname;
    }

    public void setAtname(String atname) {
        this.atname.set(atname);
    }

    public String getAtcity() {
        return atcity.get();
    }

    public SimpleStringProperty atcityProperty() {
        return atcity;
    }

    public void setAtcity(String atcity) {
        this.atcity.set(atcity);
    }

    public String getAtcountry() {
        return atcountry.get();
    }

    public SimpleStringProperty atcountryProperty() {
        return atcountry;
    }

    public void setAtcountry(String atcountry) {
        this.atcountry.set(atcountry);
    }

    public String getAtiata() {
        return atiata.get();
    }

    public SimpleStringProperty atiataProperty() {
        return atiata;
    }

    public void setAtiata(String atiata) {
        this.atiata.set(atiata);
    }

    public String getAticao() {
        return aticao.get();
    }

    public SimpleStringProperty aticaoProperty() {
        return aticao;
    }

    public void setAticao(String aticao) {
        this.aticao.set(aticao);
    }

    public double getAtlatitude() {
        return atlatitude.get();
    }

    public SimpleDoubleProperty atlatitudeProperty() {
        return atlatitude;
    }

    public void setAtlatitude(double atlatitude) {
        this.atlatitude.set(atlatitude);
    }

    public double getAtlongitude() {
        return atlongitude.get();
    }

    public SimpleDoubleProperty atlongitudeProperty() {
        return atlongitude;
    }

    public void setAtlongitude(double atlongitude) {
        this.atlongitude.set(atlongitude);
    }

    public double getAtaltitude() {
        return ataltitude.get();
    }

    public SimpleDoubleProperty ataltitudeProperty() {
        return ataltitude;
    }

    public void setAtaltitude(double ataltitude) {
        this.ataltitude.set(ataltitude);
    }

    public float getAttimezone() {
        return attimezone.get();
    }

    public SimpleFloatProperty attimezoneProperty() {
        return attimezone;
    }

    public void setAttimezone(float attimezone) {
        this.attimezone.set(attimezone);
    }

    public String getAtdst() {
        return atdst.get();
    }

    public SimpleStringProperty atdstProperty() {
        return atdst;
    }

    public void setAtdst(String atdst) {
        this.atdst.set(atdst);
    }

    public String getAttzdatabase() {
        return attzdatabase.get();
    }

    public SimpleStringProperty attzdatabaseProperty() {
        return attzdatabase;
    }

    public void setAttzdatabase(String attzdatabase) {
        this.attzdatabase.set(attzdatabase);
    }
}
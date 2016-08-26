package seng202.group4.GUI;

import javafx.beans.property.SimpleStringProperty;


/**
 * Created by psu43 on 25/08/16.
 */
public class airportTable {

    private SimpleStringProperty atid;
    private SimpleStringProperty atname;
    private SimpleStringProperty atcity;
    private SimpleStringProperty atcountry;
    private SimpleStringProperty atiata;
    private SimpleStringProperty aticao;
    private SimpleStringProperty atlatitude;
    private SimpleStringProperty atlongitude;
    private SimpleStringProperty ataltitude;
    private SimpleStringProperty attimezone;
    private SimpleStringProperty atdst;
    private SimpleStringProperty attzdatabase;

    public airportTable(String ttid, String ttname, String ttcity, String ttcountry, String ttiata, String tticao, String ttlatitude, String ttlongitude, String ttaltitude, String tttimezone, String ttdst, String tttzdatabase){
        this.atid = new SimpleStringProperty(ttid);
        this.atname = new SimpleStringProperty(ttname);
        this.atcity = new SimpleStringProperty(ttcity);
        this.atcountry = new SimpleStringProperty(ttcountry);
        this.atiata = new SimpleStringProperty(ttiata);
        this.aticao = new SimpleStringProperty(tticao);
        this.atlatitude = new SimpleStringProperty(ttlatitude);
        this.atlongitude = new SimpleStringProperty(ttlongitude);
        this.ataltitude = new SimpleStringProperty(ttaltitude);
        this.attimezone = new SimpleStringProperty(tttimezone);
        this.atdst = new SimpleStringProperty(ttdst);
        this.attzdatabase = new SimpleStringProperty(tttzdatabase);



    }

    public String getAtid() {
        return atid.get();
    }

    public SimpleStringProperty atidProperty() {
        return atid;
    }

    public void setAtid(String atid) {
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

    public String getAtlatitude() {
        return atlatitude.get();
    }

    public SimpleStringProperty atlatitudeProperty() {
        return atlatitude;
    }

    public void setAtlatitude(String atlatitude) {
        this.atlatitude.set(atlatitude);
    }

    public String getAtlongitude() {
        return atlongitude.get();
    }

    public SimpleStringProperty atlongitudeProperty() {
        return atlongitude;
    }

    public void setAtlongitude(String atlongitude) {
        this.atlongitude.set(atlongitude);
    }

    public String getAtaltitude() {
        return ataltitude.get();
    }

    public SimpleStringProperty ataltitudeProperty() {
        return ataltitude;
    }

    public void setAtaltitude(String ataltitude) {
        this.ataltitude.set(ataltitude);
    }

    public String getAttimezone() {
        return attimezone.get();
    }

    public SimpleStringProperty attimezoneProperty() {
        return attimezone;
    }

    public void setAttimezone(String attimezone) {
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
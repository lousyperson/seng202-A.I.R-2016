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

    /**
     * Initializes the variables within the class.
     * @param ttid
     * @param ttname
     * @param ttcity
     * @param ttcountry
     * @param ttiata
     * @param tticao
     * @param ttlatitude
     * @param ttlongitude
     * @param ttaltitude
     * @param tttimezone
     * @param ttdst
     * @param tttzdatabase
     */
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


    /**
     * A getter for the airport id value in the table.
     * @return
     */
    public int getAtid() {
        return atid.get();
    }

    /**
     * Retrieves the id of the airport from the parsed in data.
     * @return
     */
    public SimpleIntegerProperty atidProperty() {
        return atid;
    }

    /**
     * Sets the id value in the airport table
     * @param atid
     */
    public void setAtid(int atid) {
        this.atid.set(atid);
    }

    /**
     * Gets the value of the airport name in the airport table
     * @return
     */
    public String getAtname() {
        return atname.get();
    }

    /**
     * Retrieves the airport name that has been parsed in by the data file.
     * @return
     */
    public SimpleStringProperty atnameProperty() {
        return atname;
    }

    /**
     * Sets the name of the airport in the airport table.
     * @param atname
     */
    public void setAtname(String atname) {
        this.atname.set(atname);
    }

    /**
     * Gets the city of the airport in the airport table.
     * @return
     */
    public String getAtcity() {
        return atcity.get();
    }

    /**
     * Retrieves that value of the airport city as parsed in from the data file.
     * @return
     */
    public SimpleStringProperty atcityProperty() {
        return atcity;
    }

    /**
     * Sets the value of the city in the airport table.
     * @param atcity
     */
    public void setAtcity(String atcity) {
        this.atcity.set(atcity);
    }

    /**
     * Gets the value of the airport country in the airport table.
     * @return
     */
    public String getAtcountry() {
        return atcountry.get();
    }

    /**
     * Retrieves the value of the country from the parsed data.
     * @return
     */
    public SimpleStringProperty atcountryProperty() {
        return atcountry;
    }

    /**
     * Sets the value of the country in the table.
     * @param atcountry
     */
    public void setAtcountry(String atcountry) {
        this.atcountry.set(atcountry);
    }

    /**
     * Gets the airport IATA value for the airport table.
     * @return
     */
    public String getAtiata() {
        return atiata.get();
    }

    /**
     * Retrieves the airport IATA value from the parsed data.
     * @return
     */
    public SimpleStringProperty atiataProperty() {
        return atiata;
    }

    /**
     * Sets the IATA number in the airport table.
     * @param atiata
     */
    public void setAtiata(String atiata) {
        this.atiata.set(atiata);
    }

    /**
     * A getter for teh ICAO number of the airport in the airport table.
     * @return
     */
    public String getAticao() {
        return aticao.get();
    }

    /**
     * Retrieves the airports IATA number from the parsed data.
     * @return
     */
    public SimpleStringProperty aticaoProperty() {
        return aticao;
    }

    /**
     * Sets the airport IATA number in the airport table.
     * @param aticao
     */
    public void setAticao(String aticao) {
        this.aticao.set(aticao);
    }

    /**
     * A getter for the airport latitude in the airport table.
     * @return
     */
    public double getAtlatitude() {
        return atlatitude.get();
    }

    /**
     * Retrieves the airport latitude from the parsed data.
     * @return
     */
    public SimpleDoubleProperty atlatitudeProperty() {
        return atlatitude;
    }

    /**
     * Sets the airport latitude in the airport table.
     * @param atlatitude
     */
    public void setAtlatitude(double atlatitude) {
        this.atlatitude.set(atlatitude);
    }

    /**
     * A getter for the airport longitude in the airport table.
     * @return
     */
    public double getAtlongitude() {
        return atlongitude.get();
    }

    /**
     * Retrieves the longitude of the airport from the parsed data.
     * @return
     */
    public SimpleDoubleProperty atlongitudeProperty() {
        return atlongitude;
    }

    /**
     * Sets the longitude of the airport in the airport table.
     * @param atlongitude
     */
    public void setAtlongitude(double atlongitude) {
        this.atlongitude.set(atlongitude);
    }

    /**
     * A getter for the altitude of the airport in the airport table
     * @return
     */
    public double getAtaltitude() {
        return ataltitude.get();
    }

    /**
     * Retrieves the altitude of the airport from the parsed data.
     * @return
     */
    public SimpleDoubleProperty ataltitudeProperty() {
        return ataltitude;
    }

    /**
     * Sets the altitude of the airport in the airport table.
     * @param ataltitude
     */
    public void setAtaltitude(double ataltitude) {
        this.ataltitude.set(ataltitude);
    }

    /**
     * A getter for the airport timezone in the airport table.
     * @return
     */
    public float getAttimezone() {
        return attimezone.get();
    }

    /**
     * Retrieves the airport timezone form the parsed data.
     * @return
     */
    public SimpleFloatProperty attimezoneProperty() {
        return attimezone;
    }

    /**
     * Sets the airport timezone in the airport table.
     * @param attimezone
     */
    public void setAttimezone(float attimezone) {
        this.attimezone.set(attimezone);
    }

    /**
     * A getter for the airport daylight saving time.
     * @return
     */
    public String getAtdst() {
        return atdst.get();
    }

    /**
     * Retrieves the daylight saving time from the parsed data.
     * @return
     */
    public SimpleStringProperty atdstProperty() {
        return atdst;
    }

    /**
     * Sets the daylight saving time in the airport table.
     * @param atdst
     */
    public void setAtdst(String atdst) {
        this.atdst.set(atdst);
    }

    /**
     * A getter for the airport time zone database for the airport table.
     * @return
     */
    public String getAttzdatabase() {
        return attzdatabase.get();
    }

    /**
     * Retrieves the airport timezone database from the parsed data.
     * @return
     */
    public SimpleStringProperty attzdatabaseProperty() {
        return attzdatabase;
    }

    /**
     * Sets the timezone database in the airport talle.
     * @param attzdatabase
     */
    public void setAttzdatabase(String attzdatabase) {
        this.attzdatabase.set(attzdatabase);
    }
}
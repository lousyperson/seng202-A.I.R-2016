package seng202.group4.GUI.table;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import seng202.group4.data.dataType.DaylightSavingsTime;


/**
 * Used to make a row of information to populate the airline table based upon given data.
 */
public class AirportTable {

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
     * @param ttid int
     * @param ttname String
     * @param ttcity String
     * @param ttcountry String
     * @param ttiata String
     * @param tticao String
     * @param ttlatitude double
     * @param ttlongitude double
     * @param ttaltitude double
     * @param tttimezone float
     * @param ttdst String
     * @param tttzdatabase String
     */
    public AirportTable(int ttid, String ttname, String ttcity, String ttcountry, String ttiata, String tticao,
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
     * @return atid
     */
    public int getAtid() {
        return atid.get();
    }

    /**
     * Retrieves the id of the airport from the parsed in data.
     * @return atid
     */
    public SimpleIntegerProperty atidProperty() {
        return atid;
    }

    /**
     * Sets the id value in the airport table
     * @param atid int
     */
    public void setAtid(int atid) {
        this.atid.set(atid);
    }

    /**
     * Gets the value of the airport name in the airport table
     * @return atname
     */
    public String getAtname() {
        return atname.get();
    }

    /**
     * Retrieves the airport name that has been parsed in by the data file.
     * @return atname
     */
    public SimpleStringProperty atnameProperty() {
        return atname;
    }

    /**
     * Sets the name of the airport in the airport table.
     * @param atname String
     */
    public void setAtname(String atname) {
        this.atname.set(atname);
    }

    /**
     * Gets the city of the airport in the airport table.
     * @return atcity String
     */
    public String getAtcity() {
        return atcity.get();
    }

    /**
     * Retrieves that value of the airport city as parsed in from the data file.
     * @return atcity
     */
    public SimpleStringProperty atcityProperty() {
        return atcity;
    }

    /**
     * Sets the value of the city in the airport table.
     * @param atcity String
     */
    public void setAtcity(String atcity) {
        this.atcity.set(atcity);
    }

    /**
     * Gets the value of the airport country in the airport table.
     * @return atcountry String
     */
    public String getAtcountry() {
        return atcountry.get();
    }

    /**
     * Retrieves the value of the country from the parsed data.
     * @return atcountry
     */
    public SimpleStringProperty atcountryProperty() {
        return atcountry;
    }

    /**
     * Sets the value of the country in the table.
     * @param atcountry String
     */
    public void setAtcountry(String atcountry) {
        this.atcountry.set(atcountry);
    }

    /**
     * Gets the airport IATA value for the airport table.
     * @return atiata String
     */
    public String getAtiata() {
        return atiata.get();
    }

    /**
     * Retrieves the airport IATA value from the parsed data.
     * @return atiata
     */
    public SimpleStringProperty atiataProperty() {
        return atiata;
    }

    /**
     * Sets the IATA number in the airport table.
     * @param atiata String
     */
    public void setAtiata(String atiata) {
        this.atiata.set(atiata);
    }

    /**
     * A getter for the ICAO number of the airport in the airport table.
     * @return aticao String
     */
    public String getAticao() {
        return aticao.get();
    }

    /**
     * Retrieves the airports IATA number from the parsed data.
     * @return aticao
     */
    public SimpleStringProperty aticaoProperty() {
        return aticao;
    }

    /**
     * Sets the airport IATA number in the airport table.
     * @param aticao String
     */
    public void setAticao(String aticao) {
        this.aticao.set(aticao);
    }

    /**
     * A getter for the airport latitude in the airport table.
     * @return atlatitude double
     */
    public double getAtlatitude() {
        return atlatitude.get();
    }

    /**
     * Retrieves the airport latitude from the parsed data.
     * @return atlatitude
     */
    public SimpleDoubleProperty atlatitudeProperty() {
        return atlatitude;
    }

    /**
     * Sets the airport latitude in the airport table.
     * @param atlatitude double
     */
    public void setAtlatitude(double atlatitude) {
        this.atlatitude.set(atlatitude);
    }

    /**
     * A getter for the airport longitude in the airport table.
     * @return atlongitude double
     */
    public double getAtlongitude() {
        return atlongitude.get();
    }

    /**
     * Retrieves the longitude of the airport from the parsed data.
     * @return atlongitude
     */
    public SimpleDoubleProperty atlongitudeProperty() {
        return atlongitude;
    }

    /**
     * Sets the longitude of the airport in the airport table.
     * @param atlongitude double
     */
    public void setAtlongitude(double atlongitude) {
        this.atlongitude.set(atlongitude);
    }

    /**
     * A getter for the altitude of the airport in the airport table
     * @return ataltitude double
     */
    public double getAtaltitude() {
        return ataltitude.get();
    }

    /**
     * Retrieves the altitude of the airport from the parsed data.
     * @return ataltitude
     */
    public SimpleDoubleProperty ataltitudeProperty() {
        return ataltitude;
    }

    /**
     * Sets the altitude of the airport in the airport table.
     * @param ataltitude double
     */
    public void setAtaltitude(double ataltitude) {
        this.ataltitude.set(ataltitude);
    }

    /**
     * A getter for the airport timezone in the airport table.
     * @return attimezone float
     */
    public float getAttimezone() {
        return attimezone.get();
    }

    /**
     * Retrieves the airport timezone form the parsed data.
     * @return attimezone
     */
    public SimpleFloatProperty attimezoneProperty() {
        return attimezone;
    }

    /**
     * Sets the airport timezone in the airport table.
     * @param attimezone float
     */
    public void setAttimezone(float attimezone) {
        this.attimezone.set(attimezone);
    }

    /**
     * A getter for the airport daylight saving time.
     * @return atdst String
     */
    public String getAtdst() {
        return atdst.get();
    }

    /**
     * Retrieves the daylight saving time from the parsed data.
     * @return atdst
     */
    public SimpleStringProperty atdstProperty() {
        return atdst;
    }

    /**
     * Sets the daylight saving time in the airport table.
     * @param atdst String
     */
    public void setAtdst(String atdst) {
        this.atdst.set(atdst);
    }

    /**
     * A getter for the airport time zone database for the airport table.
     * @return attzdatabase String
     */
    public String getAttzdatabase() {
        return attzdatabase.get();
    }

    /**
     * Retrieves the airport timezone database from the parsed data.
     * @return attzdatabase
     */
    public SimpleStringProperty attzdatabaseProperty() {
        return attzdatabase;
    }

    /**
     * Sets the timezone database in the airport talle.
     * @param attzdatabase String
     */
    public void setAttzdatabase(String attzdatabase) {
        this.attzdatabase.set(attzdatabase);
    }
}
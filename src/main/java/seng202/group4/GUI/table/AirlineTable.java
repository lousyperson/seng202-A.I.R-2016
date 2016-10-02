package seng202.group4.GUI.table;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Used to make a row of information to populate the airline table based upon given data.
 */
public class AirlineTable {

    private SimpleIntegerProperty rid;
    private SimpleStringProperty rname;
    private SimpleStringProperty ralias;
    private SimpleStringProperty riata;
    private SimpleStringProperty ricao;
    private SimpleStringProperty rcallsign;
    private StringProperty rcountry;
    private SimpleBooleanProperty ractive;

    /**
     * The initializer for the airline table.
     * @param tid int
     * @param tname String
     * @param talias String
     * @param tiata String
     * @param ticao String
     * @param tcallsign String
     * @param tcountry String
     * @param tactive boolean
     */
    public AirlineTable(int tid, String tname, String talias, String tiata, String ticao, String tcallsign, String tcountry, boolean tactive){
        this.rid = new SimpleIntegerProperty(tid);
        this.rname = new SimpleStringProperty(tname);
        this.ralias = new SimpleStringProperty(talias);
        this.riata = new SimpleStringProperty(tiata);
        this.ricao = new SimpleStringProperty(ticao);
        this.rcallsign = new SimpleStringProperty(tcallsign);
        this.rcountry = new SimpleStringProperty(tcountry);
        this.ractive = new SimpleBooleanProperty(tactive);

    }

    /**
     * A getter for the airline ID number value in the table.
     * @return int
     */
    public int getRid() {
        return rid.get();
    }

    /**
     * Gets the id value that was parsed in. When called, returns that value.
     * @return rid
     */
    public SimpleIntegerProperty ridProperty() {
        return rid;
    }

    /**
     * A setter for the airline id.
     * @param rid as an integer
     */
    public void setRid(int rid) {
        this.rid.set(rid);
    }

    /**
     * A getter for the airline name value in the table.
     * @return String
     */
    public String getRname() {
        return rname.get();
    }

    /**
     * Retrieves the name property of the airline from the parsed data.
     * @return rname
     */
    public SimpleStringProperty rnameProperty() {
        return rname;
    }

    /**
     * A setter for the airline name value in the table.
     * @param rname String
     */
    public void setRname(String rname) {
        this.rname.set(rname);
    }

    /**
     * A getter for the airline alias value in the table.
     * @return ralias as a string
     */
    public String getRalias() {
        return ralias.get();
    }

    /**
     * Retrieves the airline alias from the data parsed in.
     * @return ralias
     */
    public SimpleStringProperty raliasProperty() {
        return ralias;
    }

    /**
     * Sets the airline alias value in the table.
     * @param ralias String
     */
    public void setRalias(String ralias) {
        this.ralias.set(ralias);
    }

    /**
     * A getter for the airline IATA number value in the table.
     * @return riata String
     */
    public String getRiata() {
        return riata.get();
    }

    /**
     * Retrieves the airline IATA number from the parsed in data.
     * @return riata
     */
    public SimpleStringProperty riataProperty() {
        return riata;
    }

    /**
     * A setter for the airline IATA number value in the table.
     * @param riata String
     */
    public void setRiata(String riata) {
        this.riata.set(riata);
    }

    /**
     * A getter for the airline ICAO number value in the table.
     * @return ricao String
     */
    public String getRicao() {
        return ricao.get();
    }

    /**
     * Retrieves the ICAO property of the airline parsed in by the data.
     * @return ricao
     */
    public SimpleStringProperty ricaoProperty() {
        return ricao;
    }

    /**
     * A setter for the airline ICAO number in the table.
     * @param ricao String
     */
    public void setRicao(String ricao) {
        this.ricao.set(ricao);
    }

    /**
     * A getter for the airline callsign attribute in the table.
     * @return rcallsign String
     */
    public String getRcallsign() {
        return rcallsign.get();
    }

    /**
     * Retrieves the callsign string parsed in by the data.
     * @return rcallsign
     */
    public SimpleStringProperty rcallsignProperty() {
        return rcallsign;
    }

    /**
     * Sets the callsign string in the table.
     * @param rcallsign String
     */
    public void setRcallsign(String rcallsign) {
        this.rcallsign.set(rcallsign);
    }

    /**
     * A getter for the country string in the table.
     * @return rcountry String
     */
    public String getRcountry() {
        return rcountry.get();
    }

    /**
     * Retrieves the country name parsed in by the data.
     * @return rcountry
     */
    public StringProperty rcountryProperty() {
        return rcountry;
    }

    /**
     * Sets the country name in the table.
     * @param rcountry String
     */
    public void setRcountry(String rcountry) {
        this.rcountry.set(rcountry);
    }

    /**
     * Gets the boolean value for the airline activity in the table.
     * @return ractive boolean
     */
    public boolean getRactive() {
        return ractive.get();
    }

    /**
     * Retrieves the information on the airline activity from the parsed in data.
     * @return ractive
     */
    public SimpleBooleanProperty ractiveProperty() {
        return ractive;
    }

    /**
     * Sets the boolean active value in the table.
     * @param ractive boolean
     */
    public void setRactive(boolean ractive) {
        this.ractive.set(ractive);
    }

}
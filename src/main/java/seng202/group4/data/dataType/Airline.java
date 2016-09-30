package seng202.group4.data.dataType;

import java.io.Serializable;

/**
 * Stores information about an Airline.
 * Airline class creates an airline object that is represented in the airline table.
 */
public class Airline implements Serializable {
    private int ID;
    private String name;
    private String alias;
    private String IATA;
    private String ICAO;
    private String callsign;
    private String country;


    private boolean active; // Can be active or not active

    /**
     * Initializes all the values for the variables of the airline class.
     * @param ID An Integer for the airline ID
     * @param name A String for the airline name
     * @param alias A String for the airline alias
     * @param IATA A String for the airline IATA
     * @param ICAO A String for the airline ICAO
     * @param callsign A String for the airline callsign
     * @param country A String for the airline country
     * @param active A String for the airline active
     */
    public Airline(int ID, String name, String alias, String IATA, String ICAO, String callsign, String country, boolean active) {
        this.ID = ID;
        this.name = name;
        this.alias = alias;
        this.IATA = IATA;
        this.ICAO = ICAO;
        this.callsign = callsign;
        this.country = country;
        this.active = active;
    }


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getIATA() {
        return IATA;
    }

    public void setIATA(String IATA) {
        this.IATA = IATA;
    }

    public String getICAO() {
        return ICAO;
    }

    public void setICAO(String ICAO) {
        this.ICAO = ICAO;
    }

    public String getCallsign() {
        return callsign;
    }

    public void setCallsign(String callsign) {
        this.callsign = callsign;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
       this.active = active;
    }
}

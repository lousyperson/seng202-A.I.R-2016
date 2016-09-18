package seng202.group4.data.dataType;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * Stores information about a route.
 * The route class creates the route object using the information for the route and allows for the construction of the
 * route table.
 */
public class Route implements Serializable {
    private String airline;
    private Integer AirlineID;
    private String srcAirport;
    private Integer srcAirportID;
    private String destAirport;
    private Integer destAirportID;
    private String codeshare;
    private Integer stops;
    private double distance;    // Will be used in phase 3
    private ArrayList<String> equipment = new ArrayList<String>();

    /**
     * Initializes the variables for the route class.
     * @param airline A String for the route's airline
     * @param airlineID An Integer for the route's airline ID
     * @param srcAirport A String for the route's source airport
     * @param srcAirportID An Integer for the route's source airport ID
     * @param destAirport A String for the route's destination airport
     * @param destAirportID An Integer for the route's destination airport ID
     * @param codeshare A String for the route's codeshare
     * @param stops An Integer for the number of stops
     * @param equipment An ArrayList containing the route's equipment(s)
     */
    public Route(String airline, Integer airlineID, String srcAirport, Integer srcAirportID, String destAirport,
                 Integer destAirportID, String codeshare, Integer stops, ArrayList<String> equipment) {
        this.airline = airline;
        this.AirlineID = airlineID;
        this.srcAirport = srcAirport;
        this.srcAirportID = srcAirportID;
        this.destAirport = destAirport;
        this.destAirportID = destAirportID;
        this.codeshare = codeshare;
        this.stops = stops;
        this.equipment = equipment;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public Integer getAirlineID() {
        return AirlineID;
    }

    public void setAirlineID(Integer airlineID) {
        AirlineID = airlineID;
    }

    public String getSrcAirport() {
        return srcAirport;
    }

    public void setSrcAirport(String srcAirport) {
        this.srcAirport = srcAirport;
    }

    public Integer getSrcAirportID() {
        return srcAirportID;
    }

    public void setSrcAirportID(Integer srcAirportID) {
        this.srcAirportID = srcAirportID;
    }

    public String getDestAirport() {
        return destAirport;
    }

    public void setDestAirport(String destAirport) {
        this.destAirport = destAirport;
    }

    public Integer getDestAirportID() {
        return destAirportID;
    }

    public void setDestAirportID(Integer destAirportID) {
        this.destAirportID = destAirportID;
    }

    public String getCodeshare() {
        return codeshare;
    }

    public void setCodeshare(String codeshare) {
        this.codeshare = codeshare;
    }

    public Integer getStops() {
        return stops;
    }

    public void setStops(Integer stops) {
        this.stops = stops;
    }

    public ArrayList<String> getEquipment() {
        return equipment;
    }

    public void setEquipment(ArrayList<String> equipment) {
        this.equipment = equipment;
    }
}

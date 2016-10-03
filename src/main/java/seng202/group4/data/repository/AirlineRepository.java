package seng202.group4.data.repository;

import seng202.group4.data.dataType.Airline;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Stores all the Airlines in exactly one HashMap and has methods that Airlines will use.
 */
public class AirlineRepository extends Repository implements Serializable {

    private HashMap<Integer, Airline> airlines = new HashMap<Integer, Airline>();

    /**
     * Adds an airline to the list of airlines.
     *
     * @param airline Airline
     */
    public void addAirline(Airline airline) {
        airlines.put(airline.getID(), airline);
    }

    /**
     * Returns a list of airlines.
     *
     * @return airlines
     */
    public HashMap<Integer, Airline> getAirlines() {
        return airlines;
    }


    /**
     * Given a country, finds all airlines based in that country.
     *
     * @param country String
     * @return AirlineCountry
     */
    //given a country, finds all airlines based in that country
    public ArrayList<Airline> getCountry(String country) {
        ArrayList<Airline> AirlineCountry = new ArrayList<Airline>();
        for (Airline airline : airlines.values()) {
            if (airline.getCountry().equals(country)) {
                AirlineCountry.add(airline);
            }
        }
        return AirlineCountry;
    }

    /**
     * Finds all operating airlines.
     *
     * @return ActiveAirlines
     */
    public ArrayList<Airline> getActive() {
        ArrayList<Airline> ActiveAirlines = new ArrayList<Airline>();
        for (Airline airline : airlines.values()) {
            if (airline.getActive() == true) {
                ActiveAirlines.add(airline);
            }
        }
        return ActiveAirlines;
    }

    /**
     * Finds all inactive airlines.
     *
     * @return InActiveAirlines
     */
    public ArrayList<Airline> getInActive() {
        ArrayList<Airline> InActiveAirlines = new ArrayList<Airline>();
        for (Airline airline : airlines.values()) {
            if (airline.getActive() == false) {
                InActiveAirlines.add(airline);
            }
        }
        return InActiveAirlines;
    }
}
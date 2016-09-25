package seng202.group4.data.dataType;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Stores a Flight which is made up of an array list of flight positions.
 * The flightPosition class exists to allow for building of flights to be represented in the GUI as a table.
 */
public class Flight implements Serializable {
    private ArrayList<FlightPosition> positions;

    public Flight(ArrayList<FlightPosition> positions) {
        this.positions = positions;
    }

    public ArrayList<FlightPosition> getFlightPositions() {
        return positions;
    }

    public void setFlightPositions(ArrayList<FlightPosition> positions) {
        this.positions = positions;
    }
}



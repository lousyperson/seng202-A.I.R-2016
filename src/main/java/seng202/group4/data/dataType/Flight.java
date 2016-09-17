package seng202.group4.data.dataType;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * * The flightPosition class exists to allow for building of flights to be represented in the GUI as a table.
 * Created by jjg64 on 15/08/16.
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



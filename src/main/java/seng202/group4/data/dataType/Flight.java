package seng202.group4.data.dataType;

import java.io.Serializable;
import java.util.ArrayList;

/**
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



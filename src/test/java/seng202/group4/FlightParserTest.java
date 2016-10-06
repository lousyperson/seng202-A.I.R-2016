package seng202.group4;

import org.junit.Test;
import seng202.group4.data.dataType.Flight;
import seng202.group4.data.dataType.FlightPosition;
import seng202.group4.data.parser.FlightParser;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * Tests the FlightParser class with valid Flight files.
 */
public class FlightParserTest extends ParserTest {
    private String path = "testfiles/Flights/";

    /**
     * Gets the string of the flight position that is used for testing.
     */
    private String getString(FlightPosition position) {
        String s = "";
        s += position.getType() + "," + position.getID() + "," + position.getAltitude() + "," +
                position.getLatitude() + "," + position.getLongitude();
        return s;
    }

    /**
     * Check that an empty file is parsed and does not make any flights.
     *
     * @throws IOException throws IOException error
     */
    @Test
    public void emptyFileFlight() throws IOException {
        FlightParser parser = new FlightParser(fileArray("testfiles/empty.csv"));
        Flight flight = parser.makeFlight();
        int size = flight.getFlightPositions().size();
        assertTrue(size == 0);
    }

    /**
     * Check that each row from a valid flight file is parsed by checking if the 1 flight postions is stored.
     *
     * @throws IOException throws IOException error
     */
    @Test
    public void oneFlightPositionSize() throws IOException {
        FlightParser parser = new FlightParser(fileArray(path + "oneFlightPosition.csv"));
        Flight flight = parser.makeFlight();
        int size = flight.getFlightPositions().size();
        assertTrue(size == 1);
    }

    /**
     * Check that each row from a valid flight file is parsed by checking if all 2 flight postions are stored.
     *
     * @throws IOException throws IOException error
     */
    @Test
    public void twoFlightPositionsSize() throws IOException {
        FlightParser parser = new FlightParser(fileArray(path + "twoFlightPositions.csv"));
        Flight flight = parser.makeFlight();
        int size = flight.getFlightPositions().size();
        assertTrue(size == 2);
    }

    /**
     * Check that each row from a valid flight file is parsed by checking if all 3 flight postions are stored.
     *
     * @throws IOException throws IOException error
     */
    @Test
    public void threeFlightPositionsSize() throws IOException {
        FlightParser parser = new FlightParser(fileArray(path + "threeFlightPositions.csv"));
        Flight flight = parser.makeFlight();
        int size = flight.getFlightPositions().size();
        assertTrue(size == 3);
    }

    /**
     * Check that each row from a valid flight file is parsed by checking if all 31 flight postions are stored.
     *
     * @throws IOException throws IOException error
     */
    @Test
    public void validFlight31Size() throws IOException {
        FlightParser parser = new FlightParser(fileArray(path + "31FlightPositions.csv"));
        Flight flight = parser.makeFlight();
        int size = flight.getFlightPositions().size();
        assertTrue(size == 31);
    }


    /**
     * Check that the parser can read one line correctly.
     *
     * @throws IOException throws IOException error
     */
    @Test
    public void oneFlightPositionContents() throws IOException {
        FlightParser parser = new FlightParser(fileArray(path + "oneFlightPosition.csv"));
        Flight flight = parser.makeFlight();
        ArrayList<FlightPosition> positions = flight.getFlightPositions();
        String position0 = getString(positions.get(0));
        assertTrue(position0.equals("APT,NZCH,0.0,-43.48664019,172.53368221"));
    }

    /**
     * Checks if the parser can read a consecutive line.
     *
     * @throws IOException throws IOException error
     */
    @Test
    public void twoFlightPositionsContents() throws IOException {
        FlightParser parser = new FlightParser(fileArray(path + "twoFlightPositions.csv"));
        Flight flight = parser.makeFlight();
        ArrayList<FlightPosition> positions = flight.getFlightPositions();
        String position0 = getString(positions.get(0));
        String position1 = getString(positions.get(1));
        assertTrue(position0.equals("APT,NZCH,0.0,-43.48664019,172.53368221") &&
                position1.equals("APT,WSSS,0.0,1.3519171399999976,103.99560303999999"));
    }

    /**
     * Checks if the parser can read multiple lines.
     *
     * @throws IOException throws IOException error
     */
    @Test
    public void threeFlightPositionsContents() throws IOException {
        FlightParser parser = new FlightParser(fileArray(path + "threeFlightPositions.csv"));
        Flight flight = parser.makeFlight();
        ArrayList<FlightPosition> positions = flight.getFlightPositions();
        String position0 = getString(positions.get(0));
        String position1 = getString(positions.get(1));
        String position2 = getString(positions.get(2));
        assertTrue(position0.equals("APT,KPMD,0.0,34.62938675000001,-118.0845505") &&
                position1.equals("FIX,MUBIT,34000.0,3.5,164.66833299999996") &&
                position2.equals("APT,YUPG,0.0,-14.705201990000006,134.56256028999996"));
    }

}

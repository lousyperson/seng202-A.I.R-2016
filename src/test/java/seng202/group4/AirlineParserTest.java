package seng202.group4;

import org.junit.Test;
import seng202.group4.data.dataType.Airline;
import seng202.group4.data.parser.AirlineParser;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

/**
 * Tests the AirlineParser class with valid Airline files.
 */
public class AirlineParserTest extends ParserTest {
    String path = "testfiles/Airlines/";

    /**
     * Gets the string of the flight position that is used for testing.
     */
    private String getString(Airline airline) {
        String s = "";
        s += airline.getID() + "," + airline.getName() + "," + airline.getAlias() + "," +
                airline.getIATA() + "," + airline.getICAO() + "," + airline.getCallsign() + "," +
                airline.getCountry() + ","  + airline.getActive();
        return s;
    }

    /**
     * Check that if the airline parser parses an empty file, 0 airlines are parsed.
     *
     * @throws IOException throws IOException error
     */
    @Test
    public void emptyFileAirline() throws IOException {
        AirlineParser parser = new AirlineParser(fileArray("testfiles/empty.txt"));
        ArrayList<Airline> airlines = parser.makeAirlines();
        int size = airlines.size();
        assertTrue(size == 0);
    }

    /**
     * Given one valid airline, check that the size is 1.
     *
     * @throws IOException throws IOException error
     */
    @Test
    public void oneValidAirlineSize() throws IOException {
        AirlineParser parser = new AirlineParser(fileArray(path + "oneValidAirline.txt"));
        ArrayList<Airline> airlines = parser.makeAirlines();
        assertTrue(airlines.size() == 1);
    }

    /**
     * Given two valid airlines, check that the size is 2.
     *
     * @throws IOException throws IOException error
     */
    @Test
    public void twoValidAirlineSize() throws IOException {
        AirlineParser parser = new AirlineParser(fileArray(path + "twoValidAirlines.txt"));
        ArrayList<Airline> airlines = parser.makeAirlines();
        assertTrue(airlines.size() == 2);
    }

    /**
     * Given three valid airlines, check that the size is 3.
     *
     * @throws IOException throws IOException error
     */
    @Test
    public void threeValidAirlineSize() throws IOException {
        AirlineParser parser = new AirlineParser(fileArray(path + "threeValidAirlines.txt"));
        ArrayList<Airline> airlines = parser.makeAirlines();
        assertTrue(airlines.size() == 3);
    }

    /**
     * Check that a valid airline with null is read by the parser.
     *
     * @throws IOException throws IOException error
     */
    @Test
    public void oneValidAirlineWithNullSize() throws IOException {
        AirlineParser parser = new AirlineParser(fileArray(path + "oneValidAirlineWithNull.txt"));
        ArrayList<Airline> airlines = parser.makeAirlines();
        assertTrue(airlines.size() == 1);
    }

    /**
     * Check that every airline in the 6048Airlines.txt has been read by the parser, check the size is 6048.
     *
     * @throws IOException throws IOException error
     */
    @Test
    public void Airlines6048Size() throws IOException {
        AirlineParser parser = new AirlineParser(fileArray(path + "6048Airlines.txt"));
        ArrayList<Airline> airlines = parser.makeAirlines();
        assertTrue(airlines.size() == 6048);

    }

    /**
     * Given one valid airline, check that the content has been parsed correctly.
     *
     * @throws IOException throws IOException error
     */
    @Test
    public void oneValidAirlineContents() throws IOException {
        AirlineParser parser = new AirlineParser(fileArray(path + "oneValidAirline.txt"));
        ArrayList<Airline> airlines = parser.makeAirlines();
        String airline0 = getString(airlines.get(0));
        assertTrue(airline0.equals("324,All Nippon Airways,ANA All Nippon Airways,NH,ANA,ALL NIPPON,Japan,true"));
    }

    /**
     * Given one valid airline, check that the content has been parsed correctly.
     *
     * @throws IOException throws IOException error
     */
    @Test
    public void twoValidAirlineContents() throws IOException {
        AirlineParser parser = new AirlineParser(fileArray(path + "twoValidAirlines.txt"));
        ArrayList<Airline> airlines = parser.makeAirlines();
        String airline0 = getString(airlines.get(0));
        String airline1 = getString(airlines.get(1));
        assertTrue(airline0.equals("334,Alajnihah for Air Transport,null,,ANH,ALAJNIHAH,Libya,false") &&
                airline1.equals("335,Air Atlantic (Nig) Limited,null,,ANI,NIGALANTIC,Nigeria,false"));
    }

    /**
     * Given one valid airline, check that the content has been parsed correctly.
     *
     * @throws IOException throws IOException error
     */
    @Test
    public void threeValidAirlineContents() throws IOException {
        AirlineParser parser = new AirlineParser(fileArray(path + "threeValidAirlines.txt"));
        ArrayList<Airline> airlines = parser.makeAirlines();
        String airline0 = getString(airlines.get(0));
        String airline1 = getString(airlines.get(1));
        String airline2 = getString(airlines.get(2));
        assertTrue(airline0.equals("86,Airdeal Oy,null,,ADU,AIRDEAL,Finland,false") &&
                airline1.equals("87,Advance Air Charters,null,,ADV,ADVANCE,Canada,false") &&
                airline2.equals("88,Air Andaman,null,,ADW,AIR ANDAMAN,Thailand,false"));
    }

    /**
     * Given one valid airline, check that the content has been parsed correctly.
     *
     * @throws IOException throws IOException error
     */
    @Test
    public void oneValidAirlineWithNullContents() throws IOException {
        AirlineParser parser = new AirlineParser(fileArray(path + "oneValidAirlineWithNull.txt"));
        ArrayList<Airline> airlines = parser.makeAirlines();
        String airline0 = getString(airlines.get(0));
        System.out.println(airline0);
        assertTrue(airline0.equals("412,Aerolineas Argentinas,null,AR,ARG,ARGENTINA,Argentina,true"));
    }
}

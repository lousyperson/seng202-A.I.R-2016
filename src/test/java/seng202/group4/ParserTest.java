package seng202.group4;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Test cases for each parser
 */
public class ParserTest extends TestCase{

    @Test
    public void validFlight() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("../../testfiles/Flights/validFlight.csv"));
        } catch (Exception e) {
        }

    }
}

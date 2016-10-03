package seng202.group4;

import org.junit.Before;
import org.junit.Test;
import seng202.group4.data.dataType.Airport;
import seng202.group4.data.dataType.Route;
import seng202.group4.data.repository.Repository;

/**
 * Tests the serialize class to ensure it works appropriately.
 */
public class SerializeTest {
    /**
     * Initialise the repository by deserialise.
     */
    @Before
    public void set() {
        Repository.initRepository();
    }

    /**
     * Prints the airport data list to check the deserialise works properly.
     */
    @Test
    public void defaultAirport() {
        for (Airport airport : Repository.airportRepository.getAirports().values()) {
            System.out.println(airport.getName());
        }
    }
}

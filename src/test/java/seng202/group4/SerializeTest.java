package seng202.group4;

import org.junit.Before;
import org.junit.Test;
import seng202.group4.data.dataType.Airport;
import seng202.group4.data.dataType.Route;
import seng202.group4.data.repository.Repository;

/**
 *
 */
public class SerializeTest {
    @Before
    public void set() {
        Repository.initRepository();
    }

    @Test
    public void defaultAirport() {
        for (Airport airport : Repository.airportRepository.getAirports().values()) {
            System.out.println(airport.getName());
        }
    }
}

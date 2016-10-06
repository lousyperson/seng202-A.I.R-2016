package seng202.group4;

import org.junit.Test;
import seng202.group4.data.dataType.Route;
import seng202.group4.data.parser.RouteParser;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

/**
 * Tests the RouteParser class with valid Route files.
 */
public class RouteParserTest extends ParserTest {
    private String path = "testfiles/Routes/";

    /**
     * Gets the string of the route that is used for testing.
     */
    private String getString(Route route) {
        int i = 0;
        String s = "";
        s += route.getAirline() + "," + route.getAirlineID() + "," + route.getSrcAirport() + "," +
                route.getSrcAirportID() + "," + route.getDestAirport() + "," + route.getDestAirportID() +
                "," + route.getCodeshare() + "," + route.getStops() + ",";
        for (; i < route.getEquipment().size() - 1; i++) {
            s += route.getEquipment().get(i) + " ";
        }
        s += route.getEquipment().get(i);
        return s;
    }

    /**
     * Check that an empty text file does not make any routes when parsed.
     *
     * @throws IOException throws IOException error
     */
    @Test
    public void emptyFileRoute() throws IOException {
        RouteParser parser = new RouteParser(fileArray("testfiles/empty.txt"));
        ArrayList<Route> routes = parser.makeRoutes();
        int size = routes.size();
        assertTrue(size == 0);
    }

    /**
     * Checks that one valid route can be parsed.
     *
     * @throws IOException throws IOException error
     */
    @Test
    public void oneValidRouteSize() throws IOException {
        RouteParser parser = new RouteParser(fileArray(path + "oneValidRoute.txt"));
        ArrayList<Route> routes = parser.makeRoutes();
        int size = routes.size();
        assertTrue(size == 1);
    }

    /**
     * Checks that two valid routes can be parsed.
     *
     * @throws IOException throws IOException error
     */
    @Test
    public void twoValidRouteSize() throws IOException {
        RouteParser parser = new RouteParser(fileArray(path + "twoValidRoutes.txt"));
        ArrayList<Route> routes = parser.makeRoutes();
        int size = routes.size();
        assertTrue(size == 2);
    }

    /**
     * Checks that three valid routes can be parsed.
     *
     * @throws IOException throws IOException error
     */
    @Test
    public void threeValidRouteSize() throws IOException {
        RouteParser parser = new RouteParser(fileArray(path + "threeValidRoutes.txt"));
        ArrayList<Route> routes = parser.makeRoutes();
        int size = routes.size();
        assertTrue(size == 3);
    }

    /**
     * Checks that one valid route with null can be parsed.
     *
     * @throws IOException throws IOException error
     */
    @Test
    public void oneValidRouteWithNullSize() throws IOException {
        RouteParser parser = new RouteParser(fileArray(path + "oneValidRouteWithNull.txt"));
        ArrayList<Route> routes = parser.makeRoutes();
        int size = routes.size();
        assertTrue(size == 1);
    }

    /**
     * Check that all routes in a valid route file are parsed by seeing if all 67663 routes are there.
     *
     * @throws IOException throws IOException error
     */
    @Test
    public void Routes67663Size() throws IOException {
        RouteParser parser = new RouteParser(fileArray(path + "67663Routes.txt"));
        ArrayList<Route> routes = parser.makeRoutes();
        int size = routes.size();
        assertTrue(size == 67663);
    }

    /**
     * Checks that one valid route with multiple equipments can be parsed.
     *
     * @throws IOException throws IOException error
     */
    @Test
    public void oneValidRouteWithMultiEquipmentSize() throws IOException {
        RouteParser parser = new RouteParser(fileArray(path + "oneValidRouteWithMultiEquipment.txt"));
        ArrayList<Route> routes = parser.makeRoutes();
        int size = routes.size();
        assertTrue(size == 1);
    }

    /**
     * Checks that one valid route with three equipments are parsed.
     *
     * @throws IOException throws IOException error
     */
    @Test
    public void oneValidRouteWithMultiEquipmentCheckEquipmentSize() throws IOException {
        RouteParser parser = new RouteParser(fileArray(path + "oneValidRouteWithMultiEquipment.txt"));
        ArrayList<Route> routes = parser.makeRoutes();
        int size = routes.get(0).getEquipment().size();
        assertTrue(size == 3);
    }

    /**
     * Check that the contents of a valid route file are parsed correctly.
     *
     * @throws IOException throws IOException error
     */
    @Test
    public void oneValidRouteContents() throws IOException {
        RouteParser parser = new RouteParser(fileArray(path + "oneValidRoute.txt"));
        ArrayList<Route> routes = parser.makeRoutes();
        String route0 = getString(routes.get(0));
        assertTrue(route0.equals("2B,410,AER,2965,KZN,2990,,0,CR2"));
    }

    /**
     * Check that the contents of a valid route file are parsed correctly with a consecutive route.
     *
     * @throws IOException throws IOException error
     */
    @Test
    public void twoValidRouteContents() throws IOException {
        RouteParser parser = new RouteParser(fileArray(path + "twoValidRoutes.txt"));
        ArrayList<Route> routes = parser.makeRoutes();
        String route0 = getString(routes.get(0));
        String route1 = getString(routes.get(1));
        assertTrue(route0.equals("2B,410,DME,4029,TGK,null,,0,CR2") &&
                route1.equals("2I,8359,TPP,2806,IQT,2801,,0,142"));
    }

    /**
     * Check that the contents of a valid route file are parsed correctly with multiple routes.
     *
     * @throws IOException throws IOException error
     */
    @Test
    public void threeValidRouteContents() throws IOException {
        RouteParser parser = new RouteParser(fileArray(path + "threeValidRoutes.txt"));
        ArrayList<Route> routes = parser.makeRoutes();
        String route0 = getString(routes.get(0));
        String route1 = getString(routes.get(1));
        String route2 = getString(routes.get(2));
        assertTrue(route0.equals("2K,1338,UIO,2688,OCC,2670,,0,319") &&
                route1.equals("2K,1338,GYE,2673,SCY,6045,,0,319 320") &&
                route2.equals("2P,897,LGP,4203,MNL,2397,,0,320"));
    }

    /**
     * Checks that one valid route with null can be parsed, and the contents are correct.
     *
     * @throws IOException throws IOException error
     */
    @Test
    public void oneValidRouteWithNullContents() throws IOException {
        RouteParser parser = new RouteParser(fileArray(path + "oneValidRouteWithNull.txt"));
        ArrayList<Route> routes = parser.makeRoutes();
        String route0 = getString(routes.get(0));
        assertTrue(route0.equals("4W,null,SVS,null,FAI,3832,,0,PA2"));
    }

    /**
     * Check that a valid route with multiple equipments are parsed correctly with the correct contents.
     *
     * @throws IOException throws IOException error
     */
    @Test
    public void oneValidRouteWithMultiEquipmentContents() throws IOException {
        RouteParser parser = new RouteParser(fileArray(path + "oneValidRouteWithMultiEquipment.txt"));
        ArrayList<Route> routes = parser.makeRoutes();
        String route0 = getString(routes.get(0));
        assertTrue(route0.equals("2J,470,ABJ,253,OUA,246,,0,E70 CRJ M87"));
    }
}

package seng202.group4.data.dataType;

/**
 * Created by jjg64 on 15/08/16.
 */
public class Airport {
    private int ID;
    private String name;
    private String city;
    private String country;
    private String IATA; // FAA if the country is USA
    private String ICAO;
    private double latitude;
    private double longitude;
    private double altitude;
    private float timezone;
    private DaylightSavingsTime DST;
    private String tz;
    private int routes = 0;

    public Airport() {

    }
    public Airport(int ID, String name, String city, String country, String IATA, String ICAO, double latitude,
            double longitude, double altitude, float timezone, DaylightSavingsTime DST, String tz) {
        this.ID = ID;
        this.name = name;
        this.city = city;
        this.country = country;
        this.IATA = IATA;
        this.ICAO = ICAO;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.timezone = timezone;
        this.DST = DST;
        this.tz = tz;
    }


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getIATA() {
        return IATA;
    }

    public void setIATA(String IATA) {
        this.IATA = IATA;
    }

    public String getICAO() {
        return ICAO;
    }

    public void setICAO(String ICAO) {
        this.ICAO = ICAO;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public float getTimezone() {
        return timezone;
    }

    public void setTimezone(float timezone) {
        this.timezone = timezone;
    }

    public DaylightSavingsTime getDST() {
        return DST;
    }

    public void setDST(DaylightSavingsTime DST) {
        this.DST = DST;
    }

    public String getTz() {
        return tz;
    }

    public void setTz(String tz) {
        this.tz = tz;
    }
}

package seng202.group4.data.dataType;

/**
 * An enum for Daylight Savings Time to allow for easy access to the timing in different countries and areas.
 *
 */
public enum DaylightSavingsTime {
    E ("Europe"),  // Europe
    A ("US / Canada"),  // US/Canada
    S ("South America"),  // South America
    O ("Australia"),  // Australia
    Z ("New Zealand"),  // New Zealand
    N ("None"),  // None
    U ("Unknown");   // Unknown

    private final String zone;

    private DaylightSavingsTime(String zone) {
        this.zone = zone;
    }

    public boolean equalsZone(String otherZone) {
        return (otherZone == null) ? false : zone.equals(otherZone);
    }

    public String toText() {
        return this.zone;
    }
}


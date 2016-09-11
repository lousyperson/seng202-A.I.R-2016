package seng202.group4.data.dataType;

/**
 * Created by jjg64 on 18/08/16.
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

    public String toAString() {
        return this.zone;
    }
}


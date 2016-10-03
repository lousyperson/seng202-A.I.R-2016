package seng202.group4.data.dataType;

/**
 * An enum for Daylight Savings Time to allow for easy access to the timing in different countries and areas.
 */
public enum DaylightSavingsTime {
    E ("Europe"),
    A ("US / Canada"),
    S ("South America"),
    O ("Australia"),
    Z ("New Zealand"),
    N ("None"),
    U ("Unknown");

    private final String zone;

    private DaylightSavingsTime(String zone) {
        this.zone = zone;
    }

    /**
     * Gets the DayListSavingsTime String value from the DayListSavingsTime.
     *
     * @return The string corresponding the the DayLightSavingTime value.
     */
    public String toText() {
        return this.zone;
    }
}


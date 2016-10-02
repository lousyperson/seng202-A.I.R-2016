package seng202.group4.GUI.table;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Used to make a row of information to populate the analysis table based upon given data.
 */
public class AnalysisTable {
    private SimpleStringProperty column1;
    private SimpleIntegerProperty number;

    /**
     * The initializer for the analysis table.
     * @param column1 String
     * @param number Integer
     */
    public AnalysisTable(String column1, int number){
        this.column1 = new SimpleStringProperty(column1);
        this.number = new SimpleIntegerProperty(number);
    }

    /**
     * A getter for the column1 value in the table.
     * @return String
     */
    public String getColumn1() {
        return column1.get();
    }

    /**
     * Gets the column1 value that was parsed in. When called, returns that value.
     * @return column1
     */
    public SimpleStringProperty column1Property() {
        return column1;
    }

    /**
     * A setter for the column1.
     * @param column1 as an String
     */
    public void setColumn1(String column1) {
        this.column1.set(column1);
    }

    /**
     * A getter for the number value in the table.
     * @return int
     */
    public int getNumber() {
        return number.get();
    }

    /**
     * Gets the number value that was parsed in. When called, returns that value.
     * @return number
     */
    public SimpleIntegerProperty numberProperty() {
        return number;
    }

    /**
     * A setter for the number value.
     * @param number as an int
     */
    public void setNumber(int number) {
        this.number.set(number);
    }
}

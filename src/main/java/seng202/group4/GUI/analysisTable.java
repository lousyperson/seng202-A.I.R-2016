package seng202.group4.GUI;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by zyt10 on 25/09/16.
 */
public class analysisTable {
    private SimpleStringProperty airport;
    private SimpleIntegerProperty number;

    public analysisTable(String airport, Integer number){
        this.airport = new SimpleStringProperty(airport);
        this.number = new SimpleIntegerProperty(number);
    }

    public String getAirport() {
        return airport.get();
    }

    public SimpleStringProperty airportProperty() {
        return airport;
    }

    public void setAirport(String airport) {
        this.airport.set(airport);
    }

    public int getNumber() {
        return number.get();
    }

    public SimpleIntegerProperty numberProperty() {
        return number;
    }

    public void setNumber(int number) {
        this.number.set(number);
    }
}

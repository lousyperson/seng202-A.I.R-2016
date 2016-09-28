package seng202.group4.GUI;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by zyt10 on 25/09/16.
 */
public class AnalysisTable {
    private SimpleStringProperty column1;
    private SimpleIntegerProperty number;

    public AnalysisTable(String column1, Integer number){
        this.column1 = new SimpleStringProperty(column1);
        this.number = new SimpleIntegerProperty(number);
    }

    public String getColumn1() {
        return column1.get();
    }

    public SimpleStringProperty column1Property() {
        return column1;
    }

    public void setColumn1(String column1) {
        this.column1.set(column1);
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

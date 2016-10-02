package seng202.group4.GUI.table;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by zyt10 on 25/09/16.
 */
public class EquipAnalysisTable {
    private SimpleStringProperty equipment;
    private SimpleIntegerProperty number;

    public EquipAnalysisTable(String equipment, Integer number){
        this.equipment = new SimpleStringProperty(equipment);
        this.number = new SimpleIntegerProperty(number);
    }

    public String getEquipment() {
        return equipment.get();
    }

    public SimpleStringProperty equipmentProperty() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment.set(equipment);
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
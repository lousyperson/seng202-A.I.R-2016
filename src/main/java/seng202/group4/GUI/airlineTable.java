package seng202.group4.GUI;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by psu43 on 25/08/16.
 */
public class airlineTable {

    private SimpleIntegerProperty rid;
    private SimpleStringProperty rname;
    private SimpleStringProperty ralias;
    private SimpleStringProperty riata;
    private SimpleStringProperty ricao;
    private SimpleStringProperty rcallsign;
    private StringProperty rcountry;
    private SimpleBooleanProperty ractive;

    public airlineTable(int tid, String tname, String talias, String tiata, String ticao, String tcallsign, String tcountry, boolean tactive){
        this.rid = new SimpleIntegerProperty(tid);
        this.rname = new SimpleStringProperty(tname);
        this.ralias = new SimpleStringProperty(talias);
        this.riata = new SimpleStringProperty(tiata);
        this.ricao = new SimpleStringProperty(ticao);
        this.rcallsign = new SimpleStringProperty(tcallsign);
        this.rcountry = new SimpleStringProperty(tcountry);
        this.ractive = new SimpleBooleanProperty(tactive);

    }
    public int getRid() {
        return rid.get();
    }

    public SimpleIntegerProperty ridProperty() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid.set(rid);
    }

    public String getRname() {
        return rname.get();
    }

    public SimpleStringProperty rnameProperty() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname.set(rname);
    }

    public String getRalias() {
        return ralias.get();
    }

    public SimpleStringProperty raliasProperty() {
        return ralias;
    }

    public void setRalias(String ralias) {
        this.ralias.set(ralias);
    }

    public String getRiata() {
        return riata.get();
    }

    public SimpleStringProperty riataProperty() {
        return riata;
    }

    public void setRiata(String riata) {
        this.riata.set(riata);
    }

    public String getRicao() {
        return ricao.get();
    }

    public SimpleStringProperty ricaoProperty() {
        return ricao;
    }

    public void setRicao(String ricao) {
        this.ricao.set(ricao);
    }

    public String getRcallsign() {
        return rcallsign.get();
    }

    public SimpleStringProperty rcallsignProperty() {
        return rcallsign;
    }

    public void setRcallsign(String rcallsign) {
        this.rcallsign.set(rcallsign);
    }

    public String getRcountry() {
        return rcountry.get();
    }

    public StringProperty rcountryProperty() {
        return rcountry;
    }

    public void setRcountry(String rcountry) {
        this.rcountry.set(rcountry);
    }

    public boolean getRactive() {
        return ractive.get();
    }

    public SimpleBooleanProperty ractiveProperty() {
        return ractive;
    }

    public void setRactive(boolean ractive) {
        this.ractive.set(ractive);
    }








}

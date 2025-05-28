package model;

import java.util.ArrayList;
import java.util.List;

public class Poste {
    public String refPoste;
    public String dPoste;
    public List<Machine> listeMachine;

    public Poste(String refPoste, String dPoste, List<Machine> listeMachine) {
        this.refPoste = refPoste;
        this.dPoste = dPoste;
        this.listeMachine = new ArrayList<>(listeMachine); // copie défensive
    }

    public String getRefPoste() {
        return refPoste;
    }

    public void setRefPoste(String refPoste) {
        this.refPoste = refPoste;
    }

    public String getdPoste() {
        return dPoste;
    }

    public void setdPoste(String dPoste) {
        this.dPoste = dPoste;
    }

    public List<Machine> getListeMachine() {
        return listeMachine;
    }
}

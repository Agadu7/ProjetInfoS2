package model;

import java.util.List;

public class Equipement {
    public String refEquipement;
    public String dEquipement;
    public List<Machine> listeMachine;
    public List<Poste> listePostes;

    public List<Machine> getListeMachine() {
        return listeMachine;
    }

    public void setListeMachine(List<Machine> listeMachine) {
        this.listeMachine = listeMachine;
    }

    public List<Poste> getListePostes() {
        return listePostes;
    }

    public void setListePostes(List<Poste> listePostes) {
        this.listePostes = listePostes;
    }

    public Equipement(String refEquipement, String dEquipement, List<Machine> listeMachine, List<Poste> listePostes) {
        this.refEquipement = refEquipement;
        this.dEquipement=dEquipement;
        this.listeMachine = listeMachine;
        this.listePostes = listePostes;
    }

    public String getRefEquipement() {
        return refEquipement;
    }
    public void setRefEquipement(String refEquipement) {
        this.refEquipement = refEquipement;
    }
    public String getdEquipement() {
        return dEquipement;
    }
    public void setdEquipement(String dEquipement) {
        this.dEquipement = dEquipement;
    }

    
}

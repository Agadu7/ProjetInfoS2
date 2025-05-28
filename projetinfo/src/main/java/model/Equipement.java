package model;

import java.util.ArrayList;

public class Equipement {
    public String refEquipement;
    public String dEquipement;
    public ArrayList<Machine> listeMachine;
    public ArrayList<Poste> listePostes;

    public ArrayList<Machine> getListeMachine() {
        return listeMachine;
    }

    public void setListeMachine(ArrayList<Machine> listeMachine) {
        this.listeMachine = listeMachine;
    }

    public ArrayList<Poste> getListePostes() {
        return listePostes;
    }

    public void setListePostes(ArrayList<Poste> listePostes) {
        this.listePostes = listePostes;
    }

    public Equipement(String refEquipement, String dEquipement, ArrayList<Machine> listeMachine, ArrayList<Poste> listePostes) {
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

package model;

public class Equipement {
    public String refEquipement;
    public String dEquipement;

    public Equipement(String refEquipement, String dEquipement) {
        this.refEquipement = refEquipement;
        this.dEquipement=dEquipement;
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

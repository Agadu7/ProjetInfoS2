import java.util.ArrayList;

public class Gamme {
    private ArrayList<Machine> listeMachine;
    private String refGamme;
    private ArrayList<Operation> listeOperation;
    private ArrayList<Equipement> listeEquipement;

    public Gamme(ArrayList<Machine> listeMachine, String refGamme, ArrayList<Operation> listeOperation,ArrayList<Equipement> listeEquipement) {
        this.listeMachine = listeMachine;
        this.refGamme=refGamme;
        this.listeOperation = listeOperation;
        this.listeEquipement=listeEquipement;
    }
    
    public ArrayList<Machine> getListeMachine() {
        return listeMachine;
    }
    public void setListeMachine(ArrayList<Machine> listeMachine) {
        this.listeMachine = listeMachine;
    }
    public String getRefGamme() {
        return refGamme;
    }
    public void setRefGamme(String refGamme) {
        this.refGamme = refGamme;
    }
    public ArrayList<Operation> getListeOperation() {
        return listeOperation;
    }
    public void setListeOperation(ArrayList<Operation> listeOperation) {
        this.listeOperation = listeOperation;
    }
    public ArrayList<Equipement> getListeEquipement() {
        return listeEquipement;
    }
    public void setListeEquipement(ArrayList<Equipement> listeEquipement) {
        this.listeEquipement = listeEquipement;
    }

    
}

import java.util.ArrayList;

public class Poste {
    private String refPoste;
    private String dPoste;
    private ArrayList<Machine> listeMachine;
    
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
    public ArrayList<Machine> getListeMachine() {
        return listeMachine;
    }
    public void setListeMachine(ArrayList<Machine> listeMachine) {
        this.listeMachine = listeMachine;
    }
}

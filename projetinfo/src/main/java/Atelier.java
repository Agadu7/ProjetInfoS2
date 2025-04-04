import java.util.ArrayList;

public class Atelier {
    private int codeAtelier;
    private ArrayList<Personne> listePersonne;
    private ArrayList<Machine> listeMachine;
    private ArrayList<Poste> listePoste;

    public int getCodeAtelier() {
        return codeAtelier;
    }

    public void setCodeAtelier(int codeAtelier) {
        this.codeAtelier = codeAtelier;
    }
    
    public ArrayList<Personne> getListePersonne() {
        return listePersonne;
    }
    public void setListePersonne(ArrayList<Personne> listePersonne) {
        this.listePersonne = listePersonne;
    }

    public Atelier (int codeAtelier,ArrayList<Personne> listePersonne,ArrayList<Machine> listeMachine,ArrayList<Poste> listePoste){
        this.codeAtelier=codeAtelier;
        this.listePersonne=listePersonne;
        this.listePoste=listePoste;
        this.listeMachine=listeMachine;
    }

    public ArrayList<Machine> getListeMachine() {
        return listeMachine;
    }

    public void setListeMachine(ArrayList<Machine> listeMachine) {
        this.listeMachine = listeMachine;
    }

    public ArrayList<Poste> getListePoste() {
        return listePoste;
    }

    public void setListePoste(ArrayList<Poste> listePoste) {
        this.listePoste = listePoste;
    }
    
}

import java.util.ArrayList;

public class Atelier {
    private int codeAtelier;
    private ArrayList<Personne> listePersonne;

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

    public Atelier (int codeAtelier,ArrayList<Personne> listePersonne){
        this.codeAtelier=codeAtelier;
        this.listePersonne=listePersonne;
    }
}

import java.util.ArrayList;

public class Personne {
    private ArrayList<Personne> listePersonne;
    private String nom;
    private String prenom;
    private int code;

    public ArrayList<Personne> getListePersonne() {
        return listePersonne;
    }
    public void setListePersonne(ArrayList<Personne> listePersonne) {
        this.listePersonne = listePersonne;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }

    public Personne(ArrayList<Personne> listePersonne,String nom,String prenom,int code){
        this.code = code;
        this.listePersonne = listePersonne;
        this.nom = nom;
        this.prenom = prenom;
    }

}

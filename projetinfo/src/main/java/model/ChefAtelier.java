package model;

public class ChefAtelier {
    private String nom;
    private Atelier atelier;

    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public Atelier getAtelier() {
        return atelier;
    }
    public void setAtelier(Atelier atelier) {
        this.atelier = atelier;
    }

    public ChefAtelier(String nom, Atelier atelier){
        this.atelier = atelier;
        this.nom = nom;
    }
    
}

package model;

public class Operateur {
    private String code;
    private String nom;
    private String prenom;
    private String competences;

    public Operateur(String code, String nom, String prenom, String competences) {
        this.code = code;
        this.nom = nom;
        this.prenom = prenom;
        this.competences = competences;
    }
    
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
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
    public String getCompetences() {
        return competences;
    }
    public void setCompetences(String competences) {
        this.competences = competences;
    }

    public String convertirEnLigneOperateur(){
        return code + ";" + nom + ";" + prenom + ";" + competences;
    }

    public static Operateur convertirEnObjetOperateur(String ligne) {
        String[] parts = ligne.split(";");
        return new Operateur(parts[0], parts[1], parts[2], parts[3]);
    }

    
}

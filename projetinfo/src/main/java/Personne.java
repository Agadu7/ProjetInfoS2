

public class Personne {
    private String nom;
    private String prenom;
    private int code;

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

    public Personne(String nom,String prenom,int code){
        this.code = code;
        this.nom = nom;
        this.prenom = prenom;
    }

    public String convertirEnLignePersonne(){
        return nom + ";" + prenom + ";" + code;
    }

    public static Personne convertirEnObjetPersonne(String ligne) {
        String[] parts = ligne.split(";");
        return new Personne(parts[0], parts[1], Integer.parseInt(parts[2]));
    }

}

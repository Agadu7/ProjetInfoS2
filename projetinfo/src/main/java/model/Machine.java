package model;

public class Machine {
    public String refMachine;
    public String dMachine;
    public float x;
    public float y;
    public String type;
    public float cout;
    public float Largeur;
    public float Hauteur;
    
    public Machine(String refMachine, String dMachine, float x, float y, String type, float cout, float Largeur,
                    float Hauteur) {
        this.refMachine = refMachine;
        this.x=x;
        this.y=y;
        this.dMachine =dMachine;
        this.cout = cout;
        this.type=type;
        this.Largeur=Largeur;
        this.Hauteur=Hauteur;
    }

    public String getRefMachine() {
        return refMachine;
    }
    public void setRefMachine(String refMachine) {
        this.refMachine = refMachine;
    }
    public String getdMachine() {
        return dMachine;
    }
    public void setdMachine(String dMachine) {
        this.dMachine = dMachine;
    }
    public float getX() {
        return x;
    }
    public void setX(float x) {
        this.x = x;
    }
    public float getY() {
        return y;
    }
    public void setY(float y) {
        this.y = y;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public float getLargeur() {
        return Largeur;
    }
    public void setLargeur(float Largeur) {
        this.Largeur = Largeur;
    }
    public float getHauteur() {
        return Hauteur;
    }
    public void setHauteur(float Hauteur) {
        this.Hauteur = Hauteur;
    }

    public String convertirEnLigneMachine(){
        return refMachine + ";" + dMachine + ";" + x + ";" + y + ";" + type + ";" + cout+ ";" + Largeur + ";" + Hauteur;
    }

    public static Machine convertirEnObjetMachine(String ligne) {
        String[] parts = ligne.split(";");
        return new Machine(parts[0], parts[1], Float.parseFloat(parts[2]), Float.parseFloat(parts[3]), parts[4], 
        Float.parseFloat(parts[5]), Float.parseFloat(parts[6]), Float.parseFloat(parts[7]));
    }

}

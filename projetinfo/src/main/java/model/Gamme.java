package model;

import java.util.List;

public class Gamme {
    public List<Machine> listeMachine;
    public String refGamme;
    public List<Operation> listeOperation;
    public List<Produit> listeProduit;
    

    public Gamme(List<Machine> listeMachine, String refGamme, List<Operation> listeOperation,List<Produit> listeProduit) {
        this.listeMachine = listeMachine;
        this.refGamme=refGamme;
        this.listeOperation = listeOperation;
        this.listeProduit=listeProduit;
    }
    
    public List<Machine> getListeMachine() {
        return listeMachine;
    }
    public void setListeMachine(List<Machine> listeMachine) {
        this.listeMachine = listeMachine;
    }
    public String getRefGamme() {
        return refGamme;
    }
    public void setRefGamme(String refGamme) {
        this.refGamme = refGamme;
    }
    public List<Operation> getListeOperation() {
        return listeOperation;
    }
    public void setListeOperation(List<Operation> listeOperation) {
        this.listeOperation = listeOperation;
    }
    public List<Produit> getListeProduit() {
        return listeProduit;
    }

    public void setListeProduit(List<Produit> listeProduit) {
        this.listeProduit = listeProduit;
    }

    public void afficheProduit(){
        for (int i = 0; i < this.listeProduit.size(); i++) {
            System.out.println("Produit "+i+" : "+this.listeProduit.get(i));
          }
    }
    
}

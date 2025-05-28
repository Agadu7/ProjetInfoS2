package model;

import java.util.ArrayList;
import java.util.Scanner;

public class Gamme {
    public ArrayList<Machine> listeMachine;
    public String refGamme;
    public ArrayList<Operation> listeOperation;
    public ArrayList<Produit> listeProduit;

    public Gamme(ArrayList<Machine> listeMachine, String refGamme, ArrayList<Operation> listeOperation,ArrayList<Produit> listeProduit) {
        this.listeMachine = listeMachine;
        this.refGamme=refGamme;
        this.listeOperation = listeOperation;
        this.listeProduit=listeProduit;
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
    public ArrayList<Produit> getListeProduit() {
        return listeProduit;
    }

    public void setListeProduit(ArrayList<Produit> listeProduit) {
        this.listeProduit = listeProduit;
    }

    public void afficheProduit(){
        for (int i = 0; i < this.listeProduit.size(); i++) {
            System.out.println("Produit "+i+" : "+this.listeProduit.get(i));
          }
    } 
     
    public void supprimerProduit(){
        System.out.println("Quelle est le code du produit que vous souhaitez supprimer?");
        try (Scanner sc = new Scanner(System.in)) {
            String str1 = sc.nextLine();
            Produit produitASupprimer = new Produit(str1, null);
            for (int i = 0; i < listeProduit.size(); i++) {
                if (listeProduit.get(i).getCodeProduit().equals(produitASupprimer.getCodeProduit())) {
                    listeProduit.remove(i);
                }
                else{
                    System.out.println("Le produit n'existe pas");
                }
            }
        }
    } 

    public void modifierProduit(){
        System.out.println("Quelle est le code du produit que vous souhaitez modifier?");
        try (Scanner sc = new Scanner(System.in)) {
            String str1 = sc.nextLine();
            System.out.println("Souhaitez vous modifier la description ? (Y/N)");
            String d = sc.nextLine();
            System.out.println("Souhaitez vous modifier le code du produit ? (Y/N)");
            String c = sc.nextLine();
            Produit produitASupprimer = new Produit(str1, null);
            for (int i = 0; i < listeProduit.size(); i++) {
                if (listeProduit.get(i).getCodeProduit().equals(produitASupprimer.getCodeProduit())) {
                    if(d.equalsIgnoreCase("Y")){
                        System.out.println("Quelle est la nouvelle description ?");
                        String d1 = sc.nextLine();
                        listeProduit.get(i).setdProduit(d1);
                    }
                    if(c.equalsIgnoreCase("Y")){
                        System.out.println("Quel est le nouveau code de ce produit ?");
                        String c1 = sc.nextLine();
                        listeProduit.get(i).setCodeProduit(c1);
                    }
                }
                else{
                    System.out.println("Ce produit n'existe pas");
                }
                
            }
        }
    }

    public void ajouterProduit(){
        System.out.println("Quel est le code du produit ?");
        try (Scanner sc = new Scanner(System.in)) {
            String str1 = sc.nextLine();
            System.out.println("Quelle est la description du produit ?");
            String str2 = sc.nextLine();
            Produit produit = new Produit(str1, str2);
            listeProduit.add(produit);
        }
    }

    public String convertirEnLigneGamme(){
        return listeMachine + ";" + refGamme + ";" + listeOperation +  ";" + listeProduit;
    }

    
}

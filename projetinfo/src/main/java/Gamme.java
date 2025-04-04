import java.util.ArrayList;
import java.util.Scanner;

public class Gamme {
    private ArrayList<Machine> listeMachine;
    private String refGamme;
    private ArrayList<Operation> listeOperation;
    private ArrayList<Equipement> listeEquipement;
    private ArrayList<Produit> listeProduit;

    public Gamme(ArrayList<Machine> listeMachine, String refGamme, ArrayList<Operation> listeOperation,ArrayList<Equipement> listeEquipement,ArrayList<Produit> listeProduit) {
        this.listeMachine = listeMachine;
        this.refGamme=refGamme;
        this.listeOperation = listeOperation;
        this.listeEquipement=listeEquipement;
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
    public ArrayList<Equipement> getListeEquipement() {
        return listeEquipement;
    }
    public void setListeEquipement(ArrayList<Equipement> listeEquipement) {
        this.listeEquipement = listeEquipement;
    }

    public ArrayList<Produit> getListeProduit() {
        return listeProduit;
    }

    public void setListeProduit(ArrayList<Produit> listeProduit) {
        this.listeProduit = listeProduit;
    }

    public void afficheProduit(){
        for (int i = 0; i < this.listeProduit.size(); i++) {
            System.out.println("Machine "+i+" : "+this.listeProduit.get(i));
          }
    } 
     
    public void supprimerProduit(){
        System.out.println("Quelle est le code du produit que vous souhaitez supprimer?");
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        listeProduit.remove(listeProduit.getCodeProduit()==str);
        for (Produit listeProduit : listeProduit) {
            if (listeProduit.getCodeProduit().equals(str)) {
                listeProduit.remove(str);
            }
            else{
                System.out.println("Ce produit n'existe pas");
            }
        }
    } 
}

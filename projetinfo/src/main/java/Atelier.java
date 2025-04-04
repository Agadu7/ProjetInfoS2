import java.util.ArrayList;
import java.util.Scanner;

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
public void afficheMachine(){
        for (int i = 0; i < this.listeMachine.size(); i++) {
            System.out.println("Machine "+i+" : "+this.listeMachine.get(i));
          }
    } 
     
    public void supprimerMachine(){
        System.out.println("Quelle est le code de la machine que vous souhaitez supprimer?");
        Scanner sc = new Scanner(System.in);
        String str1 = sc.nextLine();
        Machine machineASupprimer = new Machine(str1, null,0,0,null,0);
        for (int i = 0; i < listeMachine.size(); i++) {
            if (listeMachine.get(i).getRefMachine().equals(machineASupprimer.getRefMachine())) {
                listeMachine.remove(i);
            }
            else{
                System.out.println("La machine n'existe pas");
            }
        }
    } 

    public void modifierMachine(){
        System.out.println("Quelle est le code de la machine que vous souhaitez modifier?");
        Scanner sc = new Scanner(System.in);
        String str1 = sc.nextLine();
        System.out.println("Souhaitez vous modifier la description ? (Y/N)");
        String d = sc.nextLine();
        System.out.println("Souhaitez vous modifier la position en x de la machine ? (Y/N)");
        String x = sc.nextLine();
        System.out.println("Souhaitez vous modifier la position en y de la machine ? (Y/N)");
        String y = sc.nextLine();
        System.out.println("Souhaitez vous modifier le type de la machine ? (Y/N)");
        String t = sc.nextLine();
        System.out.println("Souhaitez vous modifier le coût de la machine ? (Y/N)");
        String c = sc.nextLine();
        Machine machineAModifier = new Machine(str1, null,0,0,null,0);
        for (int i = 0; i < listeMachine.size(); i++) {
            if (listeMachine.get(i).getRefMachine().equals(machineAModifier.getRefMachine())) {
                if(d.equalsIgnoreCase("Y")){
                    System.out.println("Quelle est la nouvelle description ?");
                    String d1 = sc.nextLine();
                    listeMachine.get(i).setdMachine(d1);
                }
                if(c.equalsIgnoreCase("Y")){
                    System.out.println("Quel est le nouveau code de ce produit ?");
                    String c1 = sc.nextLine();
                    listeMachine.get(i).setCodeProduit(c1);
                }
            }
            else{
                System.out.println("Ce produit n'existe pas");
            }
            
        }
    }

    
}

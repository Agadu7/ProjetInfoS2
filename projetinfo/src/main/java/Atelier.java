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

    public void affichePoste(){
        for (int i = 0; i < this.listePoste.size(); i++) {
            System.out.println("Poste "+i+" : "+this.listePoste.get(i));
          }
    } 
     
    public void supprimerPoste(){
        System.out.println("Quelle est le poste que vous souhaitez supprimer?");
        Scanner sc = new Scanner(System.in);
        String str1 = sc.nextLine();
        Poste posteASupprimer = new Poste(str1, null,null);
        for (int i = 0; i < listePoste.size(); i++) {
            if (listePoste.get(i).getRefPoste().equals(posteASupprimer.getRefPoste())) {
                listePoste.remove(i);
            }
            else{
                System.out.println("Le poste n'existe pas");
            }
        }
    } 

    public void modifierPoste(){
        System.out.println("Quelle est le poste que vous souhaitez modifier?");
        Scanner sc = new Scanner(System.in);
        String str1 = sc.nextLine();
        System.out.println("Souhaitez vous modifier la description ? (Y/N)");
        String d = sc.nextLine();
        System.out.println("Souhaitez vous modifier la ref du poste ? (Y/N)");
        String c = sc.nextLine();
        System.out.println("Souhaitez vous modifier les machine associé au poste ? (Y/N)");
        String lm = sc.nextLine();
        Poste produitAModifer = new Poste(str1, null,null);
        for (int i = 0; i < listePoste.size(); i++) {
            if (listePoste.get(i).getRefPoste().equals(produitAModifer.getRefPoste())) {
                if(d.equalsIgnoreCase("Y")){
                    System.out.println("Quelle est la nouvelle description ?");
                    String d1 = sc.nextLine();
                    listePoste.get(i).setdPoste(d1);
                }
                if(c.equalsIgnoreCase("Y")){
                    System.out.println("Quel est le nouveau code de ce poste ?");
                    String c1 = sc.nextLine();
                    listePoste.get(i).setRefPoste(c1);
                }
                if(lm.equalsIgnoreCase("Y")){
                    ArrayList<Machine> listeMachines = new ArrayList<>();

                    System.out.println("Combien de machines voulez-vous ajouter ?");
                    int nombreMachines = sc.nextInt();
                    sc.nextLine(); // Consommer la nouvelle ligne

                    for (int j = 0; i < nombreMachines; j++) {
                        System.out.println("Saisie des informations pour la machine " + (j + 1));

                        System.out.print("Référence Machine : ");
                        String refMachine = sc.nextLine();

                        System.out.print("Description Machine : ");
                        String dMachine = sc.nextLine();

                        System.out.print(" x ");
                        float x = sc.nextInt();
                        sc.nextLine();

                        System.out.print(" y ");
                        float y = sc.nextInt();
                        sc.nextLine();

                        System.out.print(" Coût ");
                        float cout = sc.nextInt();
                        sc.nextLine(); // Consommer la nouvelle ligne

                        System.out.print(" Type de machine ");
                        String type = sc.nextLine();

                        listeMachines.add(new Machine(refMachine, dMachine, x, y, type, cout));
                    }
                }
            }
            else{
                System.out.println("Ce poste n'existe pas");
            }
            
        }
    }
    
}

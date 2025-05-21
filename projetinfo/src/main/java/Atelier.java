import java.util.ArrayList;
import java.util.Scanner;

public class Atelier {
    private int codeAtelier;
    private ArrayList<Personne> listePersonne;
    private ArrayList<Machine> listeMachine;
    private ArrayList<Poste> listePoste;
    private ArrayList<Gamme> listeGamme;
    private Map<String, Integer> fiabiliteParMachine = new HashMap<>();
    private Set<String> evenementsPositifs = new HashSet<>(Arrays.asList("OK", "Maintenance réussie"));

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

    public Atelier (int codeAtelier,ArrayList<Personne> listePersonne,ArrayList<Machine> listeMachine,ArrayList<Poste> listePoste, ArrayList<Gamme> listeGamme){
        this.codeAtelier=codeAtelier;
        this.listePersonne=listePersonne;
        this.listePoste=listePoste;
        this.listeMachine=listeMachine;
        this.listeGamme=listeGamme;
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

    public ArrayList<Gamme> getListeGamme() {
        return listeGamme;
    }

    public void setListeGamme(ArrayList<Gamme> listeGamme) {
        this.listeGamme = listeGamme;
    }


    public void affichePoste(){
        for (int i = 0; i < this.listePoste.size(); i++) {
            System.out.println("Poste "+i+" : "+this.listePoste.get(i));
          }
    } 
     
    public void supprimerPoste(){
        System.out.println("Quelle est le poste que vous souhaitez supprimer?");
        try (Scanner sc = new Scanner(System.in)) {
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
    } 

    public void modifierPoste(){
        System.out.println("Quelle est le poste que vous souhaitez modifier?");
        try (Scanner sc = new Scanner(System.in)) {
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

    public void afficheMachine(){
        for (int i = 0; i < this.listeMachine.size(); i++) {
            System.out.println("Machine "+i+" : "+this.listeMachine.get(i));
          }
    } 
     
    public void supprimerMachine(){
        System.out.println("Quelle est le poste que vous souhaitez supprimer?");
        try (Scanner sc = new Scanner(System.in)) {
            String str1 = sc.nextLine();
            Machine machineASupprimer = new Machine(str1, null,0,0,null,0);
            for (int i = 0; i < listeMachine.size(); i++) {
                if (listeMachine.get(i).getRefMachine().equals(machineASupprimer.getRefMachine())) {
                    listeMachine.remove(i);
                }
                else{
                    System.out.println("Le poste n'existe pas");
                }
            }
        }
    } 

    public void modifierMachine(){
        System.out.println("Quelle est la machine que vous souhaitez modifier?");
        try (Scanner sc = new Scanner(System.in)) {
            String str1 = sc.nextLine();
            System.out.println("Souhaitez vous modifier la description ? (Y/N)");
            String d = sc.nextLine();
            System.out.println("Souhaitez vous modifier la ref du poste ? (Y/N)");
            String c = sc.nextLine();
            System.out.println("Souhaitez vous modifier la coordonnée x de la machine ? (Y/N)");
            String x = sc.nextLine();
            System.out.println("Souhaitez vous modifier la coordonnée y de la machine ? (Y/N)");
            String y = sc.nextLine();
            System.out.println("Souhaitez vous modifier le type de machine (Y/N)");
            String t = sc.nextLine();
            System.out.println("Souhaitez vous modifier le cout du fonctionnement de la machine ? (Y/N)");
            String cout = sc.nextLine();
            Machine machineAModifer = new Machine(str1, null,0,0,null,0);
            for (int i = 0; i < listeMachine.size(); i++) {
                if (listeMachine.get(i).getRefMachine().equals(machineAModifer.getRefMachine())) {
                    if(d.equalsIgnoreCase("Y")){
                        System.out.println("Quelle est la nouvelle description ?");
                        String d1 = sc.nextLine();
                        listeMachine.get(i).setdMachine(d1);
                    }
                    if(c.equalsIgnoreCase("Y")){
                        System.out.println("Quel est le nouveau code de cette machine ?");
                        String c1 = sc.nextLine();
                        listeMachine.get(i).setRefMachine(c1);
                    }
                    if(x.equalsIgnoreCase("Y")){
                        System.out.print("Quelle est la nouvelle coordonnée x de cette machine ?");
                        float x1 = sc.nextInt();
                        sc.nextLine();
                        listeMachine.get(i).setX(x1);
                    }
                    if(y.equalsIgnoreCase("Y")){
                        System.out.print("Quelle est la nouvelle coordonnée y de cette machine ?");
                        float y1 = sc.nextInt();
                        sc.nextLine();
                        listeMachine.get(i).setY(y1);
                    }
                    if(t.equalsIgnoreCase("Y")){
                        System.out.print("Quel est le nouveau type de cette machine ?");
                        String type = sc.nextLine();
                        listeMachine.get(i).setType(type);
                    }
                    if(cout.equalsIgnoreCase("Y")){
                        System.out.print("Quel est le nouveau coût de cette machine ? ");
                        float c1 = sc.nextInt();
                        sc.nextLine();
                        listeMachine.get(i).setCout(c1);
                    }
                }
                else{
                    System.out.println("Ce poste n'existe pas");
                }
                
            }
        }
    }

    public void afficheGamme(){
        for (int i=0; i<this.listeGamme.size(); i++){
            System.out.println("Gamme "+i+": "+this.listeGamme.get(i));
        }
    }

    public void supprimerGamme(){
        System.out.println("Quelle gamme voulez-vous supprimer ?");
          try (Scanner sc = new Scanner(System.in)) {
            String str1 = sc.nextLine();
            Gamme gammeASupprimer = new Gamme(null, str1, null, null, null);
            for (int i = 0; i < listeGamme.size(); i++) {
                if (listeGamme.get(i).getRefGamme().equals(gammeASupprimer.getRefGamme())) {
                    listeGamme.remove(i);
                }
                else{
                    System.out.println("La gamme n'existe pas");
                }
            }
        }
    }
    
    public void modifierGamme(){
        System.out.println("Quelle est la gamme que vous souhaitez modifier?");
        try (Scanner sc = new Scanner(System.in)) {
            String str1 = sc.nextLine();
            System.out.println("Souhaitez-vous modifier la reférence de la gamme ? (Y/N)");
            String r = sc.nextLine();
            Gamme gammeAModifier = new Gamme(null, str1, null, null, null);
            for (int i=0; i < listeGamme.size(); i++){
                if (listeGamme.get(i).getRefGamme().equals(gammeAModifier.getRefGamme())){
                    if (r.equalsIgnoreCase("Y")){
                        System.out.println("Quelle est la nouvelle reférence de cette gamme ?");
                        String r1 = sc.nextLine();
                        listeGamme.get(i).setRefGamme(r1);
                    }
                }
                else{
                    System.out.println("Cette gamme n'existe pas");
                }
            }
        }
    }
    public void calculerFiabilite(Map<String, List<EvenementMachine>> donnees) {
        for (String machine : donnees.keySet()) {
            List<EvenementMachine> evenements = donnees.get(machine);
            evenements.sort(Comparator.comparing(e -> e.horodatage));

            long cumulFonctionnement = 0;
            Date debutObservation = evenements.get(0).horodatage;
            Date finObservation = evenements.get(evenements.size() - 1).horodatage;

            Date heureDebut = null;

            for (EvenementMachine e : evenements) {
                if (e.type.equals("A")) {
                    heureDebut = e.horodatage;
                } else if (e.type.equals("D") && heureDebut != null &&
                          (e.evenement.equals("ok") || e.evenement.equals("maintenance réussie"))) {
                    long duree = (e.horodatage.getTime() - heureDebut.getTime()) / (1000 * 60);
                    if (duree > 0) cumulFonctionnement += duree;
                    heureDebut = null;
                }
            }

            long dureeObservation = (finObservation.getTime() - debutObservation.getTime()) / (1000 * 60);
            double fiabilite = dureeObservation > 0 ? (double) cumulFonctionnement / dureeObservation : 0;

            System.out.printf("Machine : %-7s | Fiabilité : %.2f (%d min fonctionnement / %d min observés)%n",
                    machine, fiabilite, cumulFonctionnement, dureeObservation);
        }
    }
    
    public void chargerFiabilite(String cheminFichier) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(cheminFichier));

        String ligneLue;
        while ((ligneLue = in.readLine()) != null) {
            StringTokenizer t = new StringTokenizer(ligneLue, ";");
            String machine = t.nextToken();
            String evenement = t.nextToken();

            fiabiliteParMachine.putIfAbsent(machine, 0);
            if (evenementsPositifs.contains(evenement)) {
                fiabiliteParMachine.put(machine, fiabiliteParMachine.get(machine) + 1);
            }
        }

        in.close();
    }

    public int getFiabilite(String machine) {
        return fiabiliteParMachine.getOrDefault(machine, 0);
    }

    public void chargerEvenements(String cheminFichier) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("projetinfo\\src\\main\\java\\suiviMaintenance.txt"));
        String ligne;

        while ((ligne = in.readLine()) != null) {
            String[] champs = ligne.split(";");
            if (champs.length < 6) continue;

            String date = champs[0];
            String heure = champs[1];
            String machine = champs[2];
            String type = champs[3];
            String evenement = champs[5];

            try {
                Date horodatage = formatDate.parse(date + " " + heure);
                EvenementMachine ev = new EvenementMachine(horodatage, type, evenement);
                evenementsParMachine.putIfAbsent(machine, new ArrayList<>());
                evenementsParMachine.get(machine).add(ev);
            } catch (Exception e) {
                System.out.println("Erreur de parsing pour la ligne : " + ligne);
            }
        }

        in.close();
    }

    public Map<String, List<EvenementMachine>> getEvenementsParMachine() {
        return evenementsParMachine;
    }
    public EvenementMachine(Date horodatage, String type, String evenement) {
        this.horodatage = horodatage;
        this.type = type;
        this.evenement = evenement;
    }
}


package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class Atelier {
    private int codeAtelier;
    private ArrayList<Operateur> listeOperateur;
    public ArrayList<Machine> listeMachine;
    private ArrayList<Poste> listePoste;
    private ArrayList<Gamme> listeGamme;
    private Map<String, Integer> fiabiliteParMachine = new HashMap<>();
    private Set<String> evenementsPositifs = new HashSet<>(Arrays.asList("OK", "Maintenance réussie"));
    private Map<String, List<EvenementMachine>> evenementsParMachine = new HashMap<>();

    public int getCodeAtelier() {
        return codeAtelier;
    }

    public void setCodeAtelier(int codeAtelier) {
        this.codeAtelier = codeAtelier;
    }
    
    public ArrayList<Operateur> getListeoperateur() {
        return listeOperateur;
    }
    public void setListePersonne(ArrayList<Operateur> listeOperateur) {
        this.listeOperateur = listeOperateur;
    }

    public Atelier (int codeAtelier,ArrayList<Operateur> listeOperateur,ArrayList<Machine> listeMachine,ArrayList<Poste> listePoste, ArrayList<Gamme> listeGamme){
        this.codeAtelier=codeAtelier;
        this.listeOperateur=listeOperateur;
        this.listePoste=listePoste;
        this.listeMachine=listeMachine;
        this.listeGamme=listeGamme;
    }
    public Atelier() {
        this.codeAtelier = 0;
        this.listeOperateur = new ArrayList<>();
        this.listeMachine = new ArrayList<>();
        this.listePoste = new ArrayList<>();
        this.listeGamme = new ArrayList<>();
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

                            //listeMachines.add(new Machine(refMachine, dMachine, x, y, type, cout));
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
            Machine machineASupprimer = new Machine(str1, null,0,0,null,0, 0, 0);
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
            Machine machineAModifer = new Machine(str1, null,0,0,null,0, 0, 0);
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
                        //listeMachine.get(i).setCout(c1);
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
    
    public void sauvegarderAtelier(String nomFichier) { 
        try (PrintWriter writer = new PrintWriter(new FileWriter(nomFichier))) {
        writer.println(codeAtelier);
        
        writer.println("Operateurs:");
        for (Operateur p : listeOperateur) {
            writer.println(p.convertirEnLigneOperateur());
        }

        writer.println("Machines:");
        for (Machine m : listeMachine) {
            writer.println(m.convertirEnLigneMachine());
        }

        writer.println("Postes:");
        for (Poste p : listePoste) {
            writer.println(p.convertirEnLignePoste());
        }

        writer.println("Gammes:");
        for (Gamme g : listeGamme) {
            writer.println(g.convertirEnLigneGamme());
        }

        System.out.println("Atelier sauvegardé avec succès !");
        }

        catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde : " + e.getMessage());
        }
    }

    public static Atelier chargerAtelier(String nomFichier) {
        try (BufferedReader reader = new BufferedReader(new FileReader(nomFichier))) {
        int code = Integer.parseInt(reader.readLine());

        ArrayList<Operateur> operateurs = new ArrayList<>();
        ArrayList<Machine> machines = new ArrayList<>();
        ArrayList<Poste> postes = new ArrayList<>();
        ArrayList<Gamme> gammes = new ArrayList<>();

        String line;
        String section = "";

        while ((line = reader.readLine()) != null) {
            if (line.equals("Operateurs:") || line.equals("Machines:") || line.equals("Postes:") || line.equals("Gammes:")) {
                section = line;
                continue;
            }

            switch (section) {
                case "Personnes:":
                    operateurs.add(Operateur.convertirEnObjetOperateur(line));
                    break;
                case "Machines:":
                    machines.add(Machine.convertirEnObjetMachine(line));
                    break;
                case "Postes:":
                    postes.add(Poste.convertirEnObjetPoste(line, machines));
                    break;
                case "Gammes:":
                    //gammes.add(Gamme.convertirEnLigneGamme(line));
                    break;
            }
        }

        return new Atelier(code, operateurs, machines, postes, gammes);
        }

        catch (IOException | NumberFormatException e) {
            System.err.println("Erreur lors du chargement : " + e.getMessage());
        return null;
        }
    }

    

    public void calculerFiabilite(Map<String, List<EvenementMachine>> donnees) {
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    for (String machine : donnees.keySet()) {
        List<EvenementMachine> evenements = donnees.get(machine);
        evenements.sort(Comparator.comparing(e -> e.horodatage));

        // Map pour stocker le cumul de fonctionnement par jour (clé = date au format dd/MM/yyyy)
        Map<String, Long> cumulParJour = new HashMap<>();
        // Map pour stocker la durée d'observation par jour (ici, on suppose 24h = 1440 minutes)
        Map<String, Long> observationParJour = new HashMap<>();

        Date heureDebut = null;
        String jourCourant = null;

        for (EvenementMachine e : evenements) {
            String jour = sdf.format(e.horodatage);
            observationParJour.putIfAbsent(jour, 1440L); // 24h en minutes

            if (e.type.equals("A")) {
                heureDebut = e.horodatage;
                jourCourant = jour;
            } else if (e.type.equals("D") && heureDebut != null &&
                      (e.evenement.equalsIgnoreCase("ok") || e.evenement.equalsIgnoreCase("maintenance réussie"))) {
                long duree = (e.horodatage.getTime() - heureDebut.getTime()) / (1000 * 60);
                if (duree > 0) {
                    cumulParJour.put(jourCourant, cumulParJour.getOrDefault(jourCourant, 0L) + duree);
                }
                heureDebut = null;
            }
        }

        System.out.println("Fiabilité par jour pour la machine : " + machine);
        for (String jour : cumulParJour.keySet()) {
            long cumul = cumulParJour.get(jour);
            long observation = observationParJour.getOrDefault(jour, 1440L);
            double fiabilite = observation > 0 ? (double) cumul / observation : 0;
            System.out.println("  Jour " + jour + " : fiabilité = " + String.format("%.4f", fiabilite));
        }

    }
}
    
    public void chargerFiabilite(Map<String, List<EvenementMachine>> donnees) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("suiviMaintenance.txt"));

        String ligneLue;
        while ((ligneLue = in.readLine()) != null) {
            String[] t = ligneLue.split(";");
        if (t.length < 6) continue;
        String machine = t[2];
        String evenement = t[5];
        fiabiliteParMachine.putIfAbsent(machine, 0);
        if (evenementsPositifs.contains(evenement)) {
            fiabiliteParMachine.put(machine, fiabiliteParMachine.get(machine) + 1);
        }
        // Remplir la map des événements par machine si besoin :
        EvenementMachine ev = new EvenementMachine(
            parseDate(t[0], t[1]), t[3], evenement, machine
        );
        evenementsParMachine.computeIfAbsent(machine, k -> new ArrayList<>()).add(ev);
    }
    in.close();
        System.out.println("=== Fiabilité par machine ===");
        for (Map.Entry<String, Integer> entry : fiabiliteParMachine.entrySet()) {
        System.out.println("Machine : " + entry.getKey() + " → Fiabilité : " + entry.getValue());
    }
    // Méthode pour parser la date
    private Date parseDate(String date, String heure) {
        try {
            return new SimpleDateFormat("ddMMyyyy HH:mm").parse(date + " " + heure);
        } catch (ParseException e) {
            return null;
        }
    }

    public int getFiabilite(String machine) {
        return fiabiliteParMachine.getOrDefault(machine, 0);
    }

    public Map<String, List<EvenementMachine>> getEvenementsParMachine() {
        return evenementsParMachine;
    }
    public static void main (String[] args) {
        EvenementMachine em = new EvenementMachine(new Date(), "Type", "Evenement", "Machine");
        try {
            em.chargerEvenements("suiviMaintenance.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
         chargerFiabilite(em.evenementsParMachine);
    }
}
    
  


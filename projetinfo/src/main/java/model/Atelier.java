package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
    }

    public void afficheMachine(){
        for (int i = 0; i < this.listeMachine.size(); i++) {
            System.out.println("Machine "+i+" : "+this.listeMachine.get(i));
          }
    } 
     
    public void supprimerMachine(){
    } 

    public void modifierMachine(){
    }

    public void afficheGamme(){
        for (int i=0; i<this.listeGamme.size(); i++){
            System.out.println("Gamme "+i+": "+this.listeGamme.get(i));
        }
    }

    public void supprimerGamme(){
    }
    
    public void modifierGamme(){
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
    
    public void chargerFiabilite(String suiviMaintenance) throws IOException {
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
}
    
  


package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.text.SimpleDateFormat;

public class Atelier {
    private int codeAtelier;
    public ArrayList<Operateur> listeOperateur;
    public ArrayList<Machine> listeMachine;
    public ArrayList<Poste> listePoste;
    public ArrayList<Gamme> listeGamme;

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

    public double calculerFiabiliteMachineUnique(Map<String, List<EvenementMachine>> donnees, String refMachine) {
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    List<EvenementMachine> evenements = donnees.get(refMachine);

    evenements.sort(Comparator.comparing(e -> e.horodatage));

    Map<String, Long> cumulParJour = new HashMap<>();
    Map<String, Long> observationParJour = new HashMap<>();

    Date heureDebut = null;
    String jourCourant = null;

    for (EvenementMachine e : evenements) {
        String jour = sdf.format(e.horodatage);

        // Heures de fonctionnement = 6h à 20h = 14h = 840 minutes
        observationParJour.putIfAbsent(jour, 840L);

        int heure = e.horodatage.getHours();
        if (heure < 6 || heure >= 20) continue;  // En dehors de la plage de fonctionnement

        if (e.type.equals("A")) {
            heureDebut = e.horodatage;
            jourCourant = jour;
        } else if (e.type.equals("D") && heureDebut != null &&
                (e.evenement.equalsIgnoreCase("ok") || e.evenement.equalsIgnoreCase("maintenance réussie"))) {

            long debut = Math.max(heureDebut.getTime(), getTimeAtHour(heureDebut, 6));
            long fin = Math.min(e.horodatage.getTime(), getTimeAtHour(heureDebut, 20));

            long duree = (fin - debut) / (1000 * 60);
            if (duree > 0) {
                cumulParJour.put(jourCourant, cumulParJour.getOrDefault(jourCourant, 0L) + duree);
            }
            heureDebut = null;
        }
    }

    long totalCumul = cumulParJour.values().stream().mapToLong(Long::longValue).sum();
    long totalObservation = observationParJour.values().stream().mapToLong(Long::longValue).sum();

    double fiabilite = totalObservation > 0 ? (double) totalCumul / totalObservation : 0;

    return fiabilite;
    }

    private long getTimeAtHour(Date base, int targetHour) {
        Date copy = new Date(base.getTime());
        copy.setHours(targetHour);
        copy.setMinutes(0);
        copy.setSeconds(0);
        return copy.getTime();
    }
}
    
  


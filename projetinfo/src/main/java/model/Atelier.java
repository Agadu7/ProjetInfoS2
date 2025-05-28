package model;

import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.text.SimpleDateFormat;

public class Atelier {
    public int codeAtelier;
    public List<Operateur> listeOperateur;
    public List<Machine> listeMachine;
    public List<Poste> listePoste;
    public List<Gamme> listeGamme;

    public int getCodeAtelier() {
        return codeAtelier;
    }

    public void setCodeAtelier(int codeAtelier) {
        this.codeAtelier = codeAtelier;
    }
    
    public List<Operateur> getListeoperateur() {
        return listeOperateur;
    }
    public void setListePersonne(List<Operateur> listeOperateur) {
        this.listeOperateur = listeOperateur;
    }

    public Atelier (int codeAtelier,List<Operateur> listeOperateur,List<Machine> listeMachine,List<Poste> listePoste, List<Gamme> listeGamme){
        this.codeAtelier=codeAtelier;
        this.listeOperateur=listeOperateur;
        this.listePoste=listePoste;
        this.listeMachine=listeMachine;
        this.listeGamme=listeGamme;
    }

    public List<Machine> getListeMachine() {
        return listeMachine;
    }

    public void setListeMachine(List<Machine> listeMachine) {
        this.listeMachine = listeMachine;
    }

    public List<Poste> getListePoste() {
        return listePoste;
    }

    public void setListePoste(List<Poste> listePoste) {
        this.listePoste = listePoste;
    }

    public List<Gamme> getListeGamme() {
        return listeGamme;
    }

    public void setListeGamme(List<Gamme> listeGamme) {
        this.listeGamme = listeGamme;
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

    public boolean check(String refGamme) {
        Optional<Gamme> gamme = listeGamme.stream()
                .filter(m -> m.getRefGamme().equals(refGamme))
                .findFirst();
        return gamme.isPresent();
    }

}
    
  


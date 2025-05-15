import java.io.IOException;
import java.util.*;

public class CalculFiabilite {

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

    public static void main(String[] args) {
        try {
            ExtractionEvenement extracteur = new ExtractionEvenement();
            extracteur.chargerEvenements("suiviMaintenance.txt");

            CalculFiabilite calc = new CalculFiabilite();
            calc.calculerFiabilite(extracteur.getEvenementsParMachine());

        } catch (IOException e) {
            System.out.println("Erreur de lecture : " + e.getMessage());
        }
    }
}

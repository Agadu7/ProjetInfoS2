import java.io.IOException;
import java.util.*;

public class TriFiabilite {

    public static void main(String[] args) {
        try {
            // Étape 1 : Extraction des événements depuis le fichier
            ExtractionEvenement extracteur = new ExtractionEvenement();
            extracteur.chargerEvenements("suiviMaintenance.txt");

            // Étape 2 : Calcul des fiabilités
            Map<String, List<EvenementMachine>> donnees = extracteur.getEvenementsParMachine();
            Map<String, Double> fiabilites = new HashMap<>();
            Map<String, Long> dureesFonctionnement = new HashMap<>();
            Map<String, Long> dureesObservation = new HashMap<>();

            for (String machine : donnees.keySet()) {
                List<EvenementMachine> events = donnees.get(machine);
                events.sort(Comparator.comparing(e -> e.horodatage));

                long cumulFonctionnement = 0;
                Date debutObservation = events.get(0).horodatage;
                Date finObservation = events.get(events.size() - 1).horodatage;

                Date heureDebut = null;

                for (EvenementMachine e : events) {
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

                fiabilites.put(machine, fiabilite);
                dureesFonctionnement.put(machine, cumulFonctionnement);
                dureesObservation.put(machine, dureeObservation);
            }

            // Étape 3 : Tri croissant de la fiabilité
            List<Map.Entry<String, Double>> classement = new ArrayList<>(fiabilites.entrySet());
            classement.sort(Map.Entry.comparingByValue());

            // Étape 4 : Affichage du classement
            System.out.println("Classement des machines par fiabilité (croissante) :\n");
            for (int i = 0; i < classement.size(); i++) {
                String machine = classement.get(i).getKey();
                double fiabilite = classement.get(i).getValue();
                long fonctionnement = dureesFonctionnement.get(machine);
                long observation = dureesObservation.get(machine);

                System.out.printf("%d. %-7s | Fiabilité : %.2f | %d min fonctionnement / %d min observés%n",
                        i + 1, machine, fiabilite, fonctionnement, observation);
            }

        } catch (IOException e) {
            System.err.println("Erreur de lecture du fichier : " + e.getMessage());
        }
    }
}

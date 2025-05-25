
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        System.out.println("Début du programme TriFiabilite"); // DEBUG

        try {
            String cheminFichier = "src/main/java/suiviMaintenance.txt";
            System.out.println("Chemin du fichier utilisé : " + cheminFichier); // DEBUG
            System.out.println("Chemin absolu : " + new java.io.File(cheminFichier).getAbsolutePath()); // DEBUG

            // Étape 1 : Extraction des événements depuis le fichier avec EvenementMachine et Atelier

            // On suppose que tu as une méthode utilitaire dans Atelier pour charger les événements par machine
            Atelier atelier = new Atelier(); // ou récupère ton instance selon ton constructeur

            // Charge les événements depuis le fichier texte
            try {
                // Cette méthode doit remplir evenementsParMachine dans Atelier
                atelier.chargerFiabilite(cheminFichier); // ou une méthode adaptée, par exemple chargerEvenementsParMachine(cheminFichier)
            } catch (IOException e) {
                System.err.println("Erreur lors du chargement des événements : " + e.getMessage());
            }

            // Récupère la map machine -> liste d'événements
            Map<String, List<EvenementMachine>> donnees = atelier.getEvenementsParMachine();
            System.out.println("Nombre de machines chargées : " + donnees.size()); // DEBUG

            // Étape 2 : Calcul des fiabilités
            System.out.println("Nombre de machines chargées : " + donnees.size()); // DEBUG

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

        } catch (Exception e) {
            System.err.println("Erreur dans le programme : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
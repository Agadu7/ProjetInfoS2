import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

// Représente un événement avec toutes les infos nécessaires
class EvenementMachine {
    Date horodatage;
    String type;      // A ou D
    String evenement; // ok, panne, maintenance, etc.

    public EvenementMachine(Date horodatage, String type, String evenement) {
        this.horodatage = horodatage;
        this.type = type;
        this.evenement = evenement;
    }
}

public class ExtractionEvenement {
    private Map<String, List<EvenementMachine>> evenementsParMachine = new HashMap<>();
    private SimpleDateFormat formatDate = new SimpleDateFormat("ddMMyyyy HH:mm");

    public void chargerEvenements(String cheminFichier) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(cheminFichier));
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
}

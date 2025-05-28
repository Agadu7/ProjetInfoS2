package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

    public class EvenementMachine {
        public Date horodatage;
        public String type;
        public String evenement;
        public String machine;
        public Map<String, List<EvenementMachine>> evenementsParMachine = new HashMap<>();
        public EvenementMachine(Date horodatage, String type, String evenement, String machine) {
            this.horodatage = horodatage;
            this.type = type;
            this.evenement = evenement;
            this.machine = machine;
        }
        public void chargerEvenements(String suiviMaintenance) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("suiviMaintenance.txt"));
        String ligne;
        SimpleDateFormat formatDate = new SimpleDateFormat("ddMMyyyy HH:mm");

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
                //System.out.println(horodatage);// Affichage de l'horodatage pour vérification
                EvenementMachine ev = new EvenementMachine(horodatage, type, evenement, machine);
                evenementsParMachine.putIfAbsent(machine, new ArrayList<>());
                evenementsParMachine.get(machine).add(ev);
            } catch (Exception e) {
                System.out.println("Erreur de parsing pour la ligne : " + ligne);
            }
        }

        in.close();
    }
}

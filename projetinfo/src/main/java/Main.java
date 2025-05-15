import java.io.IOException;
import java.util.Map;

public class Main {
   /*  public static void main(String[] args) {
        try {
            ExtractionEvenement extraction = new ExtractionEvenement();
            extraction.chargerEvenements("Suivi/suiviMaintenance.txt");

            Fiabilite fiabilite = new Fiabilite();
            fiabilite.chargerFiabilite("Suivi/suiviMaintenance.txt");

            TriFiabilite tri = new TriFiabilite();
            tri.chargerFiabilite("Suivi/suiviMaintenance.txt");

            // Affichage des données récupérées
            System.out.println("Fiabilité de Machine1 : " + fiabilite.getFiabilite("Machine1"));

            System.out.println("Machines triées par fiabilité :");
            for (Map.Entry<String, Integer> entry : tri.getMachinesTriees()) {
                System.out.println("Machine : " + entry.getKey() + " - Fiabilité : " + entry.getValue());
            }
        } catch (IOException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }*/
    public static void main(String[] args) {
        TriFiabilite tf = new TriFiabilite();
        try {
            tf.chargerFiabilite("suiviMaintenance.txt");

            for (Map.Entry<String, Integer> entry : tf.getMachinesTriees()) {
                System.out.println(entry.getKey() + " : " + entry.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


